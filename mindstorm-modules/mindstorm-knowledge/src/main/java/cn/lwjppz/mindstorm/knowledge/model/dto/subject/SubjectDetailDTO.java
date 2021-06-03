package cn.lwjppz.mindstorm.knowledge.model.dto.subject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * <p></p>
 *
 * @author : lwj
 * @since : 2021-06-02
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SubjectDetailDTO {

    private String id;

    private String name;

    private Integer level;

    private String description;

}
