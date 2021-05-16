package cn.lwjppz.mindstorm.permission.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * <p>
 * 新增用户表单信息
 * </p>
 *
 * @author : lwj
 * @since : 2021-05-10
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserVO {

    @ApiModelProperty("用户Id")
    private String id;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "真实姓名")
    private String realName;

    @ApiModelProperty(value = "年龄")
    private Integer age;

    @ApiModelProperty(value = "性别")
    private Integer sex;

    @ApiModelProperty(value = "生日")
    private Date birthDay;

    @ApiModelProperty("手机")
    private String phone;

    @ApiModelProperty("学生年级")
    private Integer userLevel;

    @ApiModelProperty("用户状态")
    private Integer status;
}
