package cn.lwjppz.mindstorm.education.model.entity;

import java.math.BigDecimal;

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
 * 试卷表
 * </p>
 *
 * @author lwj
 * @since 2021-07-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("mse_exam_paper")
@ApiModel(value = "ExamPaper对象", description = "试卷表")
public class ExamPaper implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "试卷Id")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty(value = "上级文件夹Id")
    private String pid;

    @ApiModelProperty(value = "试卷所属课程Id")
    private String courseId;

    @ApiModelProperty(value = "试卷创建用户Id")
    private String userId;

    @ApiModelProperty(value = "试卷（文件夹）名称")
    private String title;

    @ApiModelProperty(value = "试卷题目数量")
    private Integer questionCount;

    @ApiModelProperty(value = "试卷总分")
    private BigDecimal totalScore;

    @ApiModelProperty(value = "试卷发放次数")
    private Integer publishCount;

    @ApiModelProperty(value = "试卷难度（0-简单，1-中等，2-困难）")
    private Integer difficulty;

    @ApiModelProperty(value = "试卷排序")
    private Integer sort;

    @ApiModelProperty(value = "是否是文件夹（0-题目，1-文件夹）")
    private Boolean isFolder;

    @ApiModelProperty(value = "试卷状态（0-未完成，1-完成）")
    private Boolean status;

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
