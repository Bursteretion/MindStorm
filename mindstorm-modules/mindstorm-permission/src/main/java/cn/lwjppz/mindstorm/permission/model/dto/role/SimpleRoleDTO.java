package cn.lwjppz.mindstorm.permission.model.dto.role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
public class SimpleRoleDTO {

    private String value;

    private String label;
}
