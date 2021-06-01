package cn.lwjppz.mindstorm.system.service.impl;

import cn.lwjppz.mindstorm.api.system.model.SysLog;
import cn.lwjppz.mindstorm.system.model.entity.Log;
import cn.lwjppz.mindstorm.system.mapper.LogMapper;
import cn.lwjppz.mindstorm.system.service.LogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * <p>
 * 日志服务实现类
 * </p>
 *
 * @author lwj
 * @since 2021-05-30
 */
@Service
public class LogServiceImpl extends ServiceImpl<LogMapper, Log> implements LogService {

    @Override
    public Log saveLog(@NonNull SysLog sysLog) {
        Assert.notNull(sysLog, "The system log info must not be null.");

        Log log = Log.builder().build();
        BeanUtils.copyProperties(sysLog, log);
        baseMapper.insert(log);

        return log;
    }
}
