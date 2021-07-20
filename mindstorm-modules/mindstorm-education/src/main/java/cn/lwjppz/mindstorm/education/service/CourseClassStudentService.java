package cn.lwjppz.mindstorm.education.service;

import cn.lwjppz.mindstorm.education.model.dto.courseclassstudent.CourseClassStudentDTO;
import cn.lwjppz.mindstorm.education.model.dto.student.StudentSimpleDTO;
import cn.lwjppz.mindstorm.education.model.entity.CourseClassStudent;
import cn.lwjppz.mindstorm.education.model.vo.courseclassstudent.CourseClassStudentBatchVO;
import cn.lwjppz.mindstorm.education.model.vo.courseclassstudent.CourseClassStudentQueryVO;
import cn.lwjppz.mindstorm.education.model.vo.courseclassstudent.CourseClassStudentVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
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
     * 条件查询课程班级学生
     *
     * @param courseClassStudentQueryVO 查询条件
     * @return 分页信息
     */
    IPage<CourseClassStudentDTO> queryCourseClassStudent(CourseClassStudentQueryVO courseClassStudentQueryVO);

    /**
     * 新增课程班级学生关联
     *
     * @param courseClassStudentVO 课程班级学生关联信息
     * @return 课程班级学生关联信息
     */
    CourseClassStudent insertCourseClassStudent(CourseClassStudentVO courseClassStudentVO);

    /**
     * 批量新增课程班级学生关联
     *
     * @param courseClassStudentBatchVO 课程班级学生关联信息
     * @return 是否新增成功
     */
    boolean insertBatchCourseClassStudent(CourseClassStudentBatchVO courseClassStudentBatchVO);

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
     * 根据学生Id删除班级学生关联信息
     *
     * @param studentId 学生Id
     * @return 是否删除成功
     */
    boolean deleteCourseClassStudentByStudentId(String studentId);

    /**
     * 获取该班级学生数量
     *
     * @param classId 班级Id
     * @return 学生数量
     */
    Integer getCount(String classId);

    /**
     * 将 CourseClassStudent 对象转为 CourseClassStudentDTO 对象
     *
     * @param courseClassStudent CourseClassStudent 对象
     * @return CourseClassStudentDTO 对象
     */
    CourseClassStudentDTO convertCourseClassStudentDTO(CourseClassStudent courseClassStudent);

    /**
     * 将 CourseClassStudent 对象集合转为 CourseClassStudentDTO 对象集合
     *
     * @param courseClassStudents CourseClassStudent 对象集合
     * @return CourseClassStudentDTO 对象集合
     */
    List<CourseClassStudentDTO> convertCourseClassStudentDTO(List<CourseClassStudent> courseClassStudents);

    /**
     * 获取未加入该班级的学生列表
     *
     * @param courseClassStudentQueryVO 查询条件
     * @return 学生列表
     */
    List<StudentSimpleDTO> listNonClassStudents(CourseClassStudentQueryVO courseClassStudentQueryVO);
}
