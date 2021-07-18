package cn.lwjppz.mindstorm.education.model.dto.courseclass;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * <p></p>
 *
 * @author : lwj
 * @since : 2021-07-15
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CourseClassDTO {

    private String id;

    private String className;

    private Integer studentCount;

    private Integer sort;

    private String invitationCode;

}
