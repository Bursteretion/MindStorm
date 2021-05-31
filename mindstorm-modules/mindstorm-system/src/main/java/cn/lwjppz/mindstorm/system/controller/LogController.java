package cn.lwjppz.mindstorm.system.controller;


import cn.lwjppz.mindstorm.api.system.model.SysLog;
import cn.lwjppz.mindstorm.common.core.support.CommonResult;
import cn.lwjppz.mindstorm.system.model.entity.Log;
import cn.lwjppz.mindstorm.system.service.LogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 日志服务Api接口
 * </p>
 *
 * @author lwj
 * @since 2021-05-30
 */
@RestController
@RequestMapping("/system/log")
@Api("日志服务控制器")
public class LogController {

    private final LogService logService;

    public LogController(LogService logService) {
        this.logService = logService;
    }

    @ApiOperation("保存系统日志")
    @PostMapping("/create")
    public CommonResult create(@ApiParam("日志信息") @RequestBody SysLog sysLog) {
        Log log = logService.saveLog(sysLog);
        return CommonResult.ok().data("log", log);
    }

}

