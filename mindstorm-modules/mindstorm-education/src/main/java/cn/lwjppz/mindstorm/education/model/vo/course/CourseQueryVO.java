package cn.lwjppz.mindstorm.education.model.vo.course;

import cn.lwjppz.mindstorm.common.core.support.BaseQueryVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * <p></p>
 *
 * @author : lwj
 * @since : 2021-07-15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CourseQueryVO extends BaseQueryVO {

    @ApiModelProperty(value = "课程所有者Id")
    private String userId;

    @ApiModelProperty(value = "课程名称")
    private String name;

    @ApiModelProperty(value = "开设该课程的教师名称")
    private String teacherName;

    @ApiModelProperty(value = "课程所属院系Id")
    private String academyId;

}
