package cn.lwjppz.mindstorm.common.core.enums.type;

import cn.lwjppz.mindstorm.common.core.support.ValueEnum;

/**
 * <p></p>
 *
 * @author : lwj
 * @since : 2021-07-20
 */
public enum QuestionOptionType implements ValueEnum<Integer> {

    /**
     * 题目选项标签枚举
     */
    A(0, "选项A"),

    B(1, "选项B"),

    C(2, "选项C"),

    D(3, "选项D"),

    E(4, "选项E"),

    F(5, "选项F"),

    G(6, "选项G"),

    H(7, "选项H");

    private final Integer value;
    private final String name;

    QuestionOptionType(Integer value, String name) {
        this.value = value;
        this.name = name;
    }

    @Override
    public Integer getValue() {
        return value;
    }
}
