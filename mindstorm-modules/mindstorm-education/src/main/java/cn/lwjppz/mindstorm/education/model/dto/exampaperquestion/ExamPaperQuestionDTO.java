package cn.lwjppz.mindstorm.education.model.dto.exampaperquestion;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
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
public class ExamPaperQuestionDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 题目Id
     */
    private String questionId;

    /**
     * 题目内容
     */
    private String questionContent;

    /**
     * 该题分数
     */
    private BigDecimal score;
}
