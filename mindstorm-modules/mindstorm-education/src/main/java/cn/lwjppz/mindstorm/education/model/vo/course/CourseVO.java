package cn.lwjppz.mindstorm.education.model.vo.course;

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
public class CourseVO {

    @ApiModelProperty(value = "课程Id")
    private String id;

    @ApiModelProperty(value = "课程所有者Id")
    private String userId;

    @ApiModelProperty(value = "课程名称")
    private String name;

    @ApiModelProperty(value = "开设该课程的教师名称")
    private String teacherName;

    @ApiModelProperty(value = "课程所属院系Id")
    private String academyId;

    @ApiModelProperty(value = "课程封面地址")
    private String thumbnail;

    @ApiModelProperty(value = "课程说明")
    private String description;

}
