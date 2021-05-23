package cn.lwjppz.mindstorm.common.core.utils;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author : lwj
 * @since : 2020-08-11
 */
public final class ServiceUtils {

    /**
     * Fetch target value to list. <br>
     * eg：<br>
     * fetchProperty(List<Post> posts, Post::getId) -> fetch post id list <br>
     * List<String> -> post id list
     *
     * @param data data list
     * @param <V>  to return data type
     * @param <T>  accept data type
     * @return a list target list
     */
    public static <V, T> List<V> fetchProperty(final Collection<T> data, Function<T, V> mappingFunction) {
        return CollectionUtils.isEmpty(data) ?
                Collections.emptyList() :
                data.stream().map(mappingFunction).collect(Collectors.toList());
    }

    /**
     * Converts to map (key from list data)
     *
     * @param list            data list
     * @param mappingFunction the function of handle V
     * @param <K>             he data type of return map's key
     * @param <V>             the data type of return map's value
     * @return a mpa which key from list data and value from data
     */
    @NonNull
    public static <K, V> Map<K, V> convertToMap(Collection<V> list, Function<V, K> mappingFunction) {
        Assert.notNull(mappingFunction, "mapping function must not be null");

        if (CollectionUtils.isEmpty(list)) {
            return Collections.emptyMap();
        }

        Map<K, V> resultMap = new HashMap<>(16);
        list.forEach(data -> resultMap.putIfAbsent(mappingFunction.apply(data), data));

        return resultMap;
    }

    /**
     * Converts to map (key from list data)
     *
     * @param list          data list
     * @param keyFunction   key mapping function
     * @param valueFunction value mapping function
     * @param <K>           the data type of return map's key
     * @param <T>           accept data type
     * @param <V>           the data type of return map's value
     * @return a mpa which key from list data and value from data
     */
    @NonNull
    public static <K, T, V> Map<K, V> convertToMap(@Nullable Collection<T> list, @NonNull Function<T, K> keyFunction, @NonNull Function<T, V> valueFunction) {
        Assert.notNull(keyFunction, "key mapping function must not be null.");
        Assert.notNull(valueFunction, "value mapping function must not be null.");

        if (CollectionUtils.isEmpty(list)) {
            return Collections.emptyMap();
        }

        Map<K, V> resultMap = new HashMap<>(16);
        // xxx.apply(data) return result depending on incoming lambda expression.
        list.forEach(data -> resultMap.putIfAbsent(keyFunction.apply(data), valueFunction.apply(data)));

        return resultMap;
    }

    /**
     * Checks if the given number id is empty id.
     *
     * @param id the given number id
     * @return true if the given number id is empty id; false otherwise
     */
    public static boolean isEmptyId(@Nullable Number id) {
        return id == null || id.longValue() <= 0;
    }

}
