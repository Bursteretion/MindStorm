package cn.lwjppz.mindstorm.common.log.service;

import cn.lwjppz.mindstorm.api.system.feign.RemoteLogFeignService;
import cn.lwjppz.mindstorm.api.system.model.SysLog;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 异步调用日志服务保存系统日志
 * </p>
 *
 * @author : lwj
 * @since : 2021-06-01
 */
@Service
public class AsyncLogService {

    private final RemoteLogFeignService remoteLogFeignService;

    public AsyncLogService(RemoteLogFeignService remoteLogFeignService) {
        this.remoteLogFeignService = remoteLogFeignService;
    }

    /**
     * 保存系统日志
     *
     * @param sysLog 系统日志信息
     */
    @Async
    public void saveSysLog(SysLog sysLog) {
        remoteLogFeignService.saveLog(sysLog);
    }
}
