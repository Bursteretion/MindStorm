package cn.lwjppz.mindstorm.knowledge.model.vo.idea;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

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
@ApiModel("知识点提交表单信息")
public class IdeaVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "知识点Id")
    private String id;

    @ApiModelProperty(value = "上级知识点Id")
    private String pid;

    @ApiModelProperty(value = "知识点名称")
    private String name;

    @ApiModelProperty(value = "知识点描述")
    private String description;

    @ApiModelProperty(value = "排序")
    private Integer sort;

}
