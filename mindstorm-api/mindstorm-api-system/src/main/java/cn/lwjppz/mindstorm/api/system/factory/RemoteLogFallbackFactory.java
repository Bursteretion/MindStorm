package cn.lwjppz.mindstorm.api.system.factory;

import cn.lwjppz.mindstorm.api.system.feign.RemoteLogFeignService;
import cn.lwjppz.mindstorm.api.system.model.SysLog;
import cn.lwjppz.mindstorm.common.core.support.CommonResult;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 日志服务降级处理
 * </p>
 *
 * @author : lwj
 * @since : 2021-05-30
 */
@Component
public class RemoteLogFallbackFactory implements FallbackFactory<RemoteLogFeignService> {

    private static final Logger logger = LoggerFactory.getLogger(RemoteLogFallbackFactory.class);

    @Override
    public RemoteLogFeignService create(Throwable throwable) {
        logger.error("日志服务调用失败:{}", throwable.getMessage());
        return new RemoteLogFeignService() {
            @Override
            public CommonResult saveLog(SysLog sysLog) {
                return null;
            }

            @Override
            public CommonResult saveLoginVisit(String username, Integer status, String message) {
                return null;
            }
        };
    }
}
