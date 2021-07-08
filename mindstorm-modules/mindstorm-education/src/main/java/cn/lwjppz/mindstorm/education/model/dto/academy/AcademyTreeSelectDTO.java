package cn.lwjppz.mindstorm.education.model.dto.academy;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * <p></p>
 *
 * @author : lwj
 * @since : 2021-07-08
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AcademyTreeSelectDTO {

    private String title;

    private String value;

    private List<AcademyTreeSelectDTO> children;

}
