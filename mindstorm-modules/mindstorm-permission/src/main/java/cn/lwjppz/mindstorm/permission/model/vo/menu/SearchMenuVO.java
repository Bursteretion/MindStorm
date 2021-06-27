package cn.lwjppz.mindstorm.permission.model.vo.menu;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

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

    @ApiModelProperty("菜单类型")
    private Integer type;

    @ApiModelProperty("菜单状态")
    private Integer status;

    @ApiModelProperty("创建时间（起始时间）")
    private Date startTime;

    @ApiModelProperty("创建时间（结束时间）")
    private Date endTime;

}
