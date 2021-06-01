package cn.lwjppz.mindstorm.common.security.feign;

import cn.lwjppz.mindstorm.common.core.constant.CacheConstants;
import cn.lwjppz.mindstorm.common.core.utils.IpUtils;
import cn.lwjppz.mindstorm.common.core.utils.ServletUtils;
import cn.lwjppz.mindstorm.common.core.utils.StringUtils;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * feign 请求拦截器
 * </p>
 *
 * @author : lwj
 * @since : 2021-05-25
 */
@Component
public class FeignRequestInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        HttpServletRequest httpServletRequest = ServletUtils.getRequest();
        if (StringUtils.isNotNull(httpServletRequest)) {
            // 传递用户信息请求头，防止丢失
            String userId = httpServletRequest.getHeader(CacheConstants.DETAILS_USER_ID);
            if (StringUtils.isNotEmpty(userId)) {
                requestTemplate.header(CacheConstants.DETAILS_USER_ID, userId);
            }
            String userName = httpServletRequest.getHeader(CacheConstants.DETAILS_USERNAME);
            if (StringUtils.isNotEmpty(userName)) {
                requestTemplate.header(CacheConstants.DETAILS_USERNAME, userName);
            }
            String authentication = httpServletRequest.getHeader(CacheConstants.AUTHORIZATION_HEADER);
            if (StringUtils.isNotEmpty(authentication)) {
                requestTemplate.header(CacheConstants.AUTHORIZATION_HEADER, authentication);
            }
            String agent = httpServletRequest.getHeader("user-agent");
            if (StringUtils.isNotEmpty(agent)) {
                requestTemplate.header("User-Agent", agent);
            }

            // 配置客户端IP
            requestTemplate.header("X-Forwarded-For", IpUtils.getIpAddr(ServletUtils.getRequest()));
        }
    }
}
