package cn.lwjppz.mindstorm.common.core.support;

import cn.lwjppz.mindstorm.common.core.enums.ResultStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 统一返回结果
 * </p>
 *
 * @author : lwj
 * @since : 2021-05-09
 */
@ApiModel("统一返回结果")
@Data
public class CommonResult {

    @ApiModelProperty("是否成功")
    private Boolean success;

    @ApiModelProperty("返回码")
    private Integer code;

    @ApiModelProperty("返回信息")
    private String message;

    @ApiModelProperty("开发信息")
    private String devMessage;

    @ApiModelProperty("返回数据")
    private Map<String, Object> data = new HashMap<>(20);

    /**
     * 构造方法私有化
     */
    private CommonResult() {
    }

    /**
     * 成功的静态方法
     *
     * @return CommonResult
     */
    public static CommonResult ok() {
        CommonResult resultVo = new CommonResult();
        resultVo.setSuccess(true);
        resultVo.setCode(ResultStatus.SUCCESS.getCode());
        resultVo.setMessage(ResultStatus.SUCCESS.getMessage());
        return resultVo;
    }

    /**
     * 失败的静态方法
     *
     * @return CommonResult
     */
    public static CommonResult error() {
        CommonResult resultVo = new CommonResult();
        resultVo.setSuccess(false);
        resultVo.setCode(ResultStatus.ERROR.getCode());
        resultVo.setMessage(ResultStatus.ERROR.getMessage());
        return resultVo;
    }


    public CommonResult success(Boolean success) {
        this.setSuccess(success);
        return this;
    }

    public CommonResult message(String message) {
        this.setMessage(message);
        return this;
    }

    public CommonResult code(Integer code) {
        this.setCode(code);
        return this;
    }

    public CommonResult data(String key, Object value) {
        this.data.put(key, value);
        return this;
    }

    public CommonResult data(Map<String, Object> map) {
        this.setData(map);
        return this;
    }

    public CommonResult codeAndMessage(IResultStatus resultCode) {
        this.setCode(resultCode.getCode());
        this.setMessage(resultCode.getMessage());
        return this;
    }
}
