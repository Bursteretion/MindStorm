package cn.lwjppz.mindstorm.permission.model.dto.userRole;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * <p></p>
 *
 * @author : lwj
 * @since : 2021-05-23
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserRoleDTO {

    private String userId;

    private String username;

    private String userType;

    List<String> roles;

}
