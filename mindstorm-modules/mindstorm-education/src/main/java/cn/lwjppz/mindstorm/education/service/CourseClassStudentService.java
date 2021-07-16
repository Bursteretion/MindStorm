package cn.lwjppz.mindstorm.education.service;

import cn.lwjppz.mindstorm.education.model.entity.CourseClassStudent;
import cn.lwjppz.mindstorm.education.model.vo.courseclassstudent.CourseClassStudentVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 课程班级学生表 服务类
 * </p>
 *
 * @author lwj
 * @since 2021-07-15
 */
public interface CourseClassStudentService extends IService<CourseClassStudent> {

    /**
     * 根据班级Id获取学生关联列表
     *
     * @param classId 班级Id
     * @return 学生关联列表
     */
    List<CourseClassStudent> listCourseClassStudent(String classId);

    /**
     * 新增课程班级学生关联
     *
     * @param courseClassStudentVO 课程班级学生关联信息
     * @return 课程班级学生关联信息
     */
    CourseClassStudent insertCourseClassStudent(CourseClassStudentVO courseClassStudentVO);

    /**
     * 根据班级Id删除关联学生
     *
     * @param classId 班级Id
     * @return 是否删除成功
     */
    boolean deleteCourseClassStudent(String classId);

    /**
     * 根据Id删除班级学生关联信息
     *
     * @param courseClassStudentId Id
     * @return 是否删除成功
     */
    boolean deleteCourseClassStudentById(String courseClassStudentId);

    /**
     * 获取该班级学生数量
     *
     * @param classId 班级Id
     * @return 学生数量
     */
    Integer getCount(String classId);

}
