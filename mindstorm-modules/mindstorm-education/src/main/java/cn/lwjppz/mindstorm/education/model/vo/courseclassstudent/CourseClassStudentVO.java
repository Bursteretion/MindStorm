package cn.lwjppz.mindstorm.education.model.vo.courseclassstudent;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * <p></p>
 *
 * @author : lwj
 * @since : 2021-07-15
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CourseClassStudentVO {

    @ApiModelProperty(value = "班级Id")
    private String classId;

    @ApiModelProperty(value = "学生Id")
    private String studentId;

}
