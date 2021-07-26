package cn.lwjppz.mindstorm.education.model.vo.question;

import cn.lwjppz.mindstorm.common.core.support.BaseQueryVO;
import cn.lwjppz.mindstorm.education.model.vo.questionoption.QuestionOptionVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;

/**
 * <p></p>
 *
 * @author : lwj
 * @since : 2021-07-21
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class QuestionQueryVO extends BaseQueryVO {

    @ApiModelProperty(value = "所属课程Id")
    private String courseId;

    @ApiModelProperty(value = "上级文件夹Id")
    private String pid;

    @ApiModelProperty(value = "题目类型Id")
    private String questionTypeId;

    @ApiModelProperty(value = "题目知识点Id")
    private String topicId;

    @ApiModelProperty(value = "题目描述（纯文本）")
    private String originContent;

    @ApiModelProperty(value = "题目难度")
    private Integer difficulty;

}
