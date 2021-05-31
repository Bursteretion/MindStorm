package cn.lwjppz.mindstorm.system.controller;


import cn.lwjppz.mindstorm.common.core.support.CommonResult;
import cn.lwjppz.mindstorm.system.model.entity.Visit;
import cn.lwjppz.mindstorm.system.service.VisitService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 系统访问记录服务Api接口
 * </p>
 *
 * @author lwj
 * @since 2021-05-31
 */
@RestController
@RequestMapping("/system/visit")
@Api(tags = "访问记录Api接口")
public class VisitController {

    private final VisitService visitService;

    public VisitController(VisitService visitService) {
        this.visitService = visitService;
    }

    @ApiOperation("保存访问记录")
    @GetMapping("/create")
    public CommonResult create(@ApiParam("用户名") @RequestParam("username") String username,
                               @ApiParam("状态") @RequestParam("status") Integer status,
                               @ApiParam("提示消息") @RequestParam("message") String message) {
        Visit visit = visitService.saveVisit(username, status, message);
        return CommonResult.ok().data("visit", visit);
    }

}

