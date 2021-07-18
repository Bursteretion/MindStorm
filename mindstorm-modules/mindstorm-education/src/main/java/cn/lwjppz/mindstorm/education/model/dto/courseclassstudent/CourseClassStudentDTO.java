package cn.lwjppz.mindstorm.education.model.dto.courseclassstudent;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * <p></p>
 *
 * @author : lwj
 * @since : 2021-07-17
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CourseClassStudentDTO {

    private String id;

    private String sno;

    private String realName;

    private String academyName;

    private String professionName;

    private Date gmtCreate;

}
