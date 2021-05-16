package cn.lwjppz.mindstorm.permission.service;

import cn.lwjppz.mindstorm.permission.model.dto.role.RoleDTO;
import cn.lwjppz.mindstorm.permission.model.entity.Role;
import cn.lwjppz.mindstorm.permission.model.vo.role.RoleVO;
import cn.lwjppz.mindstorm.permission.model.vo.role.SearchRoleVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.lang.NonNull;

import java.util.List;

/**
 * <p>
 * 角色信息表 服务类
 * </p>
 *
 * @author lwj
 * @since 2021-05-09
 */
public interface RoleService extends IService<Role> {

    /**
     * 分页角色信息
     *
     * @param pageIndex 第几页
     * @param pageSize  每页条数
     * @return 角色信息
     */
    IPage<RoleDTO> pageBy(int pageIndex, int pageSize);

    /**
     * 获取所有角色
     *
     * @return 角色列表
     */
    List<Role> listRoles();

    /**
     * 列出所有未禁用的角色
     *
     * @return 未禁用的角色列表
     */
    List<Role> listUnDisableRoles();

    /**
     * 根据查询条件查询角色列表
     *
     * @param searchRoleVO 查询信息
     * @return 角色列表
     */
    IPage<RoleDTO> queryPageRole(@NonNull SearchRoleVO searchRoleVO);

    /**
     * 新增一名角色
     *
     * @param roleVO 角色信息
     * @return 角色信息
     */
    Role insertRole(@NonNull RoleVO roleVO);

    /**
     * 修改角色信息
     *
     * @param roleVO 角色信息
     * @return 角色信息
     */
    Role updateRole(@NonNull RoleVO roleVO);

    /**
     * 根据Id查询角色信息
     *
     * @param roleId 角色Id
     * @return 角色信息
     */
    Role selectRoleById(@NonNull String roleId);

    /**
     * 根据角色Id删除角色信息
     *
     * @param roleId 角色Id
     * @return 是否删除成功
     */
    boolean deleteRoleById(@NonNull String roleId);

    /**
     * 批量删除角色信息
     *
     * @param roleIds 角色Id集合
     * @return 是否删除成功
     */
    boolean deleteBatchRoles(List<String> roleIds);

    /**
     * 更改某角色状态
     *
     * @param roleId 角色Id
     * @param status 角色状态
     * @return 是否更改成功
     */
    boolean changeRoleStatus(@NonNull String roleId, Integer status);

    /**
     * 将 Role 转为 RoleDTO
     *
     * @param role Role对象
     * @return RoleDTO对象
     */
    RoleDTO convertToRoleDTO(Role role);

    /**
     * 将 Role集合 转为 RoleDTO集合
     *
     * @param roles Role对象集合
     * @return RoleDTO对象集合
     */
    List<RoleDTO> convertToRoleDTO(List<Role> roles);

}
