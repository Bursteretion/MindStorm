package cn.lwjppz.mindstorm.auth.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * <p>
 * 用户登录对象
 * </p>
 *
 * @author : lwj
 * @since : 2021-05-15
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@ApiModel("用户登录对象")
public class LoginBody {

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("密码")
    private String password;

}
