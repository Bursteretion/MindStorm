package cn.lwjppz.mindstorm.education.model.vo.questionanswer;

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
public class QuestionAnswerVO {

    @ApiModelProperty(value = "题目Id")
    private String questionId;

    @ApiModelProperty(value = "题目答案对应的选项的Id")
    private String optionId;

    @ApiModelProperty(value = "题目答案内容（当题目没有选项时）")
    private String value;

}
