package cn.lwjppz.mindstorm.system.service.impl;

import cn.lwjppz.mindstorm.common.core.utils.IpUtils;
import cn.lwjppz.mindstorm.common.core.utils.ServletUtils;
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

        UserAgent userAgent = IpUtils.getUserAgent();
        JSONObject parse = JSON.parseObject(IpUtils.getAddresses(IpUtils.getOutIPV4()));
        Visit visit = Visit.builder()
                .username(username)
                .ipAddress(IpUtils.getInterIP1())
                .browser(userAgent.getBrowser().getName())
                .os(userAgent.getOperatingSystem().getName())
                .loginLocation(parse.getString("addr"))
                .loginTime(new Date())
                .status(status)
                .build();

        baseMapper.insert(visit);

        return visit;
    }
}
