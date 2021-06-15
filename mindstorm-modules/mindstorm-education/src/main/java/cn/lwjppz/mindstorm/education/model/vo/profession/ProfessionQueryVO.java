package cn.lwjppz.mindstorm.education.model.vo.profession;

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
@NoArgsConstructor
@AllArgsConstructor
@ToString
@ApiModel("专业查询提交表单信息")
public class ProfessionQueryVO {

    @ApiModelProperty("页码")
    private Integer pageNum;

    @ApiModelProperty("每页条数")
    private Integer pageSize;

    @ApiModelProperty("专业名称")
    private String professionName;

    @ApiModelProperty("专业状态（1 正常，0 禁用）")
    private Integer status;

    @ApiModelProperty("创建的时间范围：开始时间")
    private Date startTime;

    @ApiModelProperty("创建的时间范围：结束时间")
    private Date endTime;

}
