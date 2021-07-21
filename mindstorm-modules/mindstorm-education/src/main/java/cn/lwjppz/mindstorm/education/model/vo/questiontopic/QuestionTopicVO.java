package cn.lwjppz.mindstorm.education.model.vo.questiontopic;

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
public class QuestionTopicVO {

    @ApiModelProperty(value = "题目Id")
    private String questionId;

    @ApiModelProperty(value = "知识点Id集合")
    private List<String> topicIds;

}
