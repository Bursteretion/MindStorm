package cn.lwjppz.mindstorm.education.model.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 *
 * </p>
 *
 * @author lwj
 * @since 2021-07-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("mse_course")
@ApiModel(value = "Course对象", description = "课程表")
public class Course implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "课程Id")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty(value = "课程所有者Id")
    private String userId;

    @ApiModelProperty(value = "课程名称")
    private String name;

    @ApiModelProperty(value = "开设该课程的教师名称")
    private String teacherName;

    @ApiModelProperty(value = "课程所属院系Id")
    private String academyId;

    @ApiModelProperty(value = "课程封面")
    private String thumbnail;

    @ApiModelProperty(value = "是否加密考试、题库（0表示不加密，1 表示加密）")
    private Integer encryption;

    @ApiModelProperty(value = "课程说明")
    private String description;

    @ApiModelProperty(value = "课程状态（0 禁用，1 正常）")
    private Integer status;

    @ApiModelProperty(value = "逻辑删除 1（true）已删除， 0（false）未删除")
    @TableLogic
    private Boolean isDeleted;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;

}
