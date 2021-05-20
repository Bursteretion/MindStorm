package cn.lwjppz.mindstorm.permission.model.vo.menu;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * <p></p>
 *
 * @author : lwj
 * @since : 2021-05-20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@ApiModel("菜单查询信息")
public class SearchMenuVO {

    @ApiModelProperty("菜单名称")
    private String name;

    @ApiModelProperty("菜单状态")
    private Integer status;
}
