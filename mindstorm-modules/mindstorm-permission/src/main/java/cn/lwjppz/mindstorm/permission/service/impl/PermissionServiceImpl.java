package cn.lwjppz.mindstorm.permission.service.impl;

import cn.lwjppz.mindstorm.common.core.enums.PermissionStatus;
import cn.lwjppz.mindstorm.permission.mapper.PermissionMapper;
import cn.lwjppz.mindstorm.permission.model.dto.PermissionDTO;
import cn.lwjppz.mindstorm.permission.model.dto.PermissionDetailDTO;
import cn.lwjppz.mindstorm.permission.model.entity.Permission;
import cn.lwjppz.mindstorm.permission.model.vo.PermissionVO;
import cn.lwjppz.mindstorm.permission.service.PermissionService;
import cn.lwjppz.mindstorm.permission.service.RolePermissionService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 权限 服务实现类
 * </p>
 *
 * @author lwj
 * @since 2021-05-09
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {

    @Autowired
    private RolePermissionService rolePermissionService;

    @Override
    public List<PermissionDTO> getPermissions() {
        // 构造查询条件
        LambdaQueryWrapper<Permission> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(Permission::getStatus, PermissionStatus.NORMAL);

        List<Permission> permissions = baseMapper.selectList(queryWrapper);

        return convertToPermissionDTO(permissions);
    }

    @Override
    public PermissionDTO insertPermission(@NonNull PermissionVO permissionVO) {
        Assert.notNull(permissionVO, "PermissionVO must not be null!");

        Permission permission = convertToPermission(permissionVO);
        // 设置启用状态
        permission.setStatus(PermissionStatus.NORMAL.getValue());

        baseMapper.insert(permission);

        return convertToPermissionDTO(permission);
    }

    @Override
    public PermissionDetailDTO getPermissionInfo(@NonNull String permissionId) {
        Assert.hasText(permissionId, "Permission id must not be empty!");

        Permission permission = null;
        if (StringUtils.hasText(permissionId) && !StringUtils.isEmpty(permissionId)) {
            permission = baseMapper.selectById(permissionId);
        }

        assert permission != null;

        return convertToPermissionDetailDTO(permission);
    }

    @Override
    public PermissionDTO updatePermission(@NonNull PermissionVO permissionVO) {
        return null;
    }

    @Override
    public boolean deletePermission(@NonNull String permissionId) {
        Assert.hasText(permissionId, "PermissionId must not be empty!");

        if (StringUtils.hasText(permissionId) && !StringUtils.isEmpty(permissionId)) {
            baseMapper.deleteById(permissionId);
        }

        // TODO: 删除 Role 和 Permission 的关联
        return true;
    }

    @Override
    public boolean deleteBatchPermission(List<String> permissionIds) {
        permissionIds.forEach(this::deletePermission);
        return true;
    }

    @Override
    public boolean forbiddenPermission(@NonNull String permissionId) {
        Assert.hasText(permissionId, "PermissionId must not be empty!");

        // 查出该权限菜单（按钮）
        Permission permission = baseMapper.selectById(permissionId);
        // 设置禁用状态
        permission.setStatus(PermissionStatus.FORBIDDEN.getValue());

        baseMapper.updateById(permission);

        return true;
    }

    @Override
    public PermissionDTO convertToPermissionDTO(@NonNull Permission permission) {
        PermissionDTO permissionDTO = new PermissionDTO();
        BeanUtils.copyProperties(permission, permissionDTO);
        return permissionDTO;
    }

    @Override
    public List<PermissionDTO> convertToPermissionDTO(List<Permission> permissions) {
        return permissions.stream()
                .map(this::convertToPermissionDTO)
                .collect(Collectors.toList());
    }

    @Override
    public PermissionDetailDTO convertToPermissionDetailDTO(@NonNull Permission permission) {
        PermissionDetailDTO permissionDetailDTO = new PermissionDetailDTO();
        BeanUtils.copyProperties(permission, permissionDetailDTO);
        return permissionDetailDTO;
    }

    @Override
    public List<PermissionDetailDTO> convertToPermissionDetailDTO(List<Permission> permissions) {
        return permissions.stream()
                .map(this::convertToPermissionDetailDTO)
                .collect(Collectors.toList());
    }

    @Override
    public PermissionVO convertToPermissionVO(@NonNull Permission permission) {
        PermissionVO permissionVO = new PermissionVO();
        BeanUtils.copyProperties(permission, permissionVO);
        return permissionVO;
    }

    @Override
    public List<PermissionVO> convertToPermissionVO(List<Permission> permissions) {
        return permissions.stream()
                .map(this::convertToPermissionVO)
                .collect(Collectors.toList());
    }

    @Override
    public Permission convertToPermission(@NonNull PermissionVO permissionVO) {
        Permission permission = new Permission();
        BeanUtils.copyProperties(permissionVO, permission);
        return permission;
    }
}
