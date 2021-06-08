package cn.lwjppz.mindstorm.common.log.annotation;

import cn.lwjppz.mindstorm.common.core.enums.type.LogType;

import java.lang.annotation.*;

/**
 * <p>
 * 自定义日志注解
 * </p>
 *
 * @author : lwj
 * @since : 2021-06-01
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {

    /**
     * 模块
     */
    String operateModule() default "";

    /**
     * 日志类型
     */
    LogType logType() default LogType.OTHER;

    /**
     * 是否保存请求参数
     */
    boolean saveRequestParams() default true;

    /**
     * 是否保存返回结果
     */
    boolean saveResponseResult() default true;

    /**
     * 是否保存请求Ip地址所在地域信息
     */
    boolean saveOperateLocation() default true;

}
