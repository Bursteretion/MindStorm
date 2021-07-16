package cn.lwjppz.mindstorm.education.model.vo.courseclass;

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
public class CourseClassVO {

    @ApiModelProperty(value = "课程班级Id")
    private String id;

    @ApiModelProperty(value = "课程Id")
    private String courseId;

    @ApiModelProperty(value = "班级名称")
    private String className;

    @ApiModelProperty(value = "班级排序")
    private Integer sort;

}
