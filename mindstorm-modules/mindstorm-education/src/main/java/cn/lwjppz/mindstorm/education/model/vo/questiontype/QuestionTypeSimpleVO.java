package cn.lwjppz.mindstorm.education.model.vo.questiontype;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p></p>
 *
 * @author : lwj
 * @since : 2021-07-20
 */
@Data
public class QuestionTypeSimpleVO {

    @ApiModelProperty(value = "题型Id")
    private String id;

    @ApiModelProperty(value = "题型名称")
    private String name;

}
