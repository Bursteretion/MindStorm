package cn.lwjppz.mindstorm.permission.model.dto.menu;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 权限DTO
 * </p>
 *
 * @author lwj
 * @since 2021-05-09
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MenuDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String pid;

    private String name;

    private String alias;

    private Integer type;

    private String permissionValue;

    private String path;

    private String redirect;

    private String component;

    private String icon;

    private Integer status;

    private Integer sort;

    private Date gmtCreate;

    private List<MenuDTO> children;

}
