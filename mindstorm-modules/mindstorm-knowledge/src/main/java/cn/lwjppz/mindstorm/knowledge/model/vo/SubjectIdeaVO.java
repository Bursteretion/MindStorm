package cn.lwjppz.mindstorm.knowledge.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * <p></p>
 *
 * @author : lwj
 * @since : 2021-06-05
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SubjectIdeaVO {

    private String subjectId;

    private List<String> ideaIds;
    
}
