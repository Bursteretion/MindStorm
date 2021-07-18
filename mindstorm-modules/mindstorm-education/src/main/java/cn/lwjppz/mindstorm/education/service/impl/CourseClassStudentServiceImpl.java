package cn.lwjppz.mindstorm.education.service.impl;

import cn.lwjppz.mindstorm.common.core.utils.StringUtils;
import cn.lwjppz.mindstorm.education.model.dto.courseclassstudent.CourseClassStudentDTO;
import cn.lwjppz.mindstorm.education.model.entity.CourseClassStudent;
import cn.lwjppz.mindstorm.education.mapper.CourseClassStudentMapper;
import cn.lwjppz.mindstorm.education.model.vo.courseclassstudent.CourseClassStudentQueryVO;
import cn.lwjppz.mindstorm.education.model.vo.courseclassstudent.CourseClassStudentVO;
import cn.lwjppz.mindstorm.education.model.vo.student.StudentQueryVO;
import cn.lwjppz.mindstorm.education.service.CourseClassStudentService;
import cn.lwjppz.mindstorm.education.service.StudentService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <p>
 * 课程班级学生表 服务实现类
 * </p>
 *
 * @author lwj
 * @since 2021-07-15
 */
@Service
public class CourseClassStudentServiceImpl extends ServiceImpl<CourseClassStudentMapper, CourseClassStudent> implements CourseClassStudentService {

    private final StudentService studentService;

    public CourseClassStudentServiceImpl(@Lazy StudentService studentService) {
        this.studentService = studentService;
    }

    @Override
    public List<CourseClassStudent> listCourseClassStudent(String classId) {
        var wrapper = getCommonWrapper();
        if (StringUtils.isNotEmpty(classId)) {
            wrapper.eq(CourseClassStudent::getClassId, classId);
        }
        return baseMapper.selectList(wrapper);
    }

    @Override
    public IPage<CourseClassStudentDTO> queryCourseClassStudent(CourseClassStudentQueryVO courseClassStudentQueryVO) {
        var students = studentService.queryStudents(new StudentQueryVO(null,
                null,
                null,
                courseClassStudentQueryVO.getRealName(),
                null,
                null,
                courseClassStudentQueryVO.getSno(),
                null)).getRecords();

        var courseClassStudents = baseMapper.selectList(null);

        var courseClassStudentMap = courseClassStudents.stream()
                .collect(Collectors.toMap(CourseClassStudent::getId,
                        courseClassStudent -> courseClassStudent));

        int skipCount = (courseClassStudentQueryVO.getPageSize() * (courseClassStudentQueryVO.getPageIndex()) - 1);
        var res = students.stream()
                .filter(item -> courseClassStudentMap.containsKey(item.getId()))
                .map(item -> convertCourseClassStudentDTO(courseClassStudentMap.get(item.getId())))
                .skip((long) skipCount * courseClassStudentQueryVO.getPageSize())
                .limit(courseClassStudentQueryVO.getPageSize())
                .collect(Collectors.toList());

        IPage<CourseClassStudentDTO> resPage = new Page<>(courseClassStudentQueryVO.getPageIndex(),
                courseClassStudentQueryVO.getPageSize());

        resPage.setRecords(res);
        resPage.setTotal(res.size());
        return resPage;
    }

    @Override
    public CourseClassStudent insertCourseClassStudent(CourseClassStudentVO courseClassStudentVO) {
        var courseClassStudent = new CourseClassStudent();
        BeanUtils.copyProperties(courseClassStudentVO, courseClassStudent);

        baseMapper.insert(courseClassStudent);
        return courseClassStudent;
    }

    @Override
    public boolean deleteCourseClassStudent(String classId) {
        if (StringUtils.isNotEmpty(classId)) {
            LambdaQueryWrapper<CourseClassStudent> queryWrapper = Wrappers.lambdaQuery();
            queryWrapper.eq(CourseClassStudent::getClassId, classId);
            baseMapper.delete(queryWrapper);
        }
        return true;
    }

    @Override
    public boolean deleteCourseClassStudentById(String courseClassStudentId) {
        if (StringUtils.isNotEmpty(courseClassStudentId)) {
            return baseMapper.deleteById(courseClassStudentId) > 0;
        }
        return false;
    }

    @Override
    public Integer getCount(String classId) {
        LambdaQueryWrapper<CourseClassStudent> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(CourseClassStudent::getClassId, classId);
        return baseMapper.selectCount(queryWrapper);
    }

    @Override
    public CourseClassStudentDTO convertCourseClassStudentDTO(CourseClassStudent courseClassStudent) {
        var courseClassStudentDTO = new CourseClassStudentDTO();
        // 获取学生信息
        var student = studentService.convertToStudentDTO(studentService.infoStudent(courseClassStudent.getStudentId()));
        BeanUtils.copyProperties(student, courseClassStudentDTO);
        courseClassStudentDTO.setId(courseClassStudent.getId());
        courseClassStudentDTO.setGmtCreate(courseClassStudent.getGmtCreate());
        return courseClassStudentDTO;
    }

    @Override
    public List<CourseClassStudentDTO> convertCourseClassStudentDTO(List<CourseClassStudent> courseClassStudents) {
        return courseClassStudents.stream()
                .map(this::convertCourseClassStudentDTO)
                .collect(Collectors.toList());
    }

    private LambdaQueryWrapper<CourseClassStudent> getCommonWrapper() {
        LambdaQueryWrapper<CourseClassStudent> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.orderByAsc(CourseClassStudent::getGmtCreate);
        return queryWrapper;
    }
}
