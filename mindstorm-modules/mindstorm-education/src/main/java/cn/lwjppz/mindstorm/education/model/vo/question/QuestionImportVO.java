package cn.lwjppz.mindstorm.education.model.vo.question;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * <p></p>
 *
 * @author : lwj
 * @since : 2021-07-29
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class QuestionImportVO {

    private String courseId;

    private String userId;

    @ExcelProperty("目录")
    private String directory;

    @ExcelProperty("题型")
    private String questionTypeName;

    @ExcelProperty("题干")
    private String questionContent;

    @ExcelProperty("正确答案")
    private String correctAnswer;

    @ExcelProperty("答案解析")
    private String answerAnalyze;

    @ExcelProperty("难易度")
    private String difficulty;

    @ExcelProperty("选项数")
    private Integer optionCount;

    @ExcelProperty("A")
    private String optionA;

    @ExcelProperty("B")
    private String optionB;

    @ExcelProperty("C")
    private String optionC;

    @ExcelProperty("D")
    private String optionD;

    @ExcelProperty("E")
    private String optionE;

    @ExcelProperty("F")
    private String optionF;

    @ExcelProperty("G")
    private String optionG;

}
