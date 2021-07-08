package cn.lwjppz.mindstorm.education.model.dto.profession;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * <p></p>
 *
 * @author : lwj
 * @since : 2021-06-08
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProfessionDTO {

    private String id;

    private String academyId;

    private String academyName;

    private String name;

    private Integer status;

    private Integer sort;

    private Date gmtCreate;

}
