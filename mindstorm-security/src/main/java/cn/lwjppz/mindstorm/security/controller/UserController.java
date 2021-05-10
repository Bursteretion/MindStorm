package cn.lwjppz.mindstorm.security.controller;


import cn.lwjppz.mindstorm.common.core.support.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author lwj
 * @since 2021-05-09
 */
@Api(tags = "用户控制器")
@RestController
@RequestMapping("/security/user")
public class UserController {

    @ApiOperation("测试方法")
    @GetMapping("/test")
    public CommonResult test() {
        return CommonResult.ok();
    }

}
