package cn.lwjppz.mindstorm.common.core.enums.status;

import cn.lwjppz.mindstorm.common.core.support.IResultStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p>
 * 枚举结果信息
 * </p>
 *
 * @author : lwj
 * @since : 2021-05-09
 */
@AllArgsConstructor
@Getter
public enum ResultStatus implements IResultStatus {

    /**
     * 枚举结果
     */
    SUCCESS(20000, "操作成功"),

    ERROR(20001, "响应失败！"),

    UPLOAD_FILE_ERROR(20002, "附件上传失败！"),

    FILE_EMPTY(20003, "附件为空！"),

    DELETE_ERROR(20004, "删除失败！"),

    VALID_CODE_SEND_FAIL(20005, "短信验证码发送失败！"),

    LOGIN_ERROR(20006, "登录失败，用户名或密码错误！"),

    REGISTER_ERROR(20007, "注册失败！"),

    USER_EXIT_ERROR(20008, "当前用户已存在！"),

    PARAMS_ERROR(20009, "参数校验失败！"),

    LOGIN_MOBILE_ERROR(20010, "手机号不正确！"),

    LOGIN_PASSWORD_ERROR(20011, "密码不正确！"),

    CODE_ERROR(20012, "验证码不正确！"),

    NOT_LOGIN(20013, "无token，请重新登录！"),

    FETCH_USERINFO_ERROR(20014, "获取用户信息失败！"),

    ENTITY_NOT_FOUND(20015, "未找到该实体"),

    BAD_REQUEST(20016, "系统繁忙！"),

    MENU_EXIST(20017, "该菜单已存在"),

    USER_NOT_EXIST(20018, "当前用户不存在/密码错误"),

    USER_IS_DISABLE(20019, "当前用户已被停用"),

    USER_PASSWORD_NO_MATCH(20020, "用户原密码匹配错误"),

    FORBIDDEN(20021, "您无权访问，请联系管理员授权");

    private final Integer code;

    private final String message;
}
