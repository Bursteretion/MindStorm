package cn.lwjppz.mindstorm.system.service.impl;

import cn.lwjppz.mindstorm.common.core.utils.IpUtils;
import cn.lwjppz.mindstorm.system.mapper.VisitMapper;
import cn.lwjppz.mindstorm.system.model.entity.Visit;
import cn.lwjppz.mindstorm.system.service.VisitService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import eu.bitwalker.useragentutils.UserAgent;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Date;

/**
 * <p>
 * 系统访问记录服务实现类
 * </p>
 *
 * @author lwj
 * @since 2021-05-31
 */
@Service
public class VisitServiceImpl extends ServiceImpl<VisitMapper, Visit> implements VisitService {

    @Override
    public Visit saveVisit(@NonNull String username, @NonNull Integer status, @NonNull String message) {
        Assert.hasText(username, "The user name cannot be empty, when saving system access records.");
        Assert.notNull(status, "The status cannot be null, when saving system access records.");
        Assert.hasText(message, "The message cannot be empty, when saving system access records.");

        // 获取访问用户客户端信息
        UserAgent userAgent = IpUtils.getUserAgent();
        // 获取访问用户所在地域信息
        JSONObject configs = JSON.parseObject(IpUtils.getAddresses(IpUtils.getOutIPV4()));
        Visit visit = Visit.builder()
                .username(username)
                .ipAddress(configs.getString("ip"))
                .browser(userAgent.getBrowser().getName())
                .os(userAgent.getOperatingSystem().getName())
                .loginLocation(configs.getString("addr").split(" ")[0])
                .loginTime(new Date())
                .status(status)
                .message(message)
                .build();

        baseMapper.insert(visit);

        return visit;
    }
}
