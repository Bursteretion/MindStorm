package cn.lwjppz.mindstorm.education.model.dto.questiontype;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p></p>
 *
 * @author : lwj
 * @since : 2021-07-20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionTypeSelectDTO {

    private String id;

    private String name;

    private Integer usageAmount;

}
