package cn.lwjppz.mindstorm.common.core.enums.type;

import cn.lwjppz.mindstorm.common.core.support.ValueEnum;

/**
 * <p></p>
 *
 * @author : lwj
 * @since : 2021-07-20
 */
public enum QuestionType implements ValueEnum<Integer> {

    /**
     * 题目类型枚举
     */
    SINGLE_CHOICE(0, "单选题"),

    MULTIPLE_CHOICE(1, "多选题"),

    FILL_BLANK(2, "填空题"),

    TRUE_FALSE(3, "判断题"),

    SHORT_ANSWER(4, "简答题");

    private final Integer value;
    private final String name;

    QuestionType(Integer value, String name) {
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
