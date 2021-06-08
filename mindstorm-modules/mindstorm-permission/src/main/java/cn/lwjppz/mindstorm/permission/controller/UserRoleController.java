package cn.lwjppz.mindstorm.permission.controller;


import cn.lwjppz.mindstorm.common.core.enums.type.LogType;
import cn.lwjppz.mindstorm.common.core.support.CommonResult;
import cn.lwjppz.mindstorm.common.log.annotation.Log;
import cn.lwjppz.mindstorm.common.security.annotation.PreAuthorize;
import cn.lwjppz.mindstorm.permission.model.dto.userRole.UserRoleDTO;
import cn.lwjppz.mindstorm.permission.service.UserRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 用户角色表 前端控制器
 * </p>
 *
 * @author lwj
 * @since 2021-05-09
 */
@RestController
@RequestMapping("/permission/user-role")
@Api(tags = "用户角色分配控制器")
public class UserRoleController {

    private final UserRoleService userRoleService;

    public UserRoleController(UserRoleService userRoleService) {
        this.userRoleService = userRoleService;
    }

    @ApiOperation("根据用户Id获取该用户所有角色")
    @GetMapping("/userRole/{userId}")
    public CommonResult userRole(@ApiParam("用户Id") @PathVariable("userId") String userId) {
        UserRoleDTO userRoleDTO = userRoleService.getRoleByUserId(userId);
        return CommonResult.ok().data("userRoleDTO", userRoleDTO);
    }

    @ApiOperation("为用户分配角色")
    @PostMapping("/distribute/{userId}")
    @Log(operateModule = "用户角色管理", logType = LogType.UPDATE)
    @PreAuthorize(hasPermission = "permission:user:distribute")
    public CommonResult distributeRole(@ApiParam("用户Id") @PathVariable("userId") String userId,
                                       @ApiParam("菜单") @RequestBody List<String> roles) {
        boolean b = userRoleService.insertUserRole(userId, roles);
        return CommonResult.ok().data("distribute", b);
    }

}
