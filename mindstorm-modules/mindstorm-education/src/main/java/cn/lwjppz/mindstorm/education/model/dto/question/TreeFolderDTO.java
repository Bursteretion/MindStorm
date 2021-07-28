package cn.lwjppz.mindstorm.education.model.dto.question;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * <p></p>
 *
 * @author : lwj
 * @since : 2021-07-28
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TreeFolderDTO {

    private String folderId;

    private String pid;

    private String folderName;

    private List<TreeFolderDTO> children;

}
