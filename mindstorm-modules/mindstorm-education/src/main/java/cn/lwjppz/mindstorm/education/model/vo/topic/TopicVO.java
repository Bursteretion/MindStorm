package cn.lwjppz.mindstorm.education.model.vo.topic;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * <p></p>
 *
 * @author : lwj
 * @since : 2021-07-21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TopicVO {

    @ApiModelProperty(value = "知识点Id")
    private String id;

    @ApiModelProperty(value = "知识点名称")
    private String name;

}
