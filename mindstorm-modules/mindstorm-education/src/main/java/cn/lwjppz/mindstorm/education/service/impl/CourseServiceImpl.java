package cn.lwjppz.mindstorm.education.service.impl;

import cn.lwjppz.mindstorm.api.permission.feign.RemotePermissionFeignService;
import cn.lwjppz.mindstorm.api.permission.model.UserTo;
import cn.lwjppz.mindstorm.common.core.enums.status.ResultStatus;
import cn.lwjppz.mindstorm.common.core.exception.EntityNotFoundException;
import cn.lwjppz.mindstorm.common.core.utils.ServiceUtils;
import cn.lwjppz.mindstorm.common.core.utils.StringUtils;
import cn.lwjppz.mindstorm.education.model.dto.course.CourseDTO;
import cn.lwjppz.mindstorm.education.model.entity.Course;
import cn.lwjppz.mindstorm.education.mapper.CourseMapper;
import cn.lwjppz.mindstorm.education.model.vo.course.CourseQueryVO;
import cn.lwjppz.mindstorm.education.model.vo.course.CourseVO;
import cn.lwjppz.mindstorm.education.model.vo.courseclass.CourseClassVO;
import cn.lwjppz.mindstorm.education.service.AcademyService;
import cn.lwjppz.mindstorm.education.service.CourseClassService;
import cn.lwjppz.mindstorm.education.service.CourseService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author lwj
 * @since 2021-07-15
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {

    private final RemotePermissionFeignService remotePermissionFeignService;
    private final AcademyService academyService;
    private final CourseClassService courseClassService;

    public CourseServiceImpl(@Lazy RemotePermissionFeignService remotePermissionFeignService,
                             @Lazy AcademyService academyService,
                             @Lazy CourseClassService courseClassService) {
        this.remotePermissionFeignService = remotePermissionFeignService;
        this.courseClassService = courseClassService;
        this.academyService = academyService;
    }

    @Override
    public List<Course> listCourse() {
        return baseMapper.selectList(getCommonWrapper());
    }

    @Override
    public IPage<CourseDTO> queryCourse(CourseQueryVO courseQueryVO) {
        var wrapper = getCommonWrapper();
        if (StringUtils.isNotEmpty(courseQueryVO.getUserId())) {
            // TODO 根据当前用户Id从助教表（课程所属用户Id， 助教用户Id）中获取（userId == 助教Id）的所有用户Id，根据查询出的用户Id列表查询课程
            wrapper.eq(Course::getUserId, courseQueryVO.getUserId());
        }
        if (StringUtils.isNotEmpty(courseQueryVO.getAcademyId())) {
            var academyIds = academyService.getAcademyIdsById(courseQueryVO.getAcademyId());
            wrapper.in(Course::getAcademyId, academyIds);
        }
        if (StringUtils.isNotEmpty(courseQueryVO.getName())) {
            wrapper.like(Course::getName, courseQueryVO.getName());
        }
        if (StringUtils.isNotEmpty(courseQueryVO.getTeacherName())) {
            wrapper.like(Course::getTeacherName, courseQueryVO.getTeacherName());
        }
        if (null != courseQueryVO.getStartTime() && null != courseQueryVO.getEndTime()) {
            wrapper.in(Course::getGmtCreate, courseQueryVO.getStartTime(), courseQueryVO.getEndTime());
        }

        IPage<Course> page = new Page<>(courseQueryVO.getPageIndex(), courseQueryVO.getPageSize());
        page = baseMapper.selectPage(page, wrapper);

        IPage<CourseDTO> resPage = new Page<>();
        BeanUtils.copyProperties(page, resPage);

        resPage.setRecords(convertToCourseDTO(page.getRecords()));
        return resPage;
    }

    @Override
    public Course insertCourse(CourseVO courseVO) {
        var course = new Course();
        BeanUtils.copyProperties(courseVO, course);

        // 新增课程
        baseMapper.insert(course);

        // 新增课程默认班级
        var courseClassVO = new CourseClassVO();
        courseClassVO.setCourseId(course.getId());
        courseClassVO.setClassName("默认班级");
        courseClassVO.setSort(0);
        courseClassService.insertCourseClass(courseClassVO);
        return course;
    }

    @Override
    public boolean updateCourse(CourseVO courseVO) {
        var course = new Course();
        BeanUtils.copyProperties(courseVO, course);

        return baseMapper.updateById(course) > 0;
    }

    @Override
    public Course infoCourse(String courseId) {
        if (StringUtils.isNotEmpty(courseId)) {
            var course = baseMapper.selectById(courseId);
            if (null == course) {
                throw new EntityNotFoundException(ResultStatus.ENTITY_NOT_FOUND);
            }
            return course;
        }
        return null;
    }

    @Override
    public boolean deleteCourse(String courseId) {
        if (StringUtils.isNotEmpty(courseId)) {
            // 删除课程和课程班级关联信息
            courseClassService.deleteCourseClass(courseId);
            baseMapper.deleteById(courseId);
        }
        return true;
    }

    @Override
    public boolean batchDeleteCourse(List<String> courseIds) {
        return courseIds.stream()
                .filter(StringUtils::isNotEmpty)
                .allMatch(this::deleteCourse);
    }

    @Override
    public boolean changeCourseStatus(String courseId, Integer status) {
        if (StringUtils.isNotEmpty(courseId)) {
            var course = baseMapper.selectById(courseId);
            course.setStatus(status);

            return baseMapper.updateById(course) > 0;
        }
        return false;
    }

    @Override
    public CourseDTO convertToCourseDTO(Course course) {
        var courseDTO = new CourseDTO();
        BeanUtils.copyProperties(course, courseDTO);

        var academy = academyService.infoAcademy(course.getAcademyId());
        courseDTO.setAcademyName(academy.getName());

        var res = remotePermissionFeignService.remoteUserInfoById(course.getUserId());
        var userTo = ServiceUtils.feignValueConvert(res.getData().get("userTo"), UserTo.class);

        courseDTO.setOwnerName(userTo.getRealName());

        var classCount = courseClassService.getCourseClassCount(course.getId());
        courseDTO.setClassCount(classCount);
        return courseDTO;
    }

    @Override
    public List<CourseDTO> convertToCourseDTO(List<Course> courses) {
        return courses.stream()
                .map(this::convertToCourseDTO)
                .collect(Collectors.toList());
    }

    private LambdaQueryWrapper<Course> getCommonWrapper() {
        LambdaQueryWrapper<Course> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.orderByDesc(Course::getGmtCreate);
        return queryWrapper;
    }
}
