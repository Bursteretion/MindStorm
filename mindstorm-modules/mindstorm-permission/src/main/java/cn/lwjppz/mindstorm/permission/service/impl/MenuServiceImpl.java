package cn.lwjppz.mindstorm.permission.service.impl;

import cn.lwjppz.mindstorm.common.core.exception.EntityNotFoundException;
import cn.lwjppz.mindstorm.permission.mapper.MenuMapper;
import cn.lwjppz.mindstorm.permission.model.dto.menu.MenuDTO;
import cn.lwjppz.mindstorm.permission.model.dto.menu.MenuDetailDTO;
import cn.lwjppz.mindstorm.permission.model.entity.Menu;
import cn.lwjppz.mindstorm.permission.model.vo.menu.MenuVO;
import cn.lwjppz.mindstorm.permission.model.vo.menu.SearchMenuVO;
import cn.lwjppz.mindstorm.permission.service.MenuService;
import cn.lwjppz.mindstorm.permission.service.RoleMenuService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

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

    @Autowired
    private RoleMenuService roleMenuService;


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
    public Menu insertMenu(@NonNull MenuVO menuVO) {
        Menu menu = new Menu();
        BeanUtils.copyProperties(menuVO, menu);

        baseMapper.insert(menu);

        return menu;
    }

    @Override
    public Menu updateMenu(@NonNull MenuVO menuVO) {
        Menu menu = new Menu();
        BeanUtils.copyProperties(menuVO, menu);

        baseMapper.updateById(menu);

        return menu;
    }

    @Override
    public Menu getMenuById(@NonNull String menuId) {
        Assert.hasText(menuId, "Menu Id must not be empty!");

        Menu menu = baseMapper.selectById(menuId);
        if (null == menu) {
            throw new EntityNotFoundException("Menu Id：" + menuId + "， 此菜单不存在。");
        }

        return menu;
    }

    @Override
    public boolean deleteById(@NonNull String menuId) {
        Assert.hasText(menuId, "Menu Id must not be empty!");

        baseMapper.deleteById(menuId);
        return true;
    }

    @Override
    public List<MenuDTO> searchMenus(@NonNull SearchMenuVO searchMenuVO) {
        Assert.notNull(searchMenuVO, "SearchMenuVO must not be null!");

        LambdaQueryWrapper<Menu> queryWrapper = Wrappers.lambdaQuery();
        if (StringUtils.hasText(searchMenuVO.getName())) {
            queryWrapper.like(Menu::getName, searchMenuVO.getName());
        }

        if (null != searchMenuVO.getStatus()) {
            queryWrapper.eq(Menu::getStatus, searchMenuVO.getStatus());
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

    private List<MenuDTO> generateTreeMenus(LambdaQueryWrapper<Menu> queryWrapper) {
        queryWrapper.orderByAsc(Menu::getSort);
        List<MenuDTO> menus = convertToMenuDTO(baseMapper.selectList(queryWrapper));

        Map<String, List<MenuDTO>> hash = new HashMap<>(menus.size());
        for (MenuDTO menuDTO : menus) {
            menuDTO.setChildren(new ArrayList<>());
            hash.put(menuDTO.getId(), menuDTO.getChildren());
        }

        menus.forEach(v -> {
            if (StringUtils.hasText(v.getPid())) {
                List<MenuDTO> list = hash.get(v.getPid());
                list.add(v);
                hash.put(v.getPid(), list);
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
}