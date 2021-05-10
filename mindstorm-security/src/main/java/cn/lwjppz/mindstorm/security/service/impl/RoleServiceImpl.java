package cn.lwjppz.mindstorm.security.service.impl;

import cn.lwjppz.mindstorm.security.model.entity.Role;
import cn.lwjppz.mindstorm.security.mapper.RoleMapper;
import cn.lwjppz.mindstorm.security.service.RoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
