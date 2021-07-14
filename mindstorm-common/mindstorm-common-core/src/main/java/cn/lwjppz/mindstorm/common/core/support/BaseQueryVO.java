package cn.lwjppz.mindstorm.common.core.support;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.Date;

/**
 * <p></p>
 *
 * @author : lwj
 * @since : 2021-07-13
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BaseQueryVO {

    @ApiModelProperty("页码")
    private Integer pageIndex;

    @ApiModelProperty("每页条数")
    private Integer pageSize;

    @ApiModelProperty("创建时间（起始时间）")
    private Date startTime;

    @ApiModelProperty("创建时间（结束时间）")
    private Date endTime;

}
