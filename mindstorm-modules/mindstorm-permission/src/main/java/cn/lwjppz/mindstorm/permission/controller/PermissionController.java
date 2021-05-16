package cn.lwjppz.mindstorm.permission.controller;


import cn.lwjppz.mindstorm.common.core.support.CommonResult;
import cn.lwjppz.mindstorm.permission.model.dto.PermissionDTO;
import cn.lwjppz.mindstorm.permission.model.dto.PermissionDetailDTO;
import cn.lwjppz.mindstorm.permission.model.vo.PermissionVO;
import cn.lwjppz.mindstorm.permission.service.PermissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("permission/permission")
@Api(tags = "权限菜单（按钮）控制器")
public class PermissionController {

    private final PermissionService permissionService;

    public PermissionController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @ApiOperation("获取所有权限菜单（按钮）")
    @GetMapping("/list")
    public CommonResult listPermissions() {
        List<PermissionDTO> permissions = permissionService.getPermissions();
        return CommonResult.ok().data("permissions", permissions);
    }

    @ApiOperation("新增权限菜单（按钮）")
    @PostMapping("/create")
    public CommonResult create(@ApiParam("权限菜单（按钮）信息") @RequestBody PermissionVO permissionVO) {
        PermissionDTO permissionDTO = permissionService.insertPermission(permissionVO);
        return CommonResult.ok().data("permission", permissionDTO);
    }

    @ApiOperation("查询权限菜单（按钮）信息")
    @GetMapping("/info/{permissionId}")
    public CommonResult info(@ApiParam("权限菜单（按钮）Id") @PathVariable("permissionId") String permissionId) {
        PermissionDetailDTO permissionInfo = permissionService.getPermissionInfo(permissionId);
        return CommonResult.ok().data("permission", permissionInfo);
    }

}

