package cn.lwjppz.mindstorm.api.system.feign;

import cn.lwjppz.mindstorm.api.system.factory.RemoteLogFallbackFactory;
import cn.lwjppz.mindstorm.api.system.model.SysLog;
import cn.lwjppz.mindstorm.common.core.support.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * <p>
 * 系统日志记录服务
 * </p>
 *
 * @author : lwj
 * @since : 2021-05-15
 */
@FeignClient(value = "mindstorm-system", fallbackFactory = RemoteLogFallbackFactory.class)
public interface RemoteLogFeignService {

    /**
     * 保存系统日志
     *
     * @param sysLog 日志信息
     * @return 结果
     */
    @PostMapping("/system/log/create")
    CommonResult saveLog(SysLog sysLog);

    /**
     * 保存系统访问记录
     *
     * @param username 用户账号（用户名）
     * @param status   登录状态
     * @param message  提示消息
     * @return 返回结果
     */
    @GetMapping("/system/visit/create")
    CommonResult saveLoginVisit(@RequestParam("username") String username,
                                @RequestParam("status") Integer status,
                                @RequestParam("message") String message);
}
