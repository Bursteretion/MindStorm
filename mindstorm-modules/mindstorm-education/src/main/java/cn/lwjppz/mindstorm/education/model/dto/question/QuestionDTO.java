package cn.lwjppz.mindstorm.education.model.dto.question;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

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
public class QuestionDTO {

    private String id;

    private String content;

    private String questionType;

    private String difficulty;

    private Integer usageAmount;

    private String userRealName;

    private String courseName;

    private Boolean isFolder;

    private Date gmtCreate;

}
