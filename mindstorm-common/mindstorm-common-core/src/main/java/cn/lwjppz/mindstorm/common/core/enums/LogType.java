package cn.lwjppz.mindstorm.common.core.enums;

import cn.lwjppz.mindstorm.common.core.support.ValueEnum;

/**
 * <p>
 * 系统日志类型枚举
 * </p>
 *
 * @author : lwj
 * @since : 2021-05-30
 */
public enum LogType implements ValueEnum<Integer> {

    /**
     * 系统日志类型
     */
    LOGGED_IN(1, "用户登录"),

    LOGGED_OUT(2, "退出登录"),

    OTHER(3, "其他日志");

    private final Integer value;
    private final String name;

    LogType(Integer value, String name) {
        this.value = value;
        this.name = name;
    }

    @Override
    public Integer getValue() {
        return value;
    }

    public String getName() {
        return name;
    }
}
