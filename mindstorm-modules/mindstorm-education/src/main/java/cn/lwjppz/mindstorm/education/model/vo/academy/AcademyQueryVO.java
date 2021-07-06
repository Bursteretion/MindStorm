package cn.lwjppz.mindstorm.education.model.vo.academy;

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
 * @since : 2021-06-08
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@ApiModel("院系查询表单信息")
public class AcademyQueryVO {

    @ApiModelProperty("页码")
    private Integer pageIndex;

    @ApiModelProperty("每页条数")
    private Integer pageSize;

    @ApiModelProperty("院系名称")
    private String academyName;

    @ApiModelProperty("院系状态")
    private Integer status;

    @ApiModelProperty("创建的时间范围：开始时间")
    private Date startTime;

    @ApiModelProperty("创建的时间范围：结束时间")
    private Date endTime;

}
