package cn.lwjppz.mindstorm.common.mybatis.common;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;

/**
 * <p></p>
 *
 * @author : lwj
 * @since : 2021-06-08
 */
public interface BaseInterface<T> {

    /**
     * 获取通用 LambdaQueryWrapper
     *
     * @return LambdaQueryWrapper
     */
    default LambdaQueryWrapper<T> getCommonQueryWrapper() {
        return Wrappers.lambdaQuery();
    }

}
