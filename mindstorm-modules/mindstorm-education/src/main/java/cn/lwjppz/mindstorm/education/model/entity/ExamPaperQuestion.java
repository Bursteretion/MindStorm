package cn.lwjppz.mindstorm.education.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 试卷题目表
 * </p>
 *
 * @author lwj
 * @since 2021-07-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("mse_exam_paper_question")
@ApiModel(value = "ExamPaperQuestion对象", description = "试卷题目表")
public class ExamPaperQuestion implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "Id")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty(value = "试卷Id")
    private String examPaperId;

    @ApiModelProperty(value = "题目Id")
    private String questionId;

    @ApiModelProperty(value = "题目分数")
    private BigDecimal score;

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
