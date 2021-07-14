package cn.lwjppz.mindstorm.api.education.factory;

import cn.lwjppz.mindstorm.api.education.feign.RemoteEducationFeignService;
import cn.lwjppz.mindstorm.common.core.support.CommonResult;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 教学服务降级处理
 * </p>
 *
 * @author : lwj
 * @since : 2021-07-13
 */
@Component
public class RemoteEducationFallbackFactory implements FallbackFactory<RemoteEducationFeignService> {

    private static final Logger logger = LoggerFactory.getLogger(RemoteEducationFallbackFactory.class);

    @Override
    public RemoteEducationFeignService create(Throwable throwable) {
        logger.error("教学服务调用失败:{}", throwable.getMessage());
        return new RemoteEducationFeignService() {
            @Override
            public CommonResult getAcademyInfo(String academyId) {
                return null;
            }

            @Override
            public CommonResult getProfessionInfo(String professionId) {
                return null;
            }
        };
    }
}
