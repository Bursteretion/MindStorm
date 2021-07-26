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
public class QuestionVO {

    @ApiModelProperty(value = "题目Id")
    private String id;

    @ApiModelProperty(value = "上级文件夹Id")
    private String pid;

    @ApiModelProperty(value = "所属课程Id")
    private String courseId;

    @ApiModelProperty(value = "创建用户Id")
    private String userId;

    @ApiModelProperty(value = "题目类型")
    private Integer questionType;

    @ApiModelProperty(value = "题目类型Id")
    private String questionTypeId;

    @ApiModelProperty(value = "题目描述（纯文本）")
    private String originalContent;

    @ApiModelProperty(value = "题目描述（HTML）")
    private String formatContent;

    @ApiModelProperty(value = "题目难度")
    private Integer difficulty;

    @ApiModelProperty(value = "是否是文件夹（0-题目，1-文件夹）")
    private Boolean isFolder;

    @ApiModelProperty(value = "题目选项列表")
    private List<QuestionOptionVO> options;

    @ApiModelProperty(value = "题目答案列表")
    private List<QuestionAnswerVO> answers;

    @ApiModelProperty(value = "题目答案在题目选项列表中的下标")
    private List<Integer> answerIndex;

    @ApiModelProperty(value = "题目答案内容，当题目没有选项时")
    private String answerValue;

    @ApiModelProperty(value = "题目答案解析")
    private String answerAnalyze;

    @ApiModelProperty(value = "题目关联知识点Id集合")
    private List<String> topicIds;

}
