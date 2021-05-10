package cn.lwjppz.mindstorm.security.service.impl;

import cn.lwjppz.mindstorm.security.model.entity.UserRole;
import cn.lwjppz.mindstorm.security.mapper.UserRoleMapper;
import cn.lwjppz.mindstorm.security.service.UserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
