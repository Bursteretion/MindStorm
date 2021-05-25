package cn.lwjppz.mindstorm.common.security.handler;

import cn.lwjppz.mindstorm.common.core.enums.ResultStatus;
import cn.lwjppz.mindstorm.common.core.exception.AbstractMindStormException;
import cn.lwjppz.mindstorm.common.core.exception.PreAuthorizeException;
import cn.lwjppz.mindstorm.common.core.support.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.util.Assert;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * <p></p>
 *
 * @author : lwj
 * @since : 2021-05-24
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 基础异常
     */
    @ExceptionHandler(AbstractMindStormException.class)
    public CommonResult error(AbstractMindStormException e) {
        log.error("exception message：{}", e.getMessage());
        return CommonResult.error().code(e.getStatus().getCode()).message(e.getStatus().getMessage());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CommonResult handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.error("exception message：{}", e.getMessage());
        CommonResult commonResult = handleBaseException(e);
        return commonResult.code(HttpStatus.BAD_GATEWAY.value());
    }

    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public CommonResult handleHttpMediaTypeNotAcceptableException(HttpMediaTypeNotAcceptableException e) {
        log.error("exception message：{}", e.getMessage());
        CommonResult commonResult = handleBaseException(e);
        return commonResult.code(HttpStatus.NOT_ACCEPTABLE.value());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CommonResult handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        log.error("exception message：{}", e.getMessage());
        CommonResult commonResult = handleBaseException(e);
        return commonResult.code(HttpStatus.BAD_REQUEST.value()).message("缺失请求主体");
    }

    @ExceptionHandler(Exception.class)
    public CommonResult error(Exception e) {
        log.error("exception message：{}", e.getMessage());
        return CommonResult.error().message("服务器又耍流氓了..");
    }

    /**
     * 权限异常
     */
    @ExceptionHandler(PreAuthorizeException.class)
    public CommonResult preAuthorizeException(PreAuthorizeException e) {
        return CommonResult.error().codeAndMessage(ResultStatus.FORBIDDEN);
    }

    private CommonResult handleBaseException(Throwable t) {
        Assert.notNull(t, "Throwable must not be null");

        CommonResult commonResult = CommonResult.error();
        commonResult.setMessage(t.getMessage());

        if (log.isDebugEnabled()) {
            log.error("Captured an exception:", t);
            commonResult.setDevMessage(ExceptionUtils.getStackTrace(t));
        } else {
            log.error("Captured an exception: [{}]", t.getMessage());
        }

        return commonResult;
    }
}
