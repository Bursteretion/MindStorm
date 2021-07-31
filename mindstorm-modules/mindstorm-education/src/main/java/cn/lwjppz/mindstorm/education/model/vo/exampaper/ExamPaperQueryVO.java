package cn.lwjppz.mindstorm.education.model.vo.exampaper;

import cn.lwjppz.mindstorm.common.core.support.BaseQueryVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.math.BigDecimal;

/**
 * <p></p>
 *
 * @author : lwj
 * @since : 2021-07-31
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ExamPaperQueryVO extends BaseQueryVO {

    @ApiModelProperty(value = "课程Id")
    private String courseId;

    @ApiModelProperty(value = "试卷标题")
    private String title;

}
