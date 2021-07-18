package cn.lwjppz.mindstorm.education.model.vo.courseclassstudent;

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
public class CourseClassStudentQueryVO extends BaseQueryVO {

    @ApiModelProperty(value = "学生学号")
    private String sno;

    @ApiModelProperty(value = "学生姓名")
    private String realName;

}
