package cn.lwjppz.mindstorm.permission.service;

import cn.lwjppz.mindstorm.api.permission.model.LoginUser;
import cn.lwjppz.mindstorm.api.permission.model.UserTo;
import cn.lwjppz.mindstorm.common.core.enums.status.UserStatus;
import cn.lwjppz.mindstorm.permission.model.dto.user.SimpleUserSelectDTO;
import cn.lwjppz.mindstorm.permission.model.dto.user.UserDTO;
import cn.lwjppz.mindstorm.permission.model.dto.user.UserDetailDTO;
import cn.lwjppz.mindstorm.permission.model.entity.User;
import cn.lwjppz.mindstorm.permission.model.vo.user.SearchUserVO;
import cn.lwjppz.mindstorm.permission.model.vo.user.UserVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.lang.NonNull;

import java.util.List;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author lwj
 * @since 2021-05-09
 */
public interface UserService extends IService<User> {

    /**
     * 分页查询用户信息
     *
     * @param pageIndex 某一页
     * @param pageSize  每页条数
     * @return 分页数据
     */
    IPage<UserDTO> pageByUsers(int pageIndex, int pageSize);

    /**
     * 多条件查询用户信息
     *
     * @param searchUserVO 搜索条件
     * @return 分页数据
     */
    IPage<UserDTO> pageBySearchUser(SearchUserVO searchUserVO);

    /**
     * 新增一个用户
     *
     * @param userVO 用户信息
     * @return User 用户信息
     */
    User insertUser(@NonNull UserVO userVO);

    /**
     * 修改用户信息
     *
     * @param userVO 用户信息
     * @return 用户信息
     */
    User updateUser(@NonNull UserVO userVO);

    /**
     * 删除用户
     *
     * @param userId 用户Id
     * @return 是否删除成功
     */
    boolean deleteUser(@NonNull String userId);

    /**
     * 更改某个用户状态
     *
     * @param userId 用户Id
     * @param status 用户状态
     * @return 是否更改成功
     */
    boolean changeUserStatus(@NonNull String userId, UserStatus status);

    /**
     * 加密用户密码
     *
     * @param user          用户
     * @param plainPassword 未加密密码
     */
    void setPassword(@NonNull User user, String plainPassword);

    /**
     * 通过用户名获取用户信息
     *
     * @param username 用户名
     * @return 用户信息
     */
    LoginUser selectUserByUserName(@NonNull String username);

    /**
     * 通过用户Id获取用户信息
     *
     * @param userId 用户Id
     * @return 用户信息
     */
    User selectUserByUserId(@NonNull String userId);

    /**
     * 将 User 对象转化为 UserDTO 对象
     *
     * @param user User 对象
     * @return UserDTO 对象
     */
    UserDTO convertToUserDTO(@NonNull User user);

    /**
     * 将 User 集合转化为 UserDTO 集合
     *
     * @param users User 集合
     * @return UserDTO 集合
     */
    List<UserDTO> convertToUserDTO(@NonNull List<User> users);

    /**
     * 将 User 对象转化为 UserDetailDTO 对象
     *
     * @param user User 对象
     * @return UserDetailDTO 对象
     */
    UserDetailDTO convertToUserDetailDTO(@NonNull User user);

    /**
     * 将 User 集合转化为 UserDetailDTO 集合
     *
     * @param users User 集合
     * @return UserDetailDTO 集合
     */
    List<UserDetailDTO> convertToUserDetailDTO(List<User> users);

    /**
     * 将 User 对象转化为 UserTo 对象
     *
     * @param user User 对象
     * @return UserTo 对象
     */
    UserTo convertToUserTo(User user);

    /**
     * 将 User 集合转化为 SimpleUserSelectDTO 集合
     *
     * @param users User 集合
     * @return SimpleUserSelectDTO 集合
     */
    List<SimpleUserSelectDTO> convertToUserSelectDTO(List<User> users);
}
