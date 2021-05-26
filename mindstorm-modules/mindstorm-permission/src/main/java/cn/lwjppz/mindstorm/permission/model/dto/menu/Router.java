package cn.lwjppz.mindstorm.permission.model.dto.menu;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 前端路由菜单
 * </p>
 *
 * @author : lwj
 * @since : 2021-05-26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Router implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String pid;

    private String name;

    private String alias;

    private String permissionValue;

    private String path;

    private String redirect;

    private String component;

    private String icon;

    private Integer sort;

    private List<Router> children;
}
