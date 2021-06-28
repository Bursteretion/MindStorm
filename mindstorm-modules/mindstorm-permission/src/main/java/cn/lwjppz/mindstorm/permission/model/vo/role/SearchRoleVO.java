package cn.lwjppz.mindstorm.permission.model.vo.role;

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
 * @since : 2021-05-16
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@ApiModel("角色搜索信息")
public class SearchRoleVO {

    @ApiModelProperty("名称")
    private String roleName;

    @ApiModelProperty("状态")
    private Integer status;

    @ApiModelProperty("第几页")
    private Integer pageIndex;

    @ApiModelProperty("每页条数")
    private Integer pageSize;

    @ApiModelProperty("创建时间（起始时间）")
    private Date startTime;

    @ApiModelProperty("创建时间（结束时间）")
    private Date endTime;
}
