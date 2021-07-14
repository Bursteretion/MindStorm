package cn.lwjppz.mindstorm.api.education.feign;

import cn.lwjppz.mindstorm.api.education.factory.RemoteEducationFallbackFactory;
import cn.lwjppz.mindstorm.common.core.support.CommonResult;
import org.springframework.boot.autoconfigure.mongo.ReactiveMongoClientFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

/**
 * <p>
 * 院系服务
 * </p>
 *
 * @author : lwj
 * @since : 2021-07-13
 */
@FeignClient(value = "mindstorm-education", fallbackFactory = RemoteEducationFallbackFactory.class)
public interface RemoteEducationFeignService {

    /**
     * 通过院系Id查询院系信息
     *
     * @param academyId 院系Id
     * @return 结果
     */
    @PutMapping("education/academy/info/remote/{academyId}")
    CommonResult getAcademyInfo(@PathVariable("academyId") String academyId);

    /**
     * 通过专业Id查询专业信息
     *
     * @param professionId 专业Id
     * @return 结果
     */
    @PutMapping("education/profession/info/remote/{professionId}")
    CommonResult getProfessionInfo(@PathVariable("professionId") String professionId);
}
