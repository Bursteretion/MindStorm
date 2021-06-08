package cn.lwjppz.mindstorm.education.model.vo.profession;

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
 * @since : 2021-06-08
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@ApiModel("专业信息提交表单")
public class ProfessionVO {

    @ApiModelProperty(value = "专业Id")
    private String id;

    @ApiModelProperty(value = "专业名称")
    private String name;

    @ApiModelProperty(value = "专业状态（1 正常，0 禁用）")
    private Integer status;

    @ApiModelProperty(value = "排序")
    private Integer sort;

}
