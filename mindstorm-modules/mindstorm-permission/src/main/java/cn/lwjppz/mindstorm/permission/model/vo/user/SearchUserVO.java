package cn.lwjppz.mindstorm.permission.model.vo.user;

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
 * @since : 2021-05-22
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SearchUserVO {

    @ApiModelProperty(value = "第某页")
    private Integer pageIndex;

    @ApiModelProperty(value = "每页条数")
    private Integer pageSize;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "真实姓名")
    private String realName;

    @ApiModelProperty(value = "状态")
    private Integer status;

    @ApiModelProperty(value = "创建时间（起始时间）")
    private Date startTime;

    @ApiModelProperty(value = "创建时间（结束时间）")
    private Date endTime;

}
