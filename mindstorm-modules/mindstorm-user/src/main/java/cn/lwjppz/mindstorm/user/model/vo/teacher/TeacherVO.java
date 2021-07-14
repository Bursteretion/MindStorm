package cn.lwjppz.mindstorm.user.model.vo.teacher;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * <p></p>
 *
 * @author : lwj
 * @since : 2021-07-13
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TeacherVO {

    @ApiModelProperty(value = "教师Id")
    private String id;

    @ApiModelProperty(value = "院系Id")
    private String academyId;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "昵称")
    private String nickName;

    @ApiModelProperty(value = "真实姓名")
    private String realName;

    @ApiModelProperty(value = "年龄")
    private Integer age;

    @ApiModelProperty(value = "性别 1.男 2.女")
    private Integer sex;

    @ApiModelProperty(value = "生日")
    private Date birthDay;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "状态(0:禁止,1:正常)")
    private Integer status;

    @ApiModelProperty(value = "头像地址")
    private String avatar;

}
