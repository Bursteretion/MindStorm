package cn.lwjppz.mindstorm.common.core.support;

import org.springframework.util.Assert;

import java.util.stream.Stream;

/**
 * <p>
 * 项目所有枚举的基类
 * </p>
 *
 * @author : lwj
 * @since : 2021-05-10
 */
public interface ValueEnum<T> {

    /**
     * Converts a value to the value of the corresponding enumeration type.
     *
     * @param enumType enum type
     * @param value    the value
     * @param <V>      value type
     * @param <E>      enum type
     * @return the value corresponding enum
     */
    static <V, E extends ValueEnum<V>> E valueToEnum(Class<E> enumType, V value) {
        Assert.notNull(enumType, "The enum must not be null!");
        Assert.notNull(value, "The value must not null!");
        Assert.isTrue(enumType.isEnum(), "The type must be enum type!");

        return Stream.of(enumType.getEnumConstants())
                .filter(item -> item.getValue().equals(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown enum value：" + value));
    }

    /**
     * Get enum value
     *
     * @return enum value
     */
    T getValue();
}
