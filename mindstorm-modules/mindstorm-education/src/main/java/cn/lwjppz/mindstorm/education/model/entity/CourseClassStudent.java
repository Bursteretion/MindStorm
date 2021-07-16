package cn.lwjppz.mindstorm.education.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 课程班级学生表
 * </p>
 *
 * @author lwj
 * @since 2021-07-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("mse_course_class_student")
@ApiModel(value = "CourseClassStudent对象", description = "课程班级学生表")
public class CourseClassStudent implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "Id")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty(value = "班级Id")
    private String classId;

    @ApiModelProperty(value = "学生Id")
    private String studentId;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;


}
