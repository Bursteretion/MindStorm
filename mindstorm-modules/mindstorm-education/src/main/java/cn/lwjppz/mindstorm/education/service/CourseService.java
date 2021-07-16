package cn.lwjppz.mindstorm.education.service;

import cn.lwjppz.mindstorm.education.model.dto.course.CourseDTO;
import cn.lwjppz.mindstorm.education.model.entity.Course;
import cn.lwjppz.mindstorm.education.model.vo.course.CourseQueryVO;
import cn.lwjppz.mindstorm.education.model.vo.course.CourseVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 课程服务类
 * </p>
 *
 * @author lwj
 * @since 2021-07-15
 */
public interface CourseService extends IService<Course> {

    /**
     * 获取所有课程信息
     *
     * @return 所有课程信息
     */
    List<Course> listCourse();

    /**
     * 条件查询课程信息
     *
     * @param courseQueryVO 查询条件
     * @return 分页信息
     */
    IPage<CourseDTO> queryCourse(CourseQueryVO courseQueryVO);

    /**
     * 新增一门课程
     *
     * @param courseVO 课程信息
     * @return 课程信息
     */
    Course insertCourse(CourseVO courseVO);

    /**
     * 更新课程信息
     *
     * @param courseVO 课程信息
     * @return 是否更改成功
     */
    boolean updateCourse(CourseVO courseVO);

    /**
     * 根据课程Id获取课程信息
     *
     * @param courseId 课程Id
     * @return 课程信息
     */
    Course infoCourse(String courseId);

    /**
     * 根据课程Id删除课程信息
     *
     * @param courseId 课程Id
     * @return 是否删除成功
     */
    boolean deleteCourse(String courseId);

    /**
     * 批量删除课程信息
     *
     * @param courseIds 课程Id集合
     * @return 是否删除成功
     */
    boolean batchDeleteCourse(List<String> courseIds);

    /**
     * 更改课程状态
     *
     * @param courseId 课程Id
     * @param status   状态
     * @return 是否更改成功
     */
    boolean changeCourseStatus(String courseId, Integer status);

    /**
     * 将 Course 对象转为 CourseDTO 对象
     *
     * @param course Course 对象
     * @return CourseDTO 对象
     */
    CourseDTO convertToCourseDTO(Course course);

    /**
     * 将 Course 对象集合转为 CourseDTO 对象集合
     *
     * @param courses Course 对象集合
     * @return CourseDTO 对象集合
     */
    List<CourseDTO> convertToCourseDTO(List<Course> courses);

}
