package cn.lwjppz.mindstorm.education.model.vo.team;

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
@ApiModel("班级查询提交表单信息")
public class TeamQueryVO extends BaseQueryVO {

    @ApiModelProperty("班级名称")
    private String teamName;

    @ApiModelProperty("班级（1 正常，0 禁用）")
    private Integer status;

}
