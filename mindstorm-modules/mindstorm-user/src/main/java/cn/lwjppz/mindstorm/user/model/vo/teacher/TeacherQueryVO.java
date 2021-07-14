package cn.lwjppz.mindstorm.user.model.vo.teacher;

import cn.lwjppz.mindstorm.common.core.support.BaseQueryVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * <p></p>
 *
 * @author : lwj
 * @since : 2021-07-13
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TeacherQueryVO extends BaseQueryVO {

    @ApiModelProperty("院系Id")
    private String academyId;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("真实姓名")
    private String realName;

}
