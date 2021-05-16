package cn.lwjppz.mindstorm.permission.service.impl;

import cn.lwjppz.mindstorm.common.core.enums.ResultStatus;
import cn.lwjppz.mindstorm.common.core.enums.UserStatus;
import cn.lwjppz.mindstorm.common.core.enums.UserType;
import cn.lwjppz.mindstorm.common.core.exception.AlreadyExistsException;
import cn.lwjppz.mindstorm.common.core.exception.EntityNotFoundException;
import cn.lwjppz.mindstorm.permission.mapper.UserMapper;
import cn.lwjppz.mindstorm.permission.model.dto.LoginUserDTO;
import cn.lwjppz.mindstorm.permission.model.entity.User;
import cn.lwjppz.mindstorm.permission.model.vo.UserVO;
import cn.lwjppz.mindstorm.permission.service.PermissionService;
import cn.lwjppz.mindstorm.permission.service.RoleService;
import cn.lwjppz.mindstorm.permission.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author lwj
 * @since 2021-05-09
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionService permissionService;

    @Override
    public User insertStudent(@NonNull UserVO userVO) {
        return insertUser(userVO, UserType.STUDENT);
    }

    @Override
    public User insertTeacher(@NonNull UserVO userVO) {
        return insertUser(userVO, UserType.TEACHER);
    }

    @Override
    public User insertAdmin(@NonNull UserVO userVO) {
        return insertUser(userVO, UserType.ADMIN);
    }

    @Override
    public User updateUser(@NonNull UserVO userVO) {
        Assert.notNull(userVO, "用户信息不能为空!");

        User user = User.builder().build();
        BeanUtils.copyProperties(userVO, user);
        setPassword(user, user.getPassword());

        baseMapper.updateById(user);

        return user;
    }

    @Override
    public void deleteUser(@NonNull String userId) {
        Assert.hasText(userId, "UserId must not be empty!");

        // 删除用户
        if (StringUtils.hasText(userId) && !StringUtils.isEmpty(userId)) {
            User user = baseMapper.selectById(userId);
            if (null == user) {
                throw new EntityNotFoundException(ResultStatus.NOT_FOUND);
            }
            baseMapper.deleteById(userId);
        }
    }

    public User insertUser(@NonNull UserVO userVO, UserType userType) {
        Assert.notNull(userVO, "The user info must not be null!");

        User user = User.builder().build();
        BeanUtils.copyProperties(userVO, user);

        // 设置用户类型
        user.setUserType(userType.getValue());
        // 设置密码
        setPassword(user, user.getPassword());

        user.setStatus(UserStatus.NORMAL.getValue());

        // 查询数据库中是否已经存在该用户
        LambdaQueryWrapper<User> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(User::getUsername, user.getUsername());
        if (baseMapper.selectCount(queryWrapper) > 0) {
            throw new AlreadyExistsException(ResultStatus.USER_EXIT_ERROR);
        }

        baseMapper.insert(user);

        return user;
    }

    @Override
    public void setPassword(@NonNull User user, String plainPassword) {
        Assert.notNull(user, "User must not be null!");
        Assert.hasText(plainPassword, "Plain password must not be blank.");

        user.setPassword(BCrypt.hashpw(plainPassword, BCrypt.gensalt()));
    }

    @Override
    public LoginUserDTO selectUserByUserName(@NonNull String username) {
        Assert.hasText(username, "Username must not be empty!");

        LambdaQueryWrapper<User> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(User::getUsername, username);

        User user = baseMapper.selectOne(queryWrapper);

        LoginUserDTO loginUserDTO = new LoginUserDTO();
        BeanUtils.copyProperties(user, loginUserDTO);

        Set<String> permissions = new HashSet<>();
        Set<String> roles = new HashSet<>();
        roles.add("admin");
        loginUserDTO.setPermissions(permissions);
        loginUserDTO.setRoles(roles);

        return loginUserDTO;
    }

}
