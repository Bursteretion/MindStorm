package cn.lwjppz.mindstorm.permission.controller;


import cn.lwjppz.mindstorm.common.core.support.CommonResult;
import cn.lwjppz.mindstorm.permission.model.dto.roleMenu.RoleMenuDTO;
import cn.lwjppz.mindstorm.permission.service.RoleMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 角色权限表 前端控制器
 * </p>
 *
 * @author lwj
 * @since 2021-05-09
 */
@RestController
@RequestMapping("/permission/role-menu")
@Api(tags = "角色菜单分配控制器")
public class RoleMenuController {

    private final RoleMenuService roleMenuService;

    public RoleMenuController(RoleMenuService roleMenuService) {
        this.roleMenuService = roleMenuService;
    }

    @ApiOperation("根据角色Id获取该角色所有菜单")
    @GetMapping("/roleMenuTreeSelect/{roleId}")
    public CommonResult roleMenu(@ApiParam("角色Id") @PathVariable("roleId") String roleId) {
        RoleMenuDTO menus = roleMenuService.getMenusByRoleId(roleId);
        return CommonResult.ok().data("menus", menus);
    }

    @ApiOperation("为角色分配菜单")
    @PostMapping("/distribute/{roleId}")
    public CommonResult distributeMenu(@ApiParam("角色Id") @PathVariable("roleId") String roleId,
                                       @ApiParam("菜单") @RequestBody List<String> menus) {
        boolean b = roleMenuService.insertRoleMenu(roleId, menus);
        return CommonResult.ok().data("distribute", b);
    }
}

