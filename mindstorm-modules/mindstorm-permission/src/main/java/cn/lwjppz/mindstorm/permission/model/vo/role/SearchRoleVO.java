package cn.lwjppz.mindstorm.permission.model.vo.role;

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
 * @since : 2021-05-16
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@ApiModel("角色搜索信息")
public class SearchRoleVO {

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("状态")
    private Integer status;

    @ApiModelProperty("第几页")
    private Integer pageIndex;

    @ApiModelProperty("每页条数")
    private Integer pageSize;
}
