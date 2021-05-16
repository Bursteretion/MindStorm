package cn.lwjppz.mindstorm.auth.factory;

import cn.lwjppz.mindstorm.auth.feign.UserFeignService;
import cn.lwjppz.mindstorm.common.core.support.CommonResult;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 用户服务降级处理
 * </p>
 *
 * @author : lwj
 * @since : 2021-05-15
 */
@Component
public class UserFallbackFactory implements FallbackFactory<UserFeignService> {

    private static final Logger logger = LoggerFactory.getLogger(UserFallbackFactory.class);

    @Override
    public UserFeignService create(Throwable throwable) {
        logger.error("用户服务调用失败:{}", throwable.getMessage());
        return username -> CommonResult.error().message("获取用户失败:" + throwable.getMessage());
    }
}
