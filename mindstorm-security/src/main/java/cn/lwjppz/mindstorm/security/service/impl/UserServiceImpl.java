package cn.lwjppz.mindstorm.security.service.impl;

import cn.lwjppz.mindstorm.security.entity.User;
import cn.lwjppz.mindstorm.security.mapper.UserMapper;
import cn.lwjppz.mindstorm.security.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
