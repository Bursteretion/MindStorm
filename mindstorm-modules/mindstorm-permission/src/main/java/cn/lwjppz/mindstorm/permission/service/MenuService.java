package cn.lwjppz.mindstorm.permission.service;

import cn.lwjppz.mindstorm.common.core.enums.MenuType;
import cn.lwjppz.mindstorm.permission.model.dto.menu.FatherTreeMenu;
import cn.lwjppz.mindstorm.permission.model.dto.menu.MenuDTO;
import cn.lwjppz.mindstorm.permission.model.dto.menu.MenuDetailDTO;
import cn.lwjppz.mindstorm.permission.model.entity.Menu;
import cn.lwjppz.mindstorm.permission.model.vo.menu.MenuVO;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.lang.NonNull;

import java.util.List;


/**
 * <p>
 * 菜单 服务类
 * </p>
 *
 * @author lwj
 * @since 2021-05-09
 */
public interface MenuService extends IService<Menu> {

    /**
     * 获取所有菜单
     *
     * @return 菜单集合
     */
    List<MenuDTO> getMenus();

    /**
     * 根据菜单类型获取菜单
     *
     * @param types 菜单类型集合
     * @return 菜单集合
     */
    List<MenuDTO> getMenus(@NonNull List<Integer> types);

    /**
     * 新增菜单
     *
     * @param menuVO 菜单信息
     * @return 菜单信息
     */
    Menu insertMenu(@NonNull MenuVO menuVO);

    /**
     * 将 Menu 对象转化为 MenuDTO 对象
     *
     * @param menu Menu 对象
     * @return MenuDTO 对象
     */
    MenuDTO convertToMenuDTO(@NonNull Menu menu);

    /**
     * 将 Menu 集合 转为 MenuDTO 集合
     *
     * @param menus Menu 集合
     * @return MenuDTO 集合
     */
    List<MenuDTO> convertToMenuDTO(List<Menu> menus);

    /**
     * 将 MenuDTO 对象转为 MenuDetailDTO 对象
     *
     * @param menuDTO MenuDTO 对象
     * @return MenuDetailDTO 对象
     */
    MenuDetailDTO convertToMenuDetailDTO(@NonNull MenuDTO menuDTO);

    /**
     * 将 MenuDTO 集合转为 MenuDetailDTO 集合
     *
     * @param menus MenuDTO 集合
     * @return MenuDetailDTO 集合
     */
    List<MenuDetailDTO> convertToMenuDetailDTO(List<MenuDTO> menus);

}
