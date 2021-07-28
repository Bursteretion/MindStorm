package cn.lwjppz.mindstorm.education.model.vo.question;

import cn.lwjppz.mindstorm.education.model.vo.questionanswer.QuestionAnswerVO;
import cn.lwjppz.mindstorm.education.model.vo.questionoption.QuestionOptionVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * <p></p>
 *
 * @author : lwj
 * @since : 2021-07-21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class QuestionFolderVO {

    @ApiModelProperty(value = "题目Id")
    private String id;

    @ApiModelProperty(value = "题目描述（纯文本）")
    private String originalContent;

}
