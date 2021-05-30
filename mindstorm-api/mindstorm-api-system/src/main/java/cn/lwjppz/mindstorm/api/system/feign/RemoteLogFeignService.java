package cn.lwjppz.mindstorm.api.system.feign;

import cn.lwjppz.mindstorm.api.system.factory.RemoteLogFallbackFactory;
import cn.lwjppz.mindstorm.common.core.support.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * <p>
 * 用户服务
 * </p>
 *
 * @author : lwj
 * @since : 2021-05-15
 */
@FeignClient(value = "mindstorm-system", fallbackFactory = RemoteLogFallbackFactory.class)
public interface RemoteLogFeignService {

    /**
     * 通过用户名查询用户信息
     *
     * @param username 用户名
     * @return 结果
     */
    @GetMapping("permission/user/info/{username}")
    CommonResult selectUserByUsername(@PathVariable("username") String username);
}
