package cn.lwjppz.mindstorm.permission.model.dto.roleMenu;

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
public class RoleMenuDTO {

    private String roleId;

    private String roleName;

    private String remark;

    private List<String> checkedKeys;
}
