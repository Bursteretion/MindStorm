package cn.lwjppz.mindstorm.education.model.vo.questionoption;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * <p></p>
 *
 * @author : lwj
 * @since : 2021-07-22
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class QuestionOptionVO {

    @ApiModelProperty(value = "题目选项名称（A、B、、、、）")
    private String optionName;

    @ApiModelProperty(value = "题目选项值")
    private String optionValue;

}
