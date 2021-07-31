package cn.lwjppz.mindstorm.education.model.vo.exampaperquestion;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * <p></p>
 *
 * @author : lwj
 * @since : 2021-07-31
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ExamPaperQuestionVO {

    @ApiModelProperty(value = "试卷Id")
    private String examPaperId;

    @ApiModelProperty(value = "题目Id")
    private String questionId;

    @ApiModelProperty(value = "题目分数")
    private BigDecimal score;

}
