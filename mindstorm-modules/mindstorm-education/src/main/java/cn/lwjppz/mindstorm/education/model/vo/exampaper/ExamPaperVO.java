package cn.lwjppz.mindstorm.education.model.vo.exampaper;

import cn.lwjppz.mindstorm.education.model.vo.exampaperquestion.ExamPaperQuestionVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
public class ExamPaperVO {

    @ApiModelProperty(value = "试卷Id")
    private String id;

    @ApiModelProperty(value = "上级文件夹Id")
    private String pid;

    @ApiModelProperty(value = "试卷所属课程Id")
    private String courseId;

    @ApiModelProperty(value = "试卷创建用户Id")
    private String userId;

    @ApiModelProperty(value = "试卷（文件夹）名称")
    private String title;

    @ApiModelProperty(value = "试卷题目数量")
    private Integer questionCount;

    @ApiModelProperty(value = "试卷总分")
    private BigDecimal totalScore;

    @ApiModelProperty(value = "试卷难度（0-简单，1-中等，2-困难）")
    private Integer difficulty;

    @ApiModelProperty(value = "是否是文件夹")
    private Boolean isFolder;

    @ApiModelProperty(value = "试卷状态（0-未完成，1-完成）")
    private Boolean status;

    @ApiModelProperty(value = "试卷题目集合")
    private List<ExamPaperQuestionVO> examPaperQuestions;

}
