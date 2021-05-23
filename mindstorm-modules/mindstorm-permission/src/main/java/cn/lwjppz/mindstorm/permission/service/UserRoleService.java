package cn.lwjppz.mindstorm.permission.service;

import cn.lwjppz.mindstorm.permission.model.dto.userRole.UserRoleDTO;
import cn.lwjppz.mindstorm.permission.model.entity.UserRole;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.lang.NonNull;

import java.util.List;

/**
 * <p>
 * 用户角色表 服务类
 * </p>
 *
 * @author lwj
 * @since 2021-05-09
 */
public interface UserRoleService extends IService<UserRole> {

    /**
     * 根据用户 Id 获取角色信息该用户所拥有的所有角色
     *
     * @param userId 用户 Id
     * @return 用户角色信息
     */
    UserRoleDTO getRoleByUserId(@NonNull String userId);

    /**
     * 为用户分配角色
     *
     * @param userId 用户Id
     * @param roles  角色
     * @return 是否分配成功
     */
    boolean insertUserRole(String userId, List<String> roles);

    /**
     * 根据用户 Id 删除相关联的菜单角色
     *
     * @param userId 用户Id
     * @return 是否删除成功
     */
    boolean deleteUserRole(String userId);

    /**
     * 根据用户Id获取该用户所有有的所有角色的Id
     *
     * @param userId 用户Id
     * @return 角色Id集合
     */
    List<String> getRoleIdsByUserId(String userId);

}
