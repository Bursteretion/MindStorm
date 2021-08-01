package cn.lwjppz.mindstorm.education.model.dto.exampaper;

import cn.lwjppz.mindstorm.education.model.dto.exampaperquestion.ExamPaperQuestionDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

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
public class ExamPaperDetailDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 试卷Id
     */
    private String id;

    /**
     * 试卷（文件夹）名
     */
    private String title;

    /**
     * 试卷题目数量
     */
    private Integer questionCount;

    /**
     * 试卷难度（0-简单，1-中等，2-困难）
     */
    private Integer difficulty;

    /**
     * 试卷总分
     */
    private BigDecimal totalScore;

    /**
     * 题目集合
     */
    private List<ExamPaperQuestionDTO> questions;

}
