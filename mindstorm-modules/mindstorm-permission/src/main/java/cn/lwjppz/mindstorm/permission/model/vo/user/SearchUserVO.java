package cn.lwjppz.mindstorm.permission.model.vo.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
public class SearchUserVO {

    private String username;

    private String realName;

    private Integer status;

    private Integer userType;
}
