package cn.lwjppz.mindstorm.security.service.impl;

import cn.lwjppz.mindstorm.security.entity.Permission;
import cn.lwjppz.mindstorm.security.mapper.PermissionMapper;
import cn.lwjppz.mindstorm.security.service.PermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 权限 服务实现类
 * </p>
 *
 * @author lwj
 * @since 2021-05-09
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {

}
