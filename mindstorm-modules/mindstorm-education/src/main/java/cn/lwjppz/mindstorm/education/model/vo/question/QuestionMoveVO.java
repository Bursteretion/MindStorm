package cn.lwjppz.mindstorm.education.model.vo.question;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
public class QuestionMoveVO {

    @ApiModelProperty(value = "文件夹/题目Id")
    private String questionId;

    @ApiModelProperty(value = "要移动到的父文件夹Id")
    private String moveToPid;

}
