package cn.lwjppz.mindstorm.permission.service.impl;

import cn.lwjppz.mindstorm.common.core.enums.status.MenuStatus;
import cn.lwjppz.mindstorm.common.core.enums.type.MenuType;
import cn.lwjppz.mindstorm.common.core.exception.EntityNotFoundException;
import cn.lwjppz.mindstorm.common.core.utils.StringUtils;
import cn.lwjppz.mindstorm.permission.mapper.MenuMapper;
import cn.lwjppz.mindstorm.permission.model.dto.menu.MenuDTO;
import cn.lwjppz.mindstorm.permission.model.dto.menu.MenuDetailDTO;
import cn.lwjppz.mindstorm.permission.model.dto.menu.Router;
import cn.lwjppz.mindstorm.permission.model.entity.Menu;
import cn.lwjppz.mindstorm.permission.model.vo.menu.MenuVO;
import cn.lwjppz.mindstorm.permission.model.vo.menu.SearchMenuVO;
import cn.lwjppz.mindstorm.permission.service.MenuService;
import cn.lwjppz.mindstorm.permission.service.RoleMenuService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;


/**
 * <p>
 * 菜单 服务实现类
 * </p>
 *
 * @author lwj
 * @since 2021-05-09
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    private final RoleMenuService roleMenuService;

    public MenuServiceImpl(@Lazy RoleMenuService roleMenuService) {
        this.roleMenuService = roleMenuService;
    }

    @Override
    public List<MenuDTO> getMenus() {
        return generateTreeMenus(Wrappers.lambdaQuery());
    }

    @Override
    public List<MenuDTO> getMenus(@NonNull List<Integer> types) {
        LambdaQueryWrapper<Menu> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.in(Menu::getType, types);

        return generateTreeMenus(queryWrapper);
    }

    @Override
    public List<Router> getRouters(List<String> roleIds) {
        Set<String> menuIdSet = new HashSet<>();
        roleIds.forEach(v -> {
            List<String> menuIds = roleMenuService.getMenuIdsByRoleId(v);
            menuIdSet.addAll(menuIds);
        });

        LambdaQueryWrapper<Menu> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.ne(Menu::getType, MenuType.BUTTON.getValue());
        queryWrapper.eq(Menu::getStatus, MenuStatus.NORMAL.getValue());
        queryWrapper.orderByAsc(Menu::getSort);
        List<Router> menus = convertToRouter(baseMapper.selectList(queryWrapper));

        Map<String, Router> idHash = new HashMap<>(menus.size());
        Map<String, List<Router>> hash = new HashMap<>(menus.size());

        menus.forEach(v -> {
            v.setChildren(new ArrayList<>());
            hash.put(v.getId(), v.getChildren());
            idHash.put(v.getId(), v);
        });

        Set<Map.Entry<String, List<Router>>> entries = hash.entrySet();
        for (Map.Entry<String, List<Router>> v : entries) {
            if (menuIdSet.contains(v.getKey())) {
                var currentRouter = idHash.get(v.getKey());
                if (StringUtils.isNotEmpty(currentRouter.getPid())) {
                    List<Router> routers = hash.get(currentRouter.getPid());
                    routers.add(currentRouter);
                    hash.put(currentRouter.getPid(), routers);
                }
            }
        }

        List<Router> res = new ArrayList<>();
        for (Map.Entry<String, List<Router>> v : hash.entrySet()) {
            if (menuIdSet.contains(v.getKey()) &&
                    StringUtils.isEmpty(Objects.requireNonNull(idHash.get(v.getKey())).getPid())) {
                res.add(idHash.get(v.getKey()));
            }
        }

        res.forEach(this::sortRouter);

        return res;
    }

    @Override
    public Menu insertMenu(@NonNull MenuVO menuVO) {
        var menu = new Menu();
        BeanUtils.copyProperties(menuVO, menu);

        baseMapper.insert(menu);

        return menu;
    }

    @Override
    public Menu updateMenu(@NonNull MenuVO menuVO) {
        var menu = new Menu();
        BeanUtils.copyProperties(menuVO, menu);

        baseMapper.updateById(menu);

        return menu;
    }

    @Override
    public Menu getMenuById(@NonNull String menuId) {
        Assert.hasText(menuId, "Menu Id must not be empty!");

        var menu = baseMapper.selectById(menuId);
        if (null == menu) {
            throw new EntityNotFoundException("Menu Id：" + menuId + "， 此菜单不存在。");
        }

        return menu;
    }

    @Override
    public boolean deleteById(@NonNull String menuId) {
        if (StringUtils.isNotEmpty(menuId)) {
            LambdaQueryWrapper<Menu> queryWrapper = Wrappers.lambdaQuery();
            queryWrapper.eq(Menu::getId, menuId).or().eq(Menu::getPid, menuId);

            List<Menu> menus = baseMapper.selectList(queryWrapper);

            if (!CollectionUtils.isEmpty(menus)) {
                menus.forEach(v -> {
                    // 删除菜单
                    baseMapper.deleteById(v.getId());
                    // 删除角色菜单关联
                    roleMenuService.deleteRoleMenuByMenuId(v.getId());
                    deleteById(v.getId());
                });
            }
        }
        return true;
    }

    @Override
    public boolean changeMenuById(@NonNull String menuId) {
        if (StringUtils.isNotEmpty(menuId)) {
            Menu menu = baseMapper.selectById(menuId);
            if (MenuStatus.NORMAL.getValue().equals(menu.getStatus())) {
                menu.setStatus(MenuStatus.FORBIDDEN.getValue());
            } else {
                menu.setStatus(MenuStatus.NORMAL.getValue());
            }
            baseMapper.updateById(menu);
        }

        return false;
    }

    @Override
    public List<MenuDTO> searchMenus(@NonNull SearchMenuVO searchMenuVO) {
        Assert.notNull(searchMenuVO, "SearchMenuVO must not be null!");

        LambdaQueryWrapper<Menu> queryWrapper = Wrappers.lambdaQuery();
        if (StringUtils.isNotEmpty(searchMenuVO.getName())) {
            queryWrapper.like(Menu::getName, searchMenuVO.getName());
        }

        if (null != searchMenuVO.getStatus()) {
            queryWrapper.eq(Menu::getStatus, searchMenuVO.getStatus());
        }

        if (null != searchMenuVO.getType()) {
            queryWrapper.eq(Menu::getType, searchMenuVO.getType());
        }

        if (null != searchMenuVO.getStartTime() && null != searchMenuVO.getEndTime()) {
            queryWrapper.between(Menu::getGmtCreate, searchMenuVO.getStartTime(), searchMenuVO.getEndTime());
        }

        return generateTreeMenus(queryWrapper);
    }

    @Override
    public MenuDTO convertToMenuDTO(@NonNull Menu menu) {
        MenuDTO menuDTO = new MenuDTO();
        BeanUtils.copyProperties(menu, menuDTO);
        return menuDTO;
    }

    @Override
    public List<MenuDTO> convertToMenuDTO(List<Menu> menus) {
        return menus.stream()
                .map(this::convertToMenuDTO)
                .collect(Collectors.toList());
    }

    @Override
    public MenuDetailDTO convertToMenuDetailDTO(@NonNull MenuDTO menuDTO) {
        MenuDetailDTO menuDetailDTO = new MenuDetailDTO();
        BeanUtils.copyProperties(menuDTO, menuDetailDTO);
        return menuDetailDTO;
    }

    @Override
    public List<MenuDetailDTO> convertToMenuDetailDTO(List<MenuDTO> menus) {
        return menus.stream()
                .map(this::convertToMenuDetailDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Router convertToRouter(Menu menu) {
        Router router = new Router();
        BeanUtils.copyProperties(menu, router);

        return router;
    }

    @Override
    public List<Router> convertToRouter(List<Menu> menus) {
        return menus.stream()
                .map(this::convertToRouter)
                .collect(Collectors.toList());
    }

    private List<MenuDTO> generateTreeMenus(LambdaQueryWrapper<Menu> queryWrapper) {
        queryWrapper.orderByAsc(Menu::getSort);
        List<MenuDTO> menus = convertToMenuDTO(baseMapper.selectList(queryWrapper));

        Map<String, List<MenuDTO>> hash = new HashMap<>(menus.size());
        for (MenuDTO menuDTO : menus) {
            menuDTO.setChildren(new ArrayList<>());
            hash.put(menuDTO.getId(), menuDTO.getChildren());
        }

        menus.forEach(v -> {
            if (StringUtils.isNotEmpty(v.getPid())) {
                List<MenuDTO> list = hash.get(v.getPid());
                if (null != list) {
                    list.add(v);
                    hash.put(v.getPid(), list);
                }
            }
        });

        List<MenuDTO> res = new ArrayList<>();
        menus.forEach(v -> {
            if (StringUtils.isEmpty(v.getPid())) {
                res.add(v);
            }
        });

        res.forEach(this::sortMenu);
        return res;
    }

    private void sortMenu(MenuDTO menuDTO) {
        menuDTO.getChildren().sort(Comparator.comparing(MenuDTO::getSort));
        menuDTO.getChildren().forEach(this::sortMenu);
    }

    private void sortRouter(Router router) {
        router.getChildren().sort(Comparator.comparing(Router::getSort));
        router.getChildren().forEach(this::sortRouter);
    }
}
