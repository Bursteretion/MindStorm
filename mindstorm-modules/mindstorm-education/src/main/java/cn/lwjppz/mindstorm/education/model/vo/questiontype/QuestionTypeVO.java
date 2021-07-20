package cn.lwjppz.mindstorm.education.model.vo.questiontype;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * <p></p>
 *
 * @author : lwj
 * @since : 2021-07-20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class QuestionTypeVO {

    @ApiModelProperty(value = "题型Id")
    private String id;

    @ApiModelProperty(value = "题型名称")
    private String name;

    @ApiModelProperty(value = "题型类型")
    private Integer type;

    @ApiModelProperty(value = "是否是用户自定义题型")
    private Integer isCustomize;

}
