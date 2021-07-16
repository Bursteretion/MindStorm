package cn.lwjppz.mindstorm.education.model.dto.team;

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
public class TeamDetailDTO {

    private String id;

    private String name;

    private Integer status;

    private Integer sort;

}
