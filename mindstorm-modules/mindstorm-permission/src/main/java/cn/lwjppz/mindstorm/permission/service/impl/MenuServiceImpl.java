package cn.lwjppz.mindstorm.permission.service.impl;

import cn.lwjppz.mindstorm.common.core.enums.MenuType;
import cn.lwjppz.mindstorm.permission.mapper.MenuMapper;
import cn.lwjppz.mindstorm.permission.model.dto.menu.FatherTreeMenu;
import cn.lwjppz.mindstorm.permission.model.dto.menu.MenuDTO;
import cn.lwjppz.mindstorm.permission.model.dto.menu.MenuDetailDTO;
import cn.lwjppz.mindstorm.permission.model.entity.Menu;
import cn.lwjppz.mindstorm.permission.model.vo.menu.MenuVO;
import cn.lwjppz.mindstorm.permission.service.MenuService;
import cn.lwjppz.mindstorm.permission.service.RoleMenuService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
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
        LambdaQueryWrapper<Menu> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.orderByAsc(Menu::getSort);

        return generateTreeMenus(queryWrapper);
    }

    @Override
    public List<MenuDTO> getMenus(@NonNull MenuType menuType) {
        LambdaQueryWrapper<Menu> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(Menu::getType, menuType.getValue());

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
        List<MenuDTO> menus = convertToMenuDTO(baseMapper.selectList(queryWrapper));

        Map<String, Set<MenuDTO>> hash = new HashMap<>(menus.size());
        for (MenuDTO menuDTO : menus) {
            menuDTO.setChildren(new HashSet<>());
            hash.put(menuDTO.getId(), menuDTO.getChildren());
        }

        menus.forEach(v -> {
            if (StringUtils.hasText(v.getPid())) {
                Set<MenuDTO> set = hash.get(v.getPid());
                set.add(v);
                hash.put(v.getPid(), set);
            }
        });

        List<MenuDTO> res = new ArrayList<>();
        menus.forEach(v -> {
            if (StringUtils.isEmpty(v.getPid())) {
                res.add(v);
            }
        });

        return res;
    }
}
