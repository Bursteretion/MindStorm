package cn.lwjppz.mindstorm.education.service;


import cn.lwjppz.mindstorm.education.model.dto.student.StudentDTO;
import cn.lwjppz.mindstorm.education.model.dto.student.StudentDetailDTO;
import cn.lwjppz.mindstorm.education.model.entity.Student;
import cn.lwjppz.mindstorm.education.model.vo.student.StudentQueryVO;
import cn.lwjppz.mindstorm.education.model.vo.student.StudentVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 学生表 服务类
 * </p>
 *
 * @author lwj
 * @since 2021-07-13
 */
public interface StudentService extends IService<Student> {

    /**
     * 获取所有学生
     *
     * @return 学生列表
     */
    List<Student> listStudents();

    /**
     * 条件查询学生信息
     *
     * @param studentQueryVO 查询信息
     * @return 分页信息
     */
    IPage<StudentDTO> queryStudents(StudentQueryVO studentQueryVO);

    /**
     * 新增一名学生
     *
     * @param studentVO 学生信息
     * @return 学生信息
     */
    Student createStudent(StudentVO studentVO);

    /**
     * 更新学生信息
     *
     * @param studentVO 学生信息
     * @return 是否更新成功
     */
    boolean updateStudent(StudentVO studentVO);

    /**
     * 通过学生Id获取学生信息
     *
     * @param studentId 学生Id
     * @return 学生信息
     */
    Student infoStudent(String studentId);

    /**
     * 批量删除学生信息
     *
     * @param studentIds 学生id集合
     * @return 是否删除成功
     */
    boolean batchDeleteStudent(List<String> studentIds);

    /**
     * 更改学生状态
     *
     * @param studentId 学生Id
     * @param status    状态
     * @return 是否更改成功
     */
    boolean changeStudentStatus(String studentId, Integer status);

    /**
     * 通过学生Id删除学生
     *
     * @param studentId 学生Id
     * @return 是否删除成功
     */
    boolean deleteStudent(String studentId);

    /**
     * 将 Student 对象转为 StudentDTO 对象
     *
     * @param student Student 对象
     * @return StudentDTO 对象
     */
    StudentDTO convertToStudentDTO(Student student);

    /**
     * 将 Student 集合转为 StudentDTO 集合
     *
     * @param students Student 集合
     * @return StudentDTO 集合
     */
    List<StudentDTO> convertToStudentDTO(List<Student> students);

    /**
     * 将 Student 对象转为 StudentDetailDTO 对象
     *
     * @param student Student 对象
     * @return StudentDetailDTO 对象
     */
    StudentDetailDTO convertStudentDetailDTO(Student student);

}
