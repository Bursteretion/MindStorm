package cn.lwjppz.mindstorm.permission.service;

import cn.lwjppz.mindstorm.permission.model.dto.roleMenu.RoleMenuDTO;
import cn.lwjppz.mindstorm.permission.model.entity.RoleMenu;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.lang.NonNull;

import java.util.List;

/**
 * <p>
 * 角色权限表 服务类
 * </p>
 *
 * @author lwj
 * @since 2021-05-09
 */
public interface RoleMenuService extends IService<RoleMenu> {

    /**
     * 根据角色 Id 获取该角色所有菜单
     *
     * @param roleId 角色Id
     * @return 角色菜单信息
     */
    RoleMenuDTO getMenusByRoleId(@NonNull String roleId);

    /**
     * 为角色分配菜单
     *
     * @param roleId 角色 Id
     * @param menus  菜单
     * @return 是否分配成功
     */
    boolean insertRoleMenu(String roleId, List<String> menus);

    /**
     * 根据角色 Id 删除相关联的菜单
     *
     * @param roleId 角色Id
     * @return 是否删除
     */
    boolean deleteRoleMenu(@NonNull String roleId);

    /**
     * 根据菜单 Id 删除相关联的信息
     *
     * @param menuId 菜单Id
     * @return 是否删除成功
     */
    boolean deleteRoleMenuByMenuId(String menuId);

    /**
     * 根据角色Id获取该角色所拥有的所有菜单Id
     *
     * @param roleId 角色Id
     * @return 菜单Id集合
     */
    List<String> getMenuIdsByRoleId(String roleId);

}
