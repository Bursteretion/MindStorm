package cn.lwjppz.mindstorm.user.service;

import cn.lwjppz.mindstorm.user.model.dto.student.StudentDetailDTO;
import cn.lwjppz.mindstorm.user.model.dto.teacher.TeacherDTO;
import cn.lwjppz.mindstorm.user.model.dto.teacher.TeacherDetailDTO;
import cn.lwjppz.mindstorm.user.model.entity.Student;
import cn.lwjppz.mindstorm.user.model.entity.Teacher;
import cn.lwjppz.mindstorm.user.model.entity.Teacher;
import cn.lwjppz.mindstorm.user.model.vo.teacher.TeacherQueryVO;
import cn.lwjppz.mindstorm.user.model.vo.teacher.TeacherVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 教师表 服务类
 * </p>
 *
 * @author lwj
 * @since 2021-07-13
 */
public interface TeacherService extends IService<Teacher> {

    /**
     * 获取所有教师
     *
     * @return 教师列表
     */
    List<Teacher> listTeachers();

    /**
     * 条件查询教师信息
     *
     * @param teacherQueryVO 查询信息
     * @return 分页信息
     */
    IPage<TeacherDTO> queryTeachers(TeacherQueryVO teacherQueryVO);

    /**
     * 新增一名教师
     *
     * @param teacherVO 教师信息
     * @return 教师信息
     */
    Teacher createTeacher(TeacherVO teacherVO);

    /**
     * 更新教师信息
     *
     * @param teacherVO 教师信息
     * @return 是否更新成功
     */
    boolean updateTeacher(TeacherVO teacherVO);

    /**
     * 通过教师Id获取教师信息
     *
     * @param teacherId 教师Id
     * @return 教师信息
     */
    Teacher infoTeacher(String teacherId);

    /**
     * 批量删除教师信息
     *
     * @param teacherIds 教师id集合
     * @return 是否删除成功
     */
    boolean batchDeleteTeacher(List<String> teacherIds);

    /**
     * 更改教师状态
     *
     * @param teacherId 教师Id
     * @param status    状态
     * @return 是否更改成功
     */
    boolean changeTeacherStatus(String teacherId, Integer status);

    /**
     * 通过教师Id删除教师
     *
     * @param teacherId 教师Id
     * @return 是否删除成功
     */
    boolean deleteTeacher(String teacherId);

    /**
     * 将 Teacher 对象转为 TeacherDTO 对象
     *
     * @param teacher Teacher 对象
     * @return TeacherDTO 对象
     */
    TeacherDTO convertToTeacherDTO(Teacher teacher);

    /**
     * 将 Teacher 集合转为 TeacherDTO 集合
     *
     * @param teachers Teacher 集合
     * @return TeacherDTO 集合
     */
    List<TeacherDTO> convertToTeacherDTO(List<Teacher> teachers);

    /**
     * 将 Teacher 对象转为 TeacherDetailDTO 对象
     *
     * @param teacher Teacher 对象
     * @return TeacherDetailDTO 对象
     */
    TeacherDetailDTO convertTeacherDetailDTO(Teacher teacher);

}
