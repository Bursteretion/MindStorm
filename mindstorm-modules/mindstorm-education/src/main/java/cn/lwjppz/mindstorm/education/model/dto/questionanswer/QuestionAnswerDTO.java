package cn.lwjppz.mindstorm.education.model.dto.questionanswer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
public class QuestionAnswerDTO {

    private String optionId;

    private String optionName;

    private String value;

    private String analyze;

}
