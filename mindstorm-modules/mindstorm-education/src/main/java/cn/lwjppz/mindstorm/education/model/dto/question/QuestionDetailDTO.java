package cn.lwjppz.mindstorm.education.model.dto.question;

import cn.lwjppz.mindstorm.education.model.dto.questionanswer.QuestionAnswerDTO;
import cn.lwjppz.mindstorm.education.model.dto.questionoption.QuestionOptionDTO;
import cn.lwjppz.mindstorm.education.model.dto.questiontopic.QuestionTopicDTO;
import cn.lwjppz.mindstorm.education.model.dto.topic.TopicDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

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
public class QuestionDetailDTO {

    private String id;

    private String formatContent;

    private Integer questionType;

    private Integer difficulty;

    private List<QuestionOptionDTO> options;

    private List<QuestionAnswerDTO> answers;

    private List<Integer> answerIndex;

    private String answerValue;

    private String answerAnalyze;

    private List<TopicDTO> topics;

}
