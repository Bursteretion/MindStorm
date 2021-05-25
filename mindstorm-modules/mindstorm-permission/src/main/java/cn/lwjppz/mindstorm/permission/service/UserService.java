package cn.lwjppz.mindstorm.permission.service;

import cn.lwjppz.mindstorm.api.permission.model.Loginuser;
import cn.lwjppz.mindstorm.common.core.enums.UserStatus;
import cn.lwjppz.mindstorm.common.core.enums.UserType;
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
     * 通过用户类型获取用户
     *
     * @param userType 用户类型
     * @return 用户集合
     */
    List<User> getUsersByType(@NonNull UserType userType);

    /**
     * 通过用户类型分页查询用户信息
     *
     * @param pageIndex 某一页
     * @param pageSize  每页条数
     * @param userType  用户类型
     * @return 分页数据
     */
    IPage<UserDTO> pageByUsersByType(int pageIndex, int pageSize, UserType userType);

    /**
     * 多条件查询用户信息
     *
     * @param pageIndex    第几页
     * @param pageSize     每页条数
     * @param searchUserVO 搜索条件
     * @return 分页数据
     */
    IPage<UserDTO> pageBySearchUser(int pageIndex, int pageSize, SearchUserVO searchUserVO);

    /**
     * 新增一个学生
     *
     * @param userVO 学生信息
     * @return 学生信息
     */
    User insertStudent(@NonNull UserVO userVO);

    /**
     * 新增一个教师
     *
     * @param userVO 教师信息
     * @return 教师信息
     */
    User insertTeacher(@NonNull UserVO userVO);

    /**
     * 新增一个管理员
     *
     * @param userVO 管理员信息
     * @return User 管理员信息
     */
    User insertAdmin(@NonNull UserVO userVO);

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
    Loginuser selectUserByUserName(@NonNull String username);

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

}
