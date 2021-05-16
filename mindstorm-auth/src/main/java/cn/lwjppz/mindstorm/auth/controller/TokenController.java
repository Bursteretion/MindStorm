package cn.lwjppz.mindstorm.auth.controller;

import cn.lwjppz.mindstorm.auth.model.LoginBody;
import cn.lwjppz.mindstorm.auth.service.LoginService;
import cn.lwjppz.mindstorm.common.core.support.CommonResult;
import cn.lwjppz.mindstorm.common.security.service.TokenService;
import cn.lwjppz.mindstorm.permission.model.dto.LoginUserDTO;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * Token 控制
 * </p>
 *
 * @author : lwj
 * @since : 2021-05-15
 */
@RestController
@RequestMapping("/auth")
public class TokenController {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private LoginService loginService;

    @ApiOperation("用户登录")
    @PostMapping("/login")
    public CommonResult login(@ApiParam("登录信息") @RequestBody LoginBody loginBody) {
        LoginUserDTO userDTO = loginService.login(loginBody.getUsername(), loginBody.getPassword());
        return CommonResult.ok().data(tokenService.createToken(userDTO));
    }

    @ApiOperation("获取用户信息")
    @GetMapping("/info")
    public CommonResult info(HttpServletRequest request) {
        LoginUserDTO loginUser = tokenService.getLoginUser();
        return CommonResult.ok().data("user", loginUser);
    }

    @ApiOperation("用户注销登录")
    @DeleteMapping("/logout")
    public CommonResult logout(HttpServletRequest request) {
        LoginUserDTO loginUser = tokenService.getLoginUser(request);
        if (ObjectUtils.isNotNull(loginUser)) {
            String username = loginUser.getUsername();
            // 删除用户缓存记录
            tokenService.delLoginUser(loginUser.getToken());
            // 记录用户退出日志
            loginService.logout(username);
        }
        return CommonResult.ok();
    }
}
