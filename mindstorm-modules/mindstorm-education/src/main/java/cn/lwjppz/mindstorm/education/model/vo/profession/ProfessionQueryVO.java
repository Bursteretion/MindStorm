package cn.lwjppz.mindstorm.education.model.vo.profession;

import cn.lwjppz.mindstorm.common.core.support.BaseQueryVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.Date;

/**
 * <p></p>
 *
 * @author : lwj
 * @since : 2021-06-08
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString
@ApiModel("专业查询提交表单信息")
public class ProfessionQueryVO extends BaseQueryVO {

    @ApiModelProperty("院系Id")
    private String academyId;

    @ApiModelProperty("专业名称")
    private String professionName;

    @ApiModelProperty("专业状态（1 正常，0 禁用）")
    private Integer status;

}
