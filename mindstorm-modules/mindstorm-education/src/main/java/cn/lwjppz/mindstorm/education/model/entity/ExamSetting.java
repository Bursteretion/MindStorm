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
 * 考试设置表
 * </p>
 *
 * @author lwj
 * @since 2021-07-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("mse_exam_setting")
@ApiModel(value = "ExamSetting对象", description = "考试设置表")
public class ExamSetting implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "设置Id")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty(value = "考试试卷Id")
    private String examPaperId;

    @ApiModelProperty(value = "考试发放班级Id集合")
    private String publishClass;

    @ApiModelProperty(value = "考试开始时间")
    private Date startTime;

    @ApiModelProperty(value = "考试结束时间")
    private Date endTime;

    @ApiModelProperty(value = "考试限时")
    private Integer limitTime;

    @ApiModelProperty(value = "是否立即发放（0-否，1-是）")
    private Boolean immediatePublish;

    @ApiModelProperty(value = "限时提交时间（考试开始xx分钟内不允许提交）")
    private Integer submitLimitTime;

    @ApiModelProperty(value = "限时进入时间（开考xx分钟后不允许参加考试）")
    private Integer entryLimitTime;

    @ApiModelProperty(value = "是否打乱题目顺序（学生接收到的题目显示顺序不同）")
    private Boolean disorderQuestion;

    @ApiModelProperty(value = "是否打乱选项顺序（学生接收到的题目选项顺序不同）")
    private Boolean disorderOption;

    @ApiModelProperty(value = "及格标准分数")
    private BigDecimal passingStandardScore;

    @ApiModelProperty(value = "考试到达截止时间后是否自动提交")
    private Boolean expiredAutoSubmit;

    @ApiModelProperty(value = "是否允许学生重考")
    private Boolean allowRetest;

    @ApiModelProperty(value = "允许重考次数")
    private Integer retestCount;

    @ApiModelProperty(value = "学生重考是否保留前一次考试的作答记录")
    private Boolean keepLastAnswer;

    @ApiModelProperty(value = "最终成绩（0-最后一次考试成绩，1-最高成绩）")
    private Boolean finalScoreStandard;

    @ApiModelProperty(value = "是否允许学生考后查看试卷")
    private Boolean allowLookPaper;

    @ApiModelProperty(value = "是否允许查看答案")
    private Boolean allowLookAnswer;

    @ApiModelProperty(value = "查看答案标准（0-学生提交后，1-考试截止后）")
    private Boolean lookAnswerStandard;

    @ApiModelProperty(value = "是否允许学生查看分数")
    private Boolean allowLookScore;

    @ApiModelProperty(value = "是否允许学生查看排名")
    private Boolean allowLookRank;

    @ApiModelProperty(value = "是否允许学生粘贴答案")
    private Boolean allowPaste;

    @ApiModelProperty(value = "是否发通知提醒")
    private Boolean sendNotice;

    @ApiModelProperty(value = "通知内容")
    private String noticeContent;

    @ApiModelProperty(value = "是否进入考试需要考试码")
    private Boolean needExamCode;

    @ApiModelProperty(value = "考试码")
    private String examCode;

    @ApiModelProperty(value = "考试码到期时间")
    private Date examCodeExpiredTime;

    @ApiModelProperty(value = "是否限制IP参加考试")
    private Boolean limitIp;

    @ApiModelProperty(value = "允许的IP")
    private String allowIp;

    @ApiModelProperty(value = "是否填空类型的题目设为主观题")
    private Boolean fillToSubjective;

    @ApiModelProperty(value = "填空题答案是否不区分大小写")
    private Boolean fillIgnoreCase;

    @ApiModelProperty(value = "多选题未选全给一半分")
    private Boolean multipleHalfScore;

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
