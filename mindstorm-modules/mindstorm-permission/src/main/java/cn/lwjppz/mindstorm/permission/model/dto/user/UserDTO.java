package cn.lwjppz.mindstorm.permission.model.dto.user;

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
 * @since : 2021-05-22
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserDTO {

    private String id;

    private String username;

    private String realName;

    private Integer age;

    private Integer sex;

    private Date birthDay;

    private String phone;

    private String email;

    private Integer status;

    private Date gmtCreate;

}
