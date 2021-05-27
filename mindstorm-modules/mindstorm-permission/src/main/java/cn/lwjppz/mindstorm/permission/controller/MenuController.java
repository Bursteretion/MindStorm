package cn.lwjppz.mindstorm.permission.controller;


import cn.lwjppz.mindstorm.common.core.support.CommonResult;
import cn.lwjppz.mindstorm.common.security.annotation.PreAuthorize;
import cn.lwjppz.mindstorm.permission.model.dto.menu.MenuDTO;
import cn.lwjppz.mindstorm.permission.model.dto.menu.MenuDetailDTO;
import cn.lwjppz.mindstorm.permission.model.dto.menu.Router;
import cn.lwjppz.mindstorm.permission.model.entity.Menu;
import cn.lwjppz.mindstorm.permission.model.vo.menu.MenuVO;
import cn.lwjppz.mindstorm.permission.model.vo.menu.SearchMenuVO;
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
@Api(tags = "菜单权限控制器")
public class MenuController {

    private final MenuService menuService;

    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @GetMapping("/list")
    @ApiOperation("获取所有菜单（按钮）")
    @PreAuthorize(hasPermission = "permission:menu:list")
    public CommonResult listMenus() {
        List<MenuDTO> menus = menuService.getMenus();
        return CommonResult.ok().data("menus", menus);
    }

    @PostMapping("routers")
    @ApiOperation("根据角色Id集合获取路由表")
    public CommonResult getRouters(@ApiParam("角色Id集合") @RequestBody List<String> roleIds) {
        List<Router> routers = menuService.getRouters(roleIds);
        return CommonResult.ok().data("routes", routers);
    }

    @PostMapping("/list/type")
    @ApiOperation("获取所有指定类型的菜单（按钮）")
    public CommonResult listMenus(@ApiParam("菜单类型集合") @RequestBody List<Integer> types) {
        List<MenuDTO> menus = menuService.getMenus(types);
        return CommonResult.ok().data("treeMenus", menus);
    }

    @PostMapping("/search")
    @ApiOperation("多条件查询菜单信息")
    @PreAuthorize(hasPermission = "permission:menu:query")
    public CommonResult search(@ApiParam("查询菜单信息") @RequestBody SearchMenuVO searchMenuVO) {
        List<MenuDTO> menus = menuService.searchMenus(searchMenuVO);
        return CommonResult.ok().data("searchMenus", menus);
    }

    @PostMapping("/create")
    @ApiOperation("新增菜单（按钮）")
    @PreAuthorize(hasPermission = "permission:menu:add")
    public CommonResult create(@ApiParam("菜单（按钮）信息") @RequestBody MenuVO menuVO) {
        Menu menu = menuService.insertMenu(menuVO);
        return CommonResult.ok().data("menu", menu);
    }

    @GetMapping("/info/{menuId}")
    @ApiOperation("查询菜单（按钮）信息")
    public CommonResult info(@ApiParam("菜单（按钮）Id") @PathVariable("menuId") String menuId) {
        MenuDetailDTO menu =
                menuService.convertToMenuDetailDTO(menuService.convertToMenuDTO(menuService.getMenuById(menuId)));
        return CommonResult.ok().data("menu", menu);
    }

    @PostMapping("/update")
    @ApiOperation("修改菜单信息")
    @PreAuthorize(hasPermission = "permission:menu:update")
    public CommonResult update(@ApiParam("菜单信息") @RequestBody MenuVO menuVO) {
        Menu menu = menuService.updateMenu(menuVO);
        return CommonResult.ok().data("menu", menu);
    }

    @DeleteMapping("/delete/{menuId}")
    @ApiOperation("删除菜单信息")
    @PreAuthorize(hasPermission = "permission:menu:delete")
    public CommonResult delete(@ApiParam("菜单Id") @PathVariable("menuId") String menuId) {
        boolean b = menuService.deleteById(menuId);
        return CommonResult.ok().data("delete", b);
    }

}

