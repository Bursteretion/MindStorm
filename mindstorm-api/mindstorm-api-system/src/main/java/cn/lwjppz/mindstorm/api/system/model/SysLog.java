package cn.lwjppz.mindstorm.api.system.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 系统日志
 * </p>
 *
 * @author : lwj
 * @since : 2021-05-30
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SysLog implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 日志类型
     */
    private Integer logType;

    /**
     * 请求方式
     */
    private String requestMethod;

    /**
     * 请求参数
     */
    private String requestParam;

    /**
     * 返回结果
     */
    private String responseResult;

    /**
     * 请求Ip地址
     */
    private String ipAddress;

    /**
     * 操作用户
     */
    private String operateUser;

    /**
     * 操作时间
     */
    private Date operateTime;

    /**
     * 操作模块
     */
    private String operateModule;

    /**
     * 操作方法
     */
    private String operateMethod;

    /**
     * 操作地址
     */
    private String operateLocation;

    /**
     * 请求执行时间
     */
    private String requestTime;

    /**
     * 操作状态
     */
    private Integer status;

    /**
     * 错误消息
     */
    private String errorMessage;

}
