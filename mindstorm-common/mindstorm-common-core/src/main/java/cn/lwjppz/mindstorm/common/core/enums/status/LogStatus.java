package cn.lwjppz.mindstorm.common.core.enums.status;

import cn.lwjppz.mindstorm.common.core.support.ValueEnum;

/**
 * <p>
 * 日志状态枚举
 * </p>
 *
 * @author : lwj
 * @since : 2021-05-30
 */
public enum LogStatus implements ValueEnum<Integer> {

    /**
     * 成功
     */
    SUCCESS(1, "成功"),

    /**
     * 失败
     */
    FAILURE(0, "失败");

    private final Integer value;
    private final String name;

    LogStatus(Integer value, String name) {
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
