package cn.lwjppz.mindstorm.permission.controller;


import cn.lwjppz.mindstorm.common.core.support.CommonResult;
import cn.lwjppz.mindstorm.permission.model.dto.menu.MenuDTO;
import cn.lwjppz.mindstorm.permission.model.dto.menu.MenuDetailDTO;
import cn.lwjppz.mindstorm.permission.model.vo.menu.MenuVO;
import cn.lwjppz.mindstorm.permission.service.MenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 权限 前端控制器
 * </p>
 *
 * @author lwj
 * @since 2021-05-09
 */
@RestController
@RequestMapping("permission/menu")
@Api(tags = "菜单（按钮）控制器")
public class PermissionController {

    private final MenuService permissionService;

    public PermissionController(MenuService permissionService) {
        this.permissionService = permissionService;
    }

    @ApiOperation("获取所有权限菜单（按钮）")
    @GetMapping("/list")
    public CommonResult listPermissions() {
        List<MenuDTO> permissions = permissionService.getPermissions();
        return CommonResult.ok().data("permissions", permissions);
    }

    @ApiOperation("新增权限菜单（按钮）")
    @PostMapping("/create")
    public CommonResult create(@ApiParam("权限菜单（按钮）信息") @RequestBody MenuVO permissionVO) {
        MenuDTO permissionDTO = permissionService.insertPermission(permissionVO);
        return CommonResult.ok().data("permission", permissionDTO);
    }

    @ApiOperation("查询权限菜单（按钮）信息")
    @GetMapping("/info/{permissionId}")
    public CommonResult info(@ApiParam("权限菜单（按钮）Id") @PathVariable("permissionId") String permissionId) {
        MenuDetailDTO permissionInfo = permissionService.getPermissionInfo(permissionId);
        return CommonResult.ok().data("permission", permissionInfo);
    }

}

