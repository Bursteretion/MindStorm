package cn.lwjppz.mindstorm.knowledge.model.vo.subject;

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
 * @since : 2021-06-03
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@ApiModel("学科信息")
public class SubjectVO {

    @ApiModelProperty(value = "学科Id")
    private String id;

    @ApiModelProperty(value = "学科名称（语文、英语、数学等）")
    private String name;

    @ApiModelProperty(value = "所属年级(1-16)")
    private Integer level;

    @ApiModelProperty(value = "学科描述")
    private String description;

}
