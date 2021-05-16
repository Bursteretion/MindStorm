package cn.lwjppz.mindstorm.permission.service;

import cn.lwjppz.mindstorm.permission.model.dto.LoginUserDTO;
import cn.lwjppz.mindstorm.permission.model.entity.User;
import cn.lwjppz.mindstorm.permission.model.vo.user.UserVO;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.lang.NonNull;

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
     */
    void deleteUser(@NonNull String userId);

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
    LoginUserDTO selectUserByUserName(@NonNull String username);

}
