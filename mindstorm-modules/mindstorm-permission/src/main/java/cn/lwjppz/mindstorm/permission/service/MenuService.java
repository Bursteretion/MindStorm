package cn.lwjppz.mindstorm.permission.service;

import cn.lwjppz.mindstorm.permission.model.dto.menu.MenuDTO;
import cn.lwjppz.mindstorm.permission.model.dto.menu.MenuDetailDTO;
import cn.lwjppz.mindstorm.permission.model.dto.menu.Router;
import cn.lwjppz.mindstorm.permission.model.entity.Menu;
import cn.lwjppz.mindstorm.permission.model.vo.menu.MenuVO;
import cn.lwjppz.mindstorm.permission.model.vo.menu.SearchMenuVO;
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
     * 根据角色Id生成可访问的路由表
     *
     * @param roleIds 角色Id集合
     * @return 路由表
     */
    List<Router> getRouters(List<String> roleIds);

    /**
     * 新增菜单
     *
     * @param menuVO 菜单信息
     * @return 菜单信息
     */
    Menu insertMenu(@NonNull MenuVO menuVO);

    /**
     * 更新菜单信息
     *
     * @param menuVO 菜单信息
     * @return 菜单信息
     */
    Menu updateMenu(@NonNull MenuVO menuVO);

    /**
     * 获取菜单信息
     *
     * @param menuId 菜单 Id
     * @return 菜单信息
     */
    Menu getMenuById(@NonNull String menuId);

    /**
     * 删除菜单
     *
     * @param menuId 菜单Id
     * @return 是否删除成功
     */
    boolean deleteById(@NonNull String menuId);

    /**
     * 多条件查询菜单
     *
     * @param searchMenuVO 查询菜单信息
     * @return 菜单集合
     */
    List<MenuDTO> searchMenus(@NonNull SearchMenuVO searchMenuVO);

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

    /**
     * 将 Menu 对象转化为 Router 对象
     *
     * @param menu Menu 对象
     * @return Router 对象
     */
    Router convertToRouter(Menu menu);

    /**
     * 将 Menu 集合转化为 Router 集合
     *
     * @param menus Menu 集合
     * @return Router 集合
     */
    List<Router> convertToRouter(List<Menu> menus);
}
