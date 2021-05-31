package cn.lwjppz.mindstorm.system.model.entity;

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
 * 日志
 * </p>
 *
 * @author lwj
 * @since 2021-05-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("mss_log")
@ApiModel(value = "Log对象", description = "系统日志对象")
public class Log implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "日志Id")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty(value = "日志类型")
    private Integer logType;

    @ApiModelProperty(value = "请求方式")
    private String requestMethod;

    @ApiModelProperty(value = "请求参数")
    private String requestParam;

    @ApiModelProperty(value = "返回结果")
    private String responseResult;

    @ApiModelProperty(value = "Ip地址")
    private String ipAddress;

    @ApiModelProperty(value = "操作用户")
    private String operateUser;

    @ApiModelProperty(value = "操作方法")
    private String operateMethod;

    @ApiModelProperty(value = "操作地点")
    private String operateAddress;

    @ApiModelProperty(value = "请求执行时间")
    private String requestTime;

    @ApiModelProperty(value = "操作状态（0失败，1正常）")
    private Boolean status;

    @ApiModelProperty(value = "操作信息")
    private String message;

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
