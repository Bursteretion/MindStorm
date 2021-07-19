package cn.lwjppz.mindstorm.education.model.vo.courseclassstudent;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

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
public class CourseClassStudentBatchVO {

    @ApiModelProperty(value = "班级Id")
    private String classId;

    @ApiModelProperty(value = "学生Id集合")
    private List<String> studentIds;

}
