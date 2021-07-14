package cn.lwjppz.mindstorm.user.model.vo.student;

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
public class StudentQueryVO extends BaseQueryVO {

    @ApiModelProperty("院系Id")
    private String academyId;

    @ApiModelProperty("专业Id")
    private String professionId;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("真实姓名")
    private String realName;

    @ApiModelProperty("电话")
    private String phone;

    @ApiModelProperty("性别")
    private Integer sex;

    @ApiModelProperty("学号")
    private String sno;

    @ApiModelProperty("状态")
    private Integer status;

}
