package cn.lwjppz.mindstorm.education.model.vo.academy;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


/**
 * <p></p>
 *
 * @author : lwj
 * @since : 2021-06-07
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@ApiModel("学院表单提交信息")
public class AcademyVO {

    @ApiModelProperty("学院Id")
    private String id;

    @ApiModelProperty("学院名称")
    private String name;

    @ApiModelProperty("排序")
    private Integer sort;

}
