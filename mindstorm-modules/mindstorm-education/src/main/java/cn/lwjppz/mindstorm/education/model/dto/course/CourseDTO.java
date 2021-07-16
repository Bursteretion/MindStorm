package cn.lwjppz.mindstorm.education.model.dto.course;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

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
public class CourseDTO {

    private String id;

    private String name;

    private String ownerName;

    private String teacherName;

    private String academyName;

    private String thumbnail;

    private Integer classCount;

    private Integer status;

    private Date gmtCreate;

}
