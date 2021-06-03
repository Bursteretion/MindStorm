package cn.lwjppz.mindstorm.knowledge.model.dto.subject;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

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
public class SubjectDTO {

    private String id;

    private String name;

    private Integer level;

    private Date gmtCreate;

}
