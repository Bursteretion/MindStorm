package cn.lwjppz.mindstorm.education.service.impl;

import cn.lwjppz.mindstorm.common.core.utils.ServiceUtils;
import cn.lwjppz.mindstorm.common.core.utils.StringUtils;
import cn.lwjppz.mindstorm.education.model.dto.courseclassstudent.CourseClassStudentDTO;
import cn.lwjppz.mindstorm.education.model.dto.student.StudentDTO;
import cn.lwjppz.mindstorm.education.model.dto.student.StudentSimpleDTO;
import cn.lwjppz.mindstorm.education.model.entity.CourseClassStudent;
import cn.lwjppz.mindstorm.education.mapper.CourseClassStudentMapper;
import cn.lwjppz.mindstorm.education.model.entity.Student;
import cn.lwjppz.mindstorm.education.model.vo.courseclassstudent.CourseClassStudentBatchVO;
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
import org.springframework.util.CollectionUtils;

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
        LambdaQueryWrapper<CourseClassStudent> wrapper = Wrappers.lambdaQuery();
        if (StringUtils.isNotEmpty(courseClassStudentQueryVO.getClassId())) {
            wrapper.eq(CourseClassStudent::getClassId, courseClassStudentQueryVO.getClassId());
        }
        var courseClassStudents = baseMapper.selectList(wrapper);
        var students =
                studentService.convertToStudentDTO(studentService.queryStudent(ServiceUtils.fetchProperty(courseClassStudents,
                        CourseClassStudent::getStudentId)));

        var studentMap = students.stream()
                .collect(Collectors.toMap(StudentDTO::getId, student -> student));

        int skipCount = (courseClassStudentQueryVO.getPageSize() * (courseClassStudentQueryVO.getPageIndex() - 1));
        var res = courseClassStudents.stream()
                .filter(item -> {
                    var student = studentMap.get(item.getStudentId());
                    boolean sno = true, realName = true, flag = true;
                    if (StringUtils.isNotEmpty(courseClassStudentQueryVO.getSno())) {
                        sno = student.getSno().contains(courseClassStudentQueryVO.getSno());
                        flag = false;
                    }
                    if (StringUtils.isNotEmpty(courseClassStudentQueryVO.getRealName())) {
                        realName = student.getRealName().contains(courseClassStudentQueryVO.getRealName());
                        flag = false;
                    }
                    if (flag) {
                        return true;
                    }
                    return sno && realName;
                })
                .map(item -> {
                    var courseClassStudentDTO = new CourseClassStudentDTO();
                    var student = studentMap.get(item.getStudentId());
                    courseClassStudentDTO.setId(item.getId());
                    courseClassStudentDTO.setAcademyName(student.getAcademyName());
                    courseClassStudentDTO.setSno(student.getSno());
                    courseClassStudentDTO.setProfessionName(student.getProfessionName());
                    courseClassStudentDTO.setRealName(student.getRealName());
                    courseClassStudentDTO.setGmtCreate(item.getGmtCreate());
                    return courseClassStudentDTO;
                })
                .skip(skipCount)
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
        var student = studentService.selectStudent(courseClassStudentVO.getRealName(),
                courseClassStudentVO.getPhoneOrSno());

        courseClassStudent.setClassId(courseClassStudentVO.getClassId());
        courseClassStudent.setStudentId(student.getId());

        baseMapper.insert(courseClassStudent);
        return courseClassStudent;
    }

    @Override
    public boolean insertBatchCourseClassStudent(CourseClassStudentBatchVO courseClassStudentBatchVO) {
        if (!CollectionUtils.isEmpty(courseClassStudentBatchVO.getStudentIds())) {
            var studentIds = courseClassStudentBatchVO.getStudentIds();
            studentIds.forEach(item -> {
                var courseClassStudent = new CourseClassStudent();
                courseClassStudent.setClassId(courseClassStudentBatchVO.getClassId());
                courseClassStudent.setStudentId(item);
                baseMapper.insert(courseClassStudent);
            });
        }
        return true;
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
    public boolean deleteCourseClassStudentByStudentId(String studentId) {
        if (StringUtils.isNotEmpty(studentId)) {
            LambdaQueryWrapper<CourseClassStudent> wrapper = Wrappers.lambdaQuery();
            wrapper.eq(CourseClassStudent::getStudentId, studentId);
            baseMapper.delete(wrapper);
        }
        return true;
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

    @Override
    public List<StudentSimpleDTO> listNonClassStudents(CourseClassStudentQueryVO courseClassStudentQueryVO) {
        var checkedKeys =
                new HashSet<>(ServiceUtils.fetchProperty(listCourseClassStudent(courseClassStudentQueryVO.getClassId()),
                        CourseClassStudent::getStudentId));
        var students =
                studentService.convertToStudentSimpleDTO(studentService.queryStudents(courseClassStudentQueryVO.getRealName(), courseClassStudentQueryVO.getSno()));

        students = students.stream().filter(item -> !checkedKeys.contains(item.getId())).collect(Collectors.toList());
        return students;
    }

    private LambdaQueryWrapper<CourseClassStudent> getCommonWrapper() {
        LambdaQueryWrapper<CourseClassStudent> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.orderByAsc(CourseClassStudent::getGmtCreate);
        return queryWrapper;
    }
}
