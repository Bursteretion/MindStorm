package cn.lwjppz.mindstorm.education.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 题目表
 * </p>
 *
 * @author lwj
 * @since 2021-07-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("mse_question")
@ApiModel(value = "Question对象", description = "题目表")
public class Question implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "题目Id")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty(value = "当有上级文件夹时，值为上级Id")
    private String pid;

    @ApiModelProperty(value = "所属课程Id")
    private String courseId;

    @ApiModelProperty(value = "创建用户Id")
    private String userId;

    @ApiModelProperty(value = "题目类型Id")
    private String questionTypeId;

    @ApiModelProperty(value = "题目描述")
    private String content;

    @ApiModelProperty(value = "题目被使用量")
    private Integer usageAmount;

    @ApiModelProperty(value = "题目难度")
    private Integer difficulty;

    @ApiModelProperty(value = "是否是文件夹（0-题目，1-文件夹）")
    private Boolean isFolder;

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
