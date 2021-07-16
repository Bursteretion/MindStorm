package cn.lwjppz.mindstorm.common.core.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static cn.lwjppz.mindstorm.common.core.constant.Constants.FILE_SEPARATOR;

/**
 * @author : lwj
 * @since : 2020-08-11
 */
public final class ServiceUtils {

    /**
     * 获取不带破折号的随机UUID
     *
     * @return 不带破折号的UUID
     */
    @NonNull
    public static String randomUuidWithoutDash() {
        return StringUtils.remove(UUID.randomUUID().toString(), '-');
    }

    public static String generateRandomInvitationCode() {
        String[] chars = new String[]{"a", "b", "c", "d", "e", "f",
                "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
                "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",
                "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I",
                "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
                "W", "X", "Y", "Z"};
        StringBuffer sb = new StringBuffer();
        String uuid = UUID.randomUUID().toString().replace("-", "");
        for (int i = 0; i < 8; i++) {
            String str = uuid.substring(i * 4, i * 4 + 4);
            int x = Integer.parseInt(str, 16);
            sb.append(chars[x % 0x3E]);
        }
        return sb.toString();
    }

    /**
     * 将文件分隔符更改为url分隔符
     *
     * @param pathname 全路径名
     * @return url路径名
     */
    public static String changeFileSeparatorToUrlSeparator(@NonNull String pathname) {
        Assert.hasText(pathname, "路径名不能为空！");

        return pathname.replace(FILE_SEPARATOR, "/");
    }

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
    public static <K, T, V> Map<K, V> convertToMap(@Nullable Collection<T> list, @NonNull Function<T, K> keyFunction,
                                                   @NonNull Function<T, V> valueFunction) {
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

    /**
     * 解决 Feign 远程调用返回 LinkedHashMap 问题
     *
     * @param value feign调用产生的 LinkedHashMap
     * @param clazz 要转换的类型Class对象
     * @param <T>   类型
     */
    public static <T> T feignValueConvert(Object value, Class<T> clazz) {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.convertValue(value, clazz);
    }

}
