package cn.lwjppz.mindstorm.permission.service.impl;

import cn.lwjppz.mindstorm.common.core.enums.UserType;
import cn.lwjppz.mindstorm.common.core.support.ValueEnum;
import cn.lwjppz.mindstorm.common.core.utils.ServiceUtils;
import cn.lwjppz.mindstorm.permission.mapper.UserRoleMapper;
import cn.lwjppz.mindstorm.permission.model.dto.userRole.UserRoleDTO;
import cn.lwjppz.mindstorm.permission.model.entity.User;
import cn.lwjppz.mindstorm.permission.model.entity.UserRole;
import cn.lwjppz.mindstorm.permission.service.UserRoleService;
import cn.lwjppz.mindstorm.permission.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

/**
 * <p>
 * 用户角色表 服务实现类
 * </p>
 *
 * @author lwj
 * @since 2021-05-09
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

    @Autowired
    private UserService userService;

    @Override
    public UserRoleDTO getRoleByUserId(@NonNull String userId) {
        Assert.hasText(userId, "User Id must not be empty!");

        LambdaQueryWrapper<UserRole> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(UserRole::getUserId, userId);

        List<UserRole> userRoles = baseMapper.selectList(queryWrapper);

        // 提取角色Id
        List<String> roles = ServiceUtils.fetchProperty(userRoles, UserRole::getRoleId);

        UserRoleDTO userRoleDTO = new UserRoleDTO();
        User user = userService.selectUserByUserId(userId);
        // 转换用户状态
        String userType = ValueEnum.valueToEnum(UserType.class, user.getUserType()).getName();

        userRoleDTO.setUserId(userId);
        userRoleDTO.setUserType(userType);
        userRoleDTO.setUsername(user.getUsername());
        userRoleDTO.setRoles(roles);

        return userRoleDTO;
    }

    @Override
    public boolean insertUserRole(String userId, List<String> roles) {
        // 为用户分配角色之前先删除之前的用户角色关联信息
        deleteUserRole(userId);

        roles.forEach(v -> {
            UserRole userRole = new UserRole();
            userRole.setUserId(userId);
            userRole.setRoleId(v);
            baseMapper.insert(userRole);
        });

        return true;
    }

    @Override
    public boolean deleteUserRole(String userId) {
        LambdaQueryWrapper<UserRole> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(UserRole::getUserId, userId);

        baseMapper.delete(queryWrapper);

        return true;
    }

    @Override
    public List<String> getRoleIdsByUserId(String userId) {
        LambdaQueryWrapper<UserRole> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(UserRole::getUserId, userId);

        List<UserRole> roles = baseMapper.selectList(queryWrapper);

        return ServiceUtils.fetchProperty(roles, UserRole::getRoleId);
    }
}
