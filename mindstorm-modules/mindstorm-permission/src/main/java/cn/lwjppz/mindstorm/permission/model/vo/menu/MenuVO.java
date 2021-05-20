package cn.lwjppz.mindstorm.permission.model.vo.menu;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


/**
 * <p>
 * 权限表单信息
 * </p>
 *
 * @author : lwj
 * @since : 2021-05-13
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@ApiModel("菜单表单信息")
public class MenuVO {

    @ApiModelProperty(value = "所属上级")
    private String pid;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "菜单别名")
    private String alias;

    @ApiModelProperty(value = "类型(1:菜单,2:按钮)")
    private Integer type;

    @ApiModelProperty(value = "权限标识")
    private String permissionValue;

    @ApiModelProperty(value = "访问路径")
    private String path;

    @ApiModelProperty("重定向地址")
    private String redirect;

    @ApiModelProperty(value = "组件路径")
    private String component;

    @ApiModelProperty(value = "图标")
    private String icon;

    @ApiModelProperty(value = "状态(0:禁止,1:正常)")
    private Integer status;

    @ApiModelProperty(value = "排序")
    private Integer sort;

}
