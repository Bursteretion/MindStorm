package cn.lwjppz.mindstorm.education.service.impl;

import cn.lwjppz.mindstorm.common.core.utils.StringUtils;
import cn.lwjppz.mindstorm.education.model.entity.CourseClassStudent;
import cn.lwjppz.mindstorm.education.mapper.CourseClassStudentMapper;
import cn.lwjppz.mindstorm.education.model.vo.courseclassstudent.CourseClassStudentVO;
import cn.lwjppz.mindstorm.education.service.CourseClassStudentService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public List<CourseClassStudent> listCourseClassStudent(String classId) {
        var wrapper = getCommonWrapper();
        if (StringUtils.isNotEmpty(classId)) {
            wrapper.eq(CourseClassStudent::getClassId, classId);
        }
        return baseMapper.selectList(wrapper);
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

    private LambdaQueryWrapper<CourseClassStudent> getCommonWrapper() {
        LambdaQueryWrapper<CourseClassStudent> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.orderByAsc(CourseClassStudent::getGmtCreate);
        return queryWrapper;
    }
}
