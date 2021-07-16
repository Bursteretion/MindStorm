package cn.lwjppz.mindstorm.education.model.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 课程班级表
 * </p>
 *
 * @author lwj
 * @since 2021-07-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("mse_course_class")
@ApiModel(value = "CourseClass对象", description = "课程班级表")
public class CourseClass implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "课程班级Id")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty(value = "课程Id")
    private String courseId;

    @ApiModelProperty(value = "班级名称")
    private String className;

    @ApiModelProperty(value = "班级排序")
    private Integer sort;

    @ApiModelProperty(value = "班级邀请码")
    private String invitationCode;

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
