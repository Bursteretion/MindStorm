package cn.lwjppz.mindstorm.common.core.support;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.http.MediaType;

/**
 * @author : lwj
 * @since : 2021-07-15
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UploadResult {

    private String filename;

    private String filePath;

    private String key;

    private String thumbPath;

    private String suffix;

    private MediaType mediaType;

    private Long size;

    private Integer width;

    private Integer height;

}
