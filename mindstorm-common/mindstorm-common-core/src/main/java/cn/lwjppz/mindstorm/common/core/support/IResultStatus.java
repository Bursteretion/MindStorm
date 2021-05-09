package cn.lwjppz.mindstorm.common.core.support;

/**
 * <p>
 * 提供http响应状态接口
 * </p>
 *
 * @author : lwj
 * @since : 2021-05-09
 */
public interface IResultStatus {
    /**
     * 获取响应码
     *
     * @return 响应码
     */
    Integer getCode();

    /**
     * 获取响应信息
     *
     * @return 响应信息
     */
    String getMessage();
}