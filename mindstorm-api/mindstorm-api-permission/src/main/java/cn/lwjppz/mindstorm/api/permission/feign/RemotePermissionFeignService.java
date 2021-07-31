package cn.lwjppz.mindstorm.api.permission.feign;

import cn.lwjppz.mindstorm.api.permission.factory.RemotePermissionFallbackFactory;
import cn.lwjppz.mindstorm.common.core.support.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * <p>
 * 权限服务
 * </p>
 *
 * @author : lwj
 * @since : 2021-05-15
 */
@FeignClient(value = "mindstorm-permission", fallbackFactory = RemotePermissionFallbackFactory.class)
public interface RemotePermissionFeignService {

    /**
     * 通过用户名查询用户信息
     *
     * @param username 用户名
     * @return 结果
     */
    @GetMapping("permission/user/info/{username}")
    CommonResult selectUserByUsername(@PathVariable("username") String username);

    /**
     * 通过用户Id查询用户信息
     *
     * @param userId 用户Id
     * @return 结果
     */
    @GetMapping("/permission/user/info/remote/{userId}")
    CommonResult remoteUserInfoById(@PathVariable("userId") String userId);
}
