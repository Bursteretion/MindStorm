package cn.lwjppz.mindstorm.education.service.impl;

import cn.lwjppz.mindstorm.common.core.utils.ServiceUtils;
import cn.lwjppz.mindstorm.common.core.utils.StringUtils;
import cn.lwjppz.mindstorm.education.model.dto.courseclass.CourseClassDTO;
import cn.lwjppz.mindstorm.education.model.entity.CourseClass;
import cn.lwjppz.mindstorm.education.mapper.CourseClassMapper;
import cn.lwjppz.mindstorm.education.model.vo.courseclass.CourseClassVO;
import cn.lwjppz.mindstorm.education.service.CourseClassService;
import cn.lwjppz.mindstorm.education.service.CourseClassStudentService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 课程班级表 服务实现类
 * </p>
 *
 * @author lwj
 * @since 2021-07-15
 */
@Service
public class CourseClassServiceImpl extends ServiceImpl<CourseClassMapper, CourseClass> implements CourseClassService {

    private final CourseClassStudentService courseClassStudentService;

    public CourseClassServiceImpl(CourseClassStudentService courseClassStudentService) {
        this.courseClassStudentService = courseClassStudentService;
    }

    @Override
    public List<CourseClass> listCourseClasses(String courseId) {
        var wrapper = getCommonWrapper();
        if (StringUtils.isNotEmpty(courseId)) {
            wrapper.eq(CourseClass::getCourseId, courseId);
        }
        return baseMapper.selectList(wrapper);
    }

    @Override
    public Integer getCourseClassCount(String courseId) {
        if (StringUtils.isNotEmpty(courseId)) {
            LambdaQueryWrapper<CourseClass> queryWrapper = Wrappers.lambdaQuery();
            queryWrapper.eq(CourseClass::getCourseId, courseId);
            return baseMapper.selectCount(queryWrapper);
        }
        return null;
    }

    @Override
    public List<CourseClass> queryCourseClasses(String courseId, String className) {
        var wrapper = getCommonWrapper();
        if (StringUtils.isNotEmpty(courseId)) {
            wrapper.eq(CourseClass::getCourseId, courseId);
        }
        if (StringUtils.isNotEmpty(className)) {
            wrapper.like(CourseClass::getClassName, className);
        }
        return baseMapper.selectList(wrapper);
    }

    @Override
    public CourseClass insertCourseClass(CourseClassVO courseClassVO) {
        var courseClass = new CourseClass();
        BeanUtils.copyProperties(courseClassVO, courseClass);

        // 补充邀请码
        var invitationCode = ServiceUtils.generateRandomInvitationCode();
        courseClass.setInvitationCode(invitationCode);

        baseMapper.insert(courseClass);
        return courseClass;
    }

    @Override
    public boolean updateCourseClass(CourseClassVO courseClassVO) {
        var courseClass = new CourseClass();
        BeanUtils.copyProperties(courseClassVO, courseClass);
        return baseMapper.updateById(courseClass) > 0;
    }

    @Override
    public boolean deleteCourseClass(String courseId) {
        if (StringUtils.isNotEmpty(courseId)) {
            LambdaQueryWrapper<CourseClass> queryWrapper = Wrappers.lambdaQuery();
            queryWrapper.eq(CourseClass::getCourseId, courseId);

            // 删除该课程所有班级所对应的学生关联信息
            var courseClasses = baseMapper.selectList(queryWrapper);
            var courseClassIds = ServiceUtils.fetchProperty(courseClasses, CourseClass::getId);
            courseClassIds.stream()
                    .filter(StringUtils::isNotEmpty)
                    .forEach(courseClassStudentService::deleteCourseClassStudent);
            // 删除该课程的所有班级
            baseMapper.delete(queryWrapper);
        }
        return true;
    }

    @Override
    public boolean deleteCourseClassById(String courseClassId) {
        if (StringUtils.isNotEmpty(courseClassId)) {
            // 删除该班级所有关联学生信息
            courseClassStudentService.deleteCourseClassStudent(courseClassId);
            // 删除该班级
            baseMapper.deleteById(courseClassId);
        }
        return true;
    }

    @Override
    public CourseClassDTO convertCourseClassDTO(CourseClass courseClass) {
        var courseClassDTO = new CourseClassDTO();
        BeanUtils.copyProperties(courseClass, courseClassDTO);
        // 获取该班级学生数量
        var studentCount = courseClassStudentService.getCount(courseClass.getId());
        courseClassDTO.setStudentCount(studentCount);
        return courseClassDTO;
    }

    @Override
    public List<CourseClassDTO> convertCourseClassDTO(List<CourseClass> courseClasses) {
        return courseClasses.stream()
                .map(this::convertCourseClassDTO)
                .collect(Collectors.toList());
    }

    private LambdaQueryWrapper<CourseClass> getCommonWrapper() {
        LambdaQueryWrapper<CourseClass> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.orderByAsc(CourseClass::getSort);
        return queryWrapper;
    }

}
