package cn.lwjppz.mindstorm.permission.service.impl;

import cn.lwjppz.mindstorm.api.permission.model.Loginuser;
import cn.lwjppz.mindstorm.common.core.enums.ResultStatus;
import cn.lwjppz.mindstorm.common.core.enums.UserStatus;
import cn.lwjppz.mindstorm.common.core.enums.UserType;
import cn.lwjppz.mindstorm.common.core.exception.AlreadyExistsException;
import cn.lwjppz.mindstorm.common.core.exception.EntityNotFoundException;
import cn.lwjppz.mindstorm.permission.mapper.UserMapper;
import cn.lwjppz.mindstorm.permission.model.dto.user.UserDTO;
import cn.lwjppz.mindstorm.permission.model.dto.user.UserDetailDTO;
import cn.lwjppz.mindstorm.permission.model.entity.User;
import cn.lwjppz.mindstorm.permission.model.vo.user.SearchUserVO;
import cn.lwjppz.mindstorm.permission.model.vo.user.UserVO;
import cn.lwjppz.mindstorm.permission.service.MenuService;
import cn.lwjppz.mindstorm.permission.service.RoleMenuService;
import cn.lwjppz.mindstorm.permission.service.UserRoleService;
import cn.lwjppz.mindstorm.permission.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.lang.NonNull;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    private final UserRoleService userRoleService;

    private final RoleMenuService roleMenuService;

    private final MenuService menuService;

    public UserServiceImpl(@Lazy UserRoleService userRoleService,
                           @Lazy RoleMenuService roleMenuService,
                           @Lazy MenuService menuService) {
        this.userRoleService = userRoleService;
        this.roleMenuService = roleMenuService;
        this.menuService = menuService;
    }

    @Override
    public List<User> getUsersByType(@NonNull UserType userType) {
        Assert.notNull(userType, "User Type must not be null!");

        // 构造查询条件
        LambdaQueryWrapper<User> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(User::getUserType, userType.getValue());

        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public IPage<UserDTO> pageByUsersByType(int pageIndex, int pageSize, UserType userType) {
        Assert.notNull(userType, "User Type must not be null!");
        IPage<User> iPage = new Page<>(pageIndex, pageSize);

        // 构造查询条件
        LambdaQueryWrapper<User> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(User::getUserType, userType.getValue());
        queryWrapper.orderByAsc(User::getGmtCreate);

        iPage = baseMapper.selectPage(iPage, queryWrapper);

        IPage<UserDTO> page = new Page<>();
        BeanUtils.copyProperties(iPage, page);

        page.setRecords(convertToUserDTO(iPage.getRecords()));

        return page;
    }

    @Override
    public IPage<UserDTO> pageBySearchUser(int pageIndex, int pageSize, SearchUserVO searchUserVO) {
        IPage<User> iPage = new Page<>(pageIndex, pageSize);

        LambdaQueryWrapper<User> queryWrapper = Wrappers.lambdaQuery();

        if (StringUtils.hasText(searchUserVO.getUsername())) {
            queryWrapper.like(User::getUsername, searchUserVO.getUsername());
        }

        if (StringUtils.hasText(searchUserVO.getRealName())) {
            queryWrapper.like(User::getRealName, searchUserVO.getRealName());
        }

        if (null != searchUserVO.getUserType()) {
            queryWrapper.eq(User::getUserType, searchUserVO.getUserType());
        }

        if (null != searchUserVO.getStatus()) {
            queryWrapper.eq(User::getStatus, searchUserVO.getStatus());
        }

        iPage = baseMapper.selectPage(iPage, queryWrapper);

        IPage<UserDTO> page = new Page<>();
        BeanUtils.copyProperties(iPage, page);

        page.setRecords(convertToUserDTO(iPage.getRecords()));
        return page;
    }

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

        User user = new User();
        BeanUtils.copyProperties(userVO, user);
        //  密码加密
        setPassword(user, user.getPassword());

        baseMapper.updateById(user);

        return user;
    }

    @Override
    public boolean deleteUser(@NonNull String userId) {
        Assert.hasText(userId, "User Id must not be empty!");

        // 删除用户
        if (StringUtils.hasText(userId) && !StringUtils.isEmpty(userId)) {
            User user = baseMapper.selectById(userId);
            if (null == user) {
                throw new EntityNotFoundException(ResultStatus.NOT_FOUND);
            }
            // 删除该用户
            baseMapper.deleteById(userId);
            // 删除用户与角色相关联信息
            userRoleService.deleteUserRole(userId);
        }

        return true;
    }

    @Override
    public boolean changeUserStatus(@NonNull String userId, UserStatus status) {
        Assert.hasText(userId, "UserId must not be empty!");

        LambdaQueryWrapper<User> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(User::getId, userId);

        User user = baseMapper.selectOne(queryWrapper);
        if (null == user) {
            throw new EntityNotFoundException("当前用户不存在！");
        }

        // 更新用户状态
        user.setStatus(status.getValue());
        baseMapper.updateById(user);

        return true;
    }

    public User insertUser(@NonNull UserVO userVO, UserType userType) {
        Assert.notNull(userVO, "The user info must not be null!");

        User user = new User();
        BeanUtils.copyProperties(userVO, user);

        // 设置用户类型
        user.setUserType(userType.getValue());
        // 设置密码
        setPassword(user, user.getPassword());
        // 设置状态
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
        Assert.notNull(user, "User must not be null");
        Assert.hasText(plainPassword, "Plain password must not be blank.");

        user.setPassword(BCrypt.hashpw(plainPassword, BCrypt.gensalt()));
    }

    @Override
    public Loginuser selectUserByUserName(@NonNull String username) {
        Assert.hasText(username, "Username must not be empty!");

        // 构造查询条件
        LambdaQueryWrapper<User> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(User::getUsername, username);

        User user = baseMapper.selectOne(queryWrapper);

        Loginuser loginUserDTO = new Loginuser();
        BeanUtils.copyProperties(user, loginUserDTO);

        // 查询该用户所拥有的角色
        List<String> roleIds = userRoleService.getRoleIdsByUserId(user.getId());
        Set<String> roles = new HashSet<>(roleIds);
        loginUserDTO.setRoles(roles);

        // 查询该用户所拥有的权限
        Set<String> menuIdSet = new HashSet<>();
        roles.forEach(v -> menuIdSet.addAll(roleMenuService.getMenuIdsByRoleId(v)));
        Set<String> permissions = new HashSet<>();
        menuIdSet.forEach(v -> permissions.add(menuService.getMenuById(v).getPermissionValue()));
        loginUserDTO.setPermissions(permissions);

        return loginUserDTO;
    }

    @Override
    public User selectUserByUserId(@NonNull String userId) {
        Assert.hasText(userId, "UserId must not be empty!");

        LambdaQueryWrapper<User> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(User::getId, userId);

        User user = baseMapper.selectOne(queryWrapper);
        if (null == user) {
            throw new EntityNotFoundException("未找到Id为" + userId + " 的用户！");
        }

        return user;
    }

    @Override
    public UserDTO convertToUserDTO(@NonNull User user) {
        Assert.notNull(user, "User must not be null!");

        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(user, userDTO);
        return userDTO;
    }

    @Override
    public List<UserDTO> convertToUserDTO(@NonNull List<User> users) {
        Assert.notNull(users, "User list must not be null!");

        return users.stream()
                .map(this::convertToUserDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UserDetailDTO convertToUserDetailDTO(@NonNull User user) {
        Assert.notNull(user, "User must not be null!");

        UserDetailDTO userDetailDTO = new UserDetailDTO();
        BeanUtils.copyProperties(user, userDetailDTO);

        return userDetailDTO;
    }

    @Override
    public List<UserDetailDTO> convertToUserDetailDTO(List<User> users) {
        return users.stream()
                .map(this::convertToUserDetailDTO)
                .collect(Collectors.toList());
    }

}
