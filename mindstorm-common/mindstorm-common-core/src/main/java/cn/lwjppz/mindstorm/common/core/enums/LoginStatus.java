package cn.lwjppz.mindstorm.common.core.enums;

import cn.lwjppz.mindstorm.common.core.support.ValueEnum;

/**
 * <p>
 * 登录状态枚举
 * </p>
 *
 * @author : lwj
 * @since : 2021-05-31
 */
public enum LoginStatus implements ValueEnum<Integer> {

    /**
     * 登录成功
     */
    SUCCESS(1, "登录成功"),

    /**
     * 登录失败
     */
    FAILED(0, "登录失败");

    private final Integer value;
    private final String name;

    LoginStatus(Integer value, String name) {
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
