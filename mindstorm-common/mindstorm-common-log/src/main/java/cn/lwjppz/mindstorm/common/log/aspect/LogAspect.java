package cn.lwjppz.mindstorm.common.log.aspect;

import cn.lwjppz.mindstorm.api.system.model.SysLog;
import cn.lwjppz.mindstorm.common.core.enums.status.LogStatus;
import cn.lwjppz.mindstorm.common.core.utils.IpUtils;
import cn.lwjppz.mindstorm.common.core.utils.SecurityUtils;
import cn.lwjppz.mindstorm.common.core.utils.ServletUtils;
import cn.lwjppz.mindstorm.common.core.utils.StringUtils;
import cn.lwjppz.mindstorm.common.log.annotation.Log;
import cn.lwjppz.mindstorm.common.log.service.AsyncLogService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.*;

/**
 * <p>
 * 操作日志切面拦截处理
 * </p>
 *
 * @author : lwj
 * @since : 2021-06-01
 */
@Aspect
@Component
public class LogAspect {

    private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);

    private final AsyncLogService asyncLogService;

    private Long startTime;

    public LogAspect(AsyncLogService asyncLogService) {
        this.asyncLogService = asyncLogService;
    }

    /**
     * 切入点
     */
    @Pointcut("@annotation(cn.lwjppz.mindstorm.common.log.annotation.Log)")
    public void logPointCut() {
    }

    @Before("logPointCut()")
    public void before(JoinPoint point) {
        /* 方法执行前开始记时间 */
        startTime = System.currentTimeMillis();
    }

    /**
     * 拦截异常操作
     *
     * @param point 切点
     * @param e     异常
     */
    @AfterThrowing(value = "logPointCut()", throwing = "e")
    public void afterThrowing(JoinPoint point, Exception e) {
        handleLog(point, e, null);
    }

    /**
     * 处理完请求后执行
     *
     * @param point  切断
     * @param result 返回结果
     */
    @AfterReturning(pointcut = "logPointCut()", returning = "result")
    public void doAfterReturning(JoinPoint point, Object result) {
        handleLog(point, null, result);
    }

    public void handleLog(final JoinPoint point, final Exception e, Object result) {
        try {
            // 获取注解
            Log logAnnotation = getAnnotationLog(point);
            if (null == logAnnotation) {
                return;
            }

            /* 封装系统日志 */
            SysLog sysLog = new SysLog();
            // 设置日志类型
            sysLog.setLogType(logAnnotation.logType().getValue());

            // 设置操作时间
            sysLog.setOperateTime(new Date());

            // 设置模块名称
            sysLog.setOperateModule(logAnnotation.operateModule());

            // 设置状态
            sysLog.setStatus(LogStatus.SUCCESS.getValue());

            // 设置操作用户
            String username = SecurityUtils.getUsername();
            if (StringUtils.isNotEmpty(username)) {
                sysLog.setOperateUser(username);
            }

            // 设置异常信息
            if (null != e) {
                sysLog.setStatus(LogStatus.FAILURE.getValue());
                sysLog.setErrorMessage(StringUtils.substring(e.getMessage(), 0, 2000));
            }

            // 设置请求方法
            String className = point.getSignature().getDeclaringTypeName();
            String methodName = point.getSignature().getName();
            sysLog.setOperateMethod(className + "." + methodName + "()");

            // 设置请求方式（GET、POST...)
            sysLog.setRequestMethod(Objects.requireNonNull(ServletUtils.getRequest()).getMethod());

            // 设置IP
            JSONObject configs = JSON.parseObject(IpUtils.getAddresses(IpUtils.getOutIPV4()));
            sysLog.setIpAddress(configs.getString("ip"));

            // 检查是否需要保存操作地点
            if (logAnnotation.saveOperateLocation()) {
                sysLog.setOperateLocation(configs.getString("addr").split(" ")[0]);
            }

            // 检查是否需要保存请求参数
            if (logAnnotation.saveRequestParams()) {
                setRequestParams(point, sysLog);
            }

            // 检查是否需要保持返回结果
            if (logAnnotation.saveResponseResult()) {
                sysLog.setResponseResult(JSON.toJSONString(result));
            }

            // 设置方法执行时间
            sysLog.setRequestTime(String.valueOf(System.currentTimeMillis() - startTime));

            // 调用远程日志服务保存
            asyncLogService.saveSysLog(sysLog);
        } catch (Exception exp) {
            // 打印异常日志信息
            logger.error("==前置通知异常==");
            logger.error("异常信息:{}", exp.getMessage());
            exp.printStackTrace();
        }
    }

    /**
     * 获取日志注解
     *
     * @param point 切点
     * @return 如果存在则返回
     */
    private Log getAnnotationLog(JoinPoint point) {
        Signature signature = point.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        if (null != method) {
            return method.getAnnotation(Log.class);
        }
        return null;
    }

    /**
     * 获取请求的参数，放到log中
     *
     * @param sysLog 操作日志
     */
    private void setRequestParams(JoinPoint joinPoint, SysLog sysLog) {
        String requestMethod = sysLog.getRequestMethod();
        if (HttpMethod.PUT.name().equals(requestMethod) || HttpMethod.POST.name().equals(requestMethod)) {
            String params = argsToJson(joinPoint.getArgs());
            sysLog.setRequestParam(StringUtils.substring(params, 0, 2000));
        }
    }

    /**
     * 拼装请求参数
     *
     * @param args 参数数组
     * @return a json string
     */
    private String argsToJson(Object[] args) {
        StringBuilder sb = new StringBuilder();
        sb.append("{ ");
        for (Object arg : args) {
            if (StringUtils.isNotNull(arg) && !isFilterObject(arg)) {
                sb.append(JSON.toJSONString(arg)).append(" ");
            }
        }
        return sb.append("}").toString();
    }

    /**
     * 判断是否需要过滤的对象。
     *
     * @param o 对象信息。
     * @return 如果是需要过滤的对象，则返回true；否则返回false。
     */
    @SuppressWarnings("rawtypes")
    public boolean isFilterObject(final Object o) {
        Class<?> clazz = o.getClass();
        if (clazz.isArray()) {
            return clazz.getComponentType().isAssignableFrom(MultipartFile.class);
        } else if (Collection.class.isAssignableFrom(clazz)) {
            Collection collection = (Collection) o;
            for (Object value : collection) {
                if (value instanceof MultipartFile) {
                    return true;
                }
            }
        } else if (Map.class.isAssignableFrom(clazz)) {
            Map map = (Map) o;
            for (Object value : map.entrySet()) {
                Map.Entry entry = (Map.Entry) value;
                if (entry.getValue() instanceof MultipartFile) {
                    return true;
                }
            }
        }
        return o instanceof MultipartFile || o instanceof HttpServletRequest || o instanceof HttpServletResponse
                || o instanceof BindingResult;
    }

}
