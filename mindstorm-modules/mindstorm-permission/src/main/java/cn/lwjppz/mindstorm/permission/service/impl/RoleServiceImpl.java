package cn.lwjppz.mindstorm.permission.service.impl;

import cn.lwjppz.mindstorm.common.core.enums.status.RoleStatus;
import cn.lwjppz.mindstorm.common.core.exception.EntityNotFoundException;
import cn.lwjppz.mindstorm.common.core.support.ValueEnum;
import cn.lwjppz.mindstorm.permission.mapper.RoleMapper;
import cn.lwjppz.mindstorm.permission.model.dto.role.RoleDTO;
import cn.lwjppz.mindstorm.permission.model.dto.role.SimpleRoleDTO;
import cn.lwjppz.mindstorm.permission.model.entity.Role;
import cn.lwjppz.mindstorm.permission.model.vo.role.RoleVO;
import cn.lwjppz.mindstorm.permission.model.vo.role.SearchRoleVO;
import cn.lwjppz.mindstorm.permission.service.RoleMenuService;
import cn.lwjppz.mindstorm.permission.service.RoleService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * <p>
 * 角色信息表 服务实现类
 * </p>
 *
 * @author lwj
 * @since 2021-05-09
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    private final RoleMenuService roleMenuService;

    public RoleServiceImpl(@Lazy RoleMenuService roleMenuService) {
        this.roleMenuService = roleMenuService;
    }

    @Override
    public IPage<RoleDTO> pageBy(int pageIndex, int pageSize) {
        LambdaQueryWrapper<Role> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.orderByAsc(Role::getSort);
        return getPageDTO(pageIndex, pageSize, queryWrapper);
    }

    @Override
    public List<Role> listRoles() {
        List<Role> roles = baseMapper.selectList(null);
        return Optional.of(roles).orElse(Collections.emptyList());
    }

    @Override
    public List<Role> listUnDisableRoles() {
        // 构造查询条件
        LambdaQueryWrapper<Role> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(Role::getStatus, RoleStatus.NORMAL.getValue());

        // 获取所有未禁用的角色
        List<Role> roles = baseMapper.selectList(queryWrapper);

        return Optional.of(roles).orElse(Collections.emptyList());
    }

    @Override
    public IPage<RoleDTO> queryPageRole(@NonNull SearchRoleVO searchRoleVO) {
        Assert.notNull(searchRoleVO, "SearchRoleVO must not be null!");

        // 构造搜索角色条件
        LambdaQueryWrapper<Role> queryWrapper = Wrappers.lambdaQuery();
        if (!StringUtils.isEmpty(searchRoleVO.getRoleName()) && StringUtils.hasText(searchRoleVO.getRoleName())) {
            queryWrapper.like(Role::getRoleName, searchRoleVO.getRoleName());
        }
        if (null != searchRoleVO.getStatus()) {
            queryWrapper.eq(Role::getStatus, searchRoleVO.getStatus());
        }
        if (null != searchRoleVO.getStartTime() && null != searchRoleVO.getEndTime()) {
            queryWrapper.between(Role::getGmtCreate, searchRoleVO.getStartTime(), searchRoleVO.getEndTime());
        }

        return getPageDTO(searchRoleVO.getPageIndex(), searchRoleVO.getPageSize(), queryWrapper);
    }

    @Override
    public Role insertRole(@NonNull RoleVO roleVO) {
        Assert.notNull(roleVO, "RoleVO must not be null!");

        Role role = new Role();
        BeanUtils.copyProperties(roleVO, role);

        if (null == roleVO.getStatus()) {
            role.setStatus(RoleStatus.NORMAL.getValue());
        }

        baseMapper.insert(role);

        return role;
    }

    @Override
    public Role updateRole(@NonNull RoleVO roleVO) {
        Assert.notNull(roleVO, "RoleVO must not be null!");

        Role role = baseMapper.selectById(roleVO.getId());
        if (null == role) {
            throw new EntityNotFoundException("当前角色不存在");
        }

        BeanUtils.copyProperties(roleVO, role);
        baseMapper.updateById(role);

        return role;
    }

    @Override
    public Role selectRoleById(@NonNull String roleId) {
        Role role = null;
        if (StringUtils.hasText(roleId) && !StringUtils.isEmpty(roleId)) {
            role = baseMapper.selectById(roleId);
            if (null == role) {
                throw new EntityNotFoundException("角色信息不存在");
            }
        }

        return role;
    }

    @Override
    public boolean deleteRoleById(@NonNull String roleId) {
        Assert.hasText(roleId, "Role Id must not be empty!");

        if (StringUtils.hasText(roleId) && !StringUtils.isEmpty(roleId)) {
            baseMapper.deleteById(roleId);
            // 删除相关联的菜单
            return roleMenuService.deleteRoleMenu(roleId);
        }

        return true;
    }

    @Override
    public boolean deleteBatchRoles(List<String> roleIds) {
        roleIds.forEach(this::deleteRoleById);
        return true;
    }

    @Override
    public boolean changeRoleStatus(@NonNull String roleId, Integer status) {
        Assert.hasText(roleId, "Role Id must not be empty!");

        if (StringUtils.hasText(roleId) && !StringUtils.isEmpty(roleId)) {
            Role role = baseMapper.selectById(roleId);
            // 转化角色状态
            role.setStatus(ValueEnum.valueToEnum(RoleStatus.class, status).getValue());
            baseMapper.updateById(role);
        }
        return true;
    }

    @Override
    public RoleDTO convertToRoleDTO(Role role) {
        RoleDTO roleDTO = new RoleDTO();
        BeanUtils.copyProperties(role, roleDTO);
        return roleDTO;
    }

    @Override
    public List<RoleDTO> convertToRoleDTO(List<Role> roles) {
        return roles.stream()
                .map(this::convertToRoleDTO)
                .collect(Collectors.toList());
    }

    @Override
    public SimpleRoleDTO convertToSimpleRoleDTO(Role role) {
        SimpleRoleDTO simpleRoleDTO = new SimpleRoleDTO();
        simpleRoleDTO.setValue(role.getId());
        simpleRoleDTO.setLabel(role.getRoleName());

        return simpleRoleDTO;
    }

    @Override
    public List<SimpleRoleDTO> convertToSimpleRoleDTO(List<Role> roles) {
        return roles.stream()
                .map(this::convertToSimpleRoleDTO)
                .collect(Collectors.toList());
    }

    private IPage<RoleDTO> getPageDTO(int pageIndex, int pageSize, LambdaQueryWrapper<Role> queryWrapper) {
        Page<Role> page = new Page<>(pageIndex, pageSize);
        page = baseMapper.selectPage(page, queryWrapper);
        IPage<RoleDTO> pageDTO = new Page<>();
        BeanUtils.copyProperties(page, pageDTO);
        pageDTO.setRecords(this.convertToRoleDTO(page.getRecords()));
        return pageDTO;
    }
}
