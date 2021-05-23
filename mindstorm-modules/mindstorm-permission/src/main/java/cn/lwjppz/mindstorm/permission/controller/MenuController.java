package cn.lwjppz.mindstorm.permission.controller;


import cn.lwjppz.mindstorm.common.core.support.CommonResult;
import cn.lwjppz.mindstorm.permission.model.dto.menu.MenuDTO;
import cn.lwjppz.mindstorm.permission.model.dto.menu.MenuDetailDTO;
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
@Api(tags = "菜单（按钮）控制器")
public class MenuController {

    private final MenuService menuService;

    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @ApiOperation("获取所有菜单（按钮）")
    @GetMapping("/list")
    public CommonResult listMenus() {
        List<MenuDTO> menus = menuService.getMenus();
        return CommonResult.ok().data("menus", menus);
    }

    @ApiOperation("根据角色Id集合获取路由表")
    @PostMapping("routers")
    public CommonResult getRouters(@ApiParam("角色Id集合") @RequestBody List<String> roleIds) {
        List<MenuDTO> routers = menuService.getRouters(roleIds);
        return CommonResult.ok().data("routes", routers);
    }

    @ApiOperation("获取所有指定类型的菜单（按钮）")
    @PostMapping("/list/type")
    public CommonResult listMenus(@ApiParam("菜单类型集合") @RequestBody List<Integer> types) {
        List<MenuDTO> menus = menuService.getMenus(types);
        return CommonResult.ok().data("menus", menus);
    }

    @ApiOperation("多条件查询菜单信息")
    @PostMapping("/search")
    public CommonResult search(@ApiParam("查询菜单信息") @RequestBody SearchMenuVO searchMenuVO) {
        List<MenuDTO> menus = menuService.searchMenus(searchMenuVO);
        return CommonResult.ok().data("menus", menus);
    }

    @ApiOperation("新增菜单（按钮）")
    @PostMapping("/create")
    public CommonResult create(@ApiParam("菜单（按钮）信息") @RequestBody MenuVO menuVO) {
        Menu menu = menuService.insertMenu(menuVO);
        return CommonResult.ok().data("menu", menu);
    }

    @ApiOperation("查询菜单（按钮）信息")
    @GetMapping("/info/{menuId}")
    public CommonResult info(@ApiParam("菜单（按钮）Id") @PathVariable("menuId") String menuId) {
        MenuDetailDTO menu =
                menuService.convertToMenuDetailDTO(menuService.convertToMenuDTO(menuService.getMenuById(menuId)));
        return CommonResult.ok().data("menu", menu);
    }

    @ApiOperation("修改菜单信息")
    @PostMapping("/update")
    public CommonResult update(@ApiParam("菜单信息") @RequestBody MenuVO menuVO) {
        Menu menu = menuService.updateMenu(menuVO);
        return CommonResult.ok().data("menu", menu);
    }

    @ApiOperation("删除菜单信息")
    @DeleteMapping("/delete/{menuId}")
    public CommonResult delete(@ApiParam("菜单Id") @PathVariable("menuId") String menuId) {
        boolean b = menuService.deleteById(menuId);
        return CommonResult.ok().data("delete", b);
    }

}

