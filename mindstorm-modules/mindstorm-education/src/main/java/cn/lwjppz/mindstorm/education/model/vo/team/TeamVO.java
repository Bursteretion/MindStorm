package cn.lwjppz.mindstorm.education.model.vo.team;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * <p></p>
 *
 * @author : lwj
 * @since : 2021-07-15
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TeamVO {

    @ApiModelProperty(value = "班级Id")
    private String id;

    @ApiModelProperty(value = "班级名称")
    private String name;

    @ApiModelProperty(value = "班级状态（0 禁用，1 正常）")
    private Integer status;

    @ApiModelProperty(value = "排序")
    private Integer sort;

}
