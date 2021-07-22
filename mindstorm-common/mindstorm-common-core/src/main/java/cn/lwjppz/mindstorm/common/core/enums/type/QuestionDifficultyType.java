package cn.lwjppz.mindstorm.common.core.enums.type;

import cn.lwjppz.mindstorm.common.core.support.ValueEnum;

/**
 * <p></p>
 *
 * @author : lwj
 * @since : 2021-07-21
 */
public enum QuestionDifficultyType implements ValueEnum<Integer> {

    /**
     * 题目难度枚举
     */
    EASY(0, "简单"),

    MEDIUM(1, "中等"),

    DIFFICULT(2, "困难");

    private final Integer value;
    private final String name;

    QuestionDifficultyType(Integer value, String name) {
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
