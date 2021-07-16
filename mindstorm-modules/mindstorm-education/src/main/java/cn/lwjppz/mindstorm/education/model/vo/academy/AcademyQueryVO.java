package cn.lwjppz.mindstorm.education.model.vo.academy;

import cn.lwjppz.mindstorm.common.core.support.BaseQueryVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * <p></p>
 *
 * @author : lwj
 * @since : 2021-06-08
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@ToString
@ApiModel("院系查询表单信息")
public class AcademyQueryVO extends BaseQueryVO {

    @ApiModelProperty("院系名称")
    private String academyName;

    @ApiModelProperty("院系状态")
    private Integer status;

}
