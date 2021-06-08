package cn.lwjppz.mindstorm.common.core.enums.type;

import cn.lwjppz.mindstorm.common.core.support.ValueEnum;

/**
 * <p>
 * 菜单类型枚举
 * </p>
 *
 * @author : lwj
 * @since : 2021-05-18
 */
public enum MenuType implements ValueEnum<Integer> {

    /**
     * 目录
     */
    CATALOG(0, "目录"),

    /**
     * 菜单
     */
    MENU(1, "菜单"),

    /**
     * 按钮
     */
    BUTTON(2, "按钮");

    private final Integer value;
    private final String name;

    MenuType(Integer value, String name) {
        this.value = value;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public Integer getValue() {
        return value;
    }
}
