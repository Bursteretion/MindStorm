package cn.lwjppz.mindstorm.api.permission.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * <p></p>
 *
 * @author : lwj
 * @since : 2021-07-16
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserTo {

    private String userId;

    private String username;

    private String realName;

}
