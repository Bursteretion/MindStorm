package cn.lwjppz.mindstorm.api.permission.factory;

import cn.lwjppz.mindstorm.api.permission.feign.RemotePermissionFeignService;
import cn.lwjppz.mindstorm.common.core.support.CommonResult;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 权限服务降级处理
 * </p>
 *
 * @author : lwj
 * @since : 2021-05-15
 */
@Component
public class RemotePermissionFallbackFactory implements FallbackFactory<RemotePermissionFeignService> {

    private static final Logger logger = LoggerFactory.getLogger(RemotePermissionFallbackFactory.class);

    @Override
    public RemotePermissionFeignService create(Throwable throwable) {
        logger.error("权限服务调用失败:{}", throwable.getMessage());
        return new RemotePermissionFeignService() {
            @Override
            public CommonResult selectUserByUsername(String username) {
                return null;
            }

            @Override
            public CommonResult remoteUserInfoById(String userId) {
                return null;
            }
        };
    }
}
