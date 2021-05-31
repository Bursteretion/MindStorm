package cn.lwjppz.mindstorm.system.service;

import cn.lwjppz.mindstorm.system.model.entity.Visit;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.lang.NonNull;

/**
 * <p>
 * 系统访问记录服务类
 * </p>
 *
 * @author lwj
 * @since 2021-05-31
 */
public interface VisitService extends IService<Visit> {

    /**
     * 保存系统访问记录
     *
     * @param username 用户名
     * @param status   登录状态
     * @param message  提示消息
     * @return 访问记录
     */
    Visit saveVisit(@NonNull String username, @NonNull Integer status, @NonNull String message);

}
