package cn.lwjppz.mindstorm.education.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * <p></p>
 *
 * @author : lwj
 * @since : 2021-06-07
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AcademyDTO {

    private String id;

    private String name;

    private Integer sort;

    private Date gmtCreate;

}
