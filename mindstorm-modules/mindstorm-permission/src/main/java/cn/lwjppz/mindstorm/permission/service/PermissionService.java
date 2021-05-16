package cn.lwjppz.mindstorm.permission.service;

import cn.lwjppz.mindstorm.permission.model.dto.PermissionDTO;
import cn.lwjppz.mindstorm.permission.model.dto.PermissionDetailDTO;
import cn.lwjppz.mindstorm.permission.model.entity.Permission;
import cn.lwjppz.mindstorm.permission.model.vo.PermissionVO;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.lang.NonNull;

import java.util.List;

/**
 * <p>
 * 权限 服务类
 * </p>
 *
 * @author lwj
 * @since 2021-05-09
 */
public interface PermissionService extends IService<Permission> {

    /**
     * 获取所有权限菜单
     *
     * @return 权限菜单
     */
    List<PermissionDTO> getPermissions();

    /**
     * 添加权限菜单或按钮
     *
     * @param permissionVO 权限信息
     * @return 权限信息
     */
    PermissionDTO insertPermission(@NonNull PermissionVO permissionVO);

    /**
     * 获取权限菜单（按钮）信息
     *
     * @param permissionId 权限菜单（按钮）Id
     * @return 权限菜单（按钮）信息
     */
    PermissionDetailDTO getPermissionInfo(@NonNull String permissionId);

    /**
     * 更新某权限菜单（按钮）信息
     *
     * @param permissionVO 权限菜单（按钮）信息
     * @return 权限菜单（按钮）信息
     */
    PermissionDTO updatePermission(@NonNull PermissionVO permissionVO);

    /**
     * 删除某权限菜单（按钮）信息
     *
     * @param permissionId 权限菜单（按钮）Id
     * @return 是否删除成功
     */
    boolean deletePermission(@NonNull String permissionId);

    /**
     * 批量删除权限菜单（按钮）信息
     *
     * @param permissionIds id信息集合
     * @return 是否删除成功
     */
    boolean deleteBatchPermission(List<String> permissionIds);

    /**
     * 禁用某个权限菜单（按钮）信息
     *
     * @param permissionId 权限菜单（按钮）Id
     * @return 是否禁用成功
     */
    boolean forbiddenPermission(@NonNull String permissionId);

    /**
     * 将 Permission 转为 PermissionDTO
     *
     * @param permission Permission对象
     * @return PermissionDTO对象
     */
    PermissionDTO convertToPermissionDTO(@NonNull Permission permission);

    /**
     * 将 permission 集合转为 PermissionDetailDTO 集合
     *
     * @param permissions permission 集合
     * @return PermissionDTO 集合
     */
    List<PermissionDTO> convertToPermissionDTO(List<Permission> permissions);

    /**
     * 将 Permission 转为 PermissionDetailDTO
     *
     * @param permission Permission对象
     * @return PermissionDetailDTO对象
     */
    PermissionDetailDTO convertToPermissionDetailDTO(@NonNull Permission permission);

    /**
     * 将 permission 集合转为 PermissionDetailDTO 集合
     *
     * @param permissions permission 集合
     * @return PermissionDetailDTO 集合
     */
    List<PermissionDetailDTO> convertToPermissionDetailDTO(List<Permission> permissions);

    /**
     * 将 Permission 转为 PermissionVO
     *
     * @param permission Permission对象
     * @return PermissionVO对象
     */
    PermissionVO convertToPermissionVO(@NonNull Permission permission);

    /**
     * 将 permission 集合转为 PermissionVO 集合
     *
     * @param permissions permission 集合
     * @return PermissionVO 集合
     */
    List<PermissionVO> convertToPermissionVO(List<Permission> permissions);

    /**
     * 将 PermissionVO 转为 Permission
     *
     * @param permissionVO PermissionVO 对象
     * @return Permission对象
     */
    Permission convertToPermission(@NonNull PermissionVO permissionVO);
}
