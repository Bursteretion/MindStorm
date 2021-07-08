package cn.lwjppz.mindstorm.education.model.dto.academy;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
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
public class AcademyDetailDTO {

    private String id;

    private String pid;

    private String name;

    private Integer status;

    private Integer sort;

    private Date gmtCreate;

    private List<AcademyDetailDTO> children;

}
