package cn.lwjppz.mindstorm.education.service;

import cn.lwjppz.mindstorm.education.model.dto.courseclass.CourseClassDTO;
import cn.lwjppz.mindstorm.education.model.entity.CourseClass;
import cn.lwjppz.mindstorm.education.model.vo.courseclass.CourseClassVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 课程班级表 服务类
 * </p>
 *
 * @author lwj
 * @since 2021-07-15
 */
public interface CourseClassService extends IService<CourseClass> {

    /**
     * 获取该课程下所有班级
     *
     * @param courseId 课程Id
     * @return 该课程所有班级
     */
    List<CourseClass> listCourseClasses(String courseId);

    /**
     * 获取该课程班级个数
     *
     * @param courseId 课程Id
     * @return 班级个数
     */
    Integer getCourseClassCount(String courseId);

    /**
     * 根据班级名称模糊查询
     *
     * @param className 班级名称
     * @return 查询到的所有班级
     */
    List<CourseClass> queryCourseClasses(String className);

    /**
     * 新增课程班级信息
     *
     * @param courseClassVO 课程班级信息
     * @return 课程班级信息
     */
    CourseClass insertCourseClass(CourseClassVO courseClassVO);

    /**
     * 更新课程班级信息
     *
     * @param courseClassVO 课程班级信息
     * @return 是否更新成功
     */
    boolean updateCourseClass(CourseClassVO courseClassVO);

    /**
     * 通过课程Id删除课程班级关联信息
     *
     * @param courseId 课程Id
     * @return 是否删除成功
     */
    boolean deleteCourseClass(String courseId);

    /**
     * 根据Id删除课程班级关联
     *
     * @param courseClassId Id
     * @return 是否删除成功
     */
    boolean deleteCourseClassById(String courseClassId);

    /**
     * 将 CourseClass 对象转为 CourseClassDTO 对象
     *
     * @param courseClass CourseClass 对象
     * @return CourseClassDTO 对象
     */
    CourseClassDTO convertCourseClassDTO(CourseClass courseClass);

    /**
     * 将 CourseClass 对象集合转为 CourseClassDTO 对象集合
     *
     * @param courseClasses CourseClass 对象集合
     * @return CourseClassDTO 对象集合
     */
    List<CourseClassDTO> convertCourseClassDTO(List<CourseClass> courseClasses);

}
