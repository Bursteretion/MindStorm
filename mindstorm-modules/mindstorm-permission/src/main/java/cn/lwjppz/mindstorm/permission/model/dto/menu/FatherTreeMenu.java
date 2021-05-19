package cn.lwjppz.mindstorm.permission.model.dto.menu;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Set;

/**
 * <p></p>
 *
 * @author : lwj
 * @since : 2021-05-19
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FatherTreeMenu {

    /**
     * 菜单 id
     */
    private String value;

    /**
     * 菜单 Name
     */
    private String label;

    private Set<FatherTreeMenu> children;
}
