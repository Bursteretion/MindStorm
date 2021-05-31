package cn.lwjppz.mindstorm.system.service;

import cn.lwjppz.mindstorm.api.system.model.SysLog;
import cn.lwjppz.mindstorm.system.model.entity.Log;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.lang.NonNull;

/**
 * <p>
 * 日志服务类
 * </p>
 *
 * @author lwj
 * @since 2021-05-30
 */
public interface LogService extends IService<Log> {


    /**
     * 保存系统日志信息
     *
     * @param sysLog 系统日志
     * @return 日志信息
     */
    Log saveLog(@NonNull SysLog sysLog);

}
