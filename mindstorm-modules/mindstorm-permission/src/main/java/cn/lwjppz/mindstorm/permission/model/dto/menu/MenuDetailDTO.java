package cn.lwjppz.mindstorm.permission.model.dto.menu;

import lombok.*;

import java.io.Serializable;

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
public class MenuDetailDTO implements Serializable {

    private String id;

    private String pid;

    private String name;

    private Integer type;

    private String permissionValue;

    private String path;

    private String component;

    private String icon;

    private Integer status;

}
