package cn.lwjppz.mindstorm.education.controller;

import cn.lwjppz.mindstorm.common.core.support.CommonResult;
import cn.lwjppz.mindstorm.education.service.IdeaService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 知识点Api接口
 * </p>
 *
 * @author : lwj
 * @since : 2021-07-13
 */
@RestController
@RequestMapping("/education/idea")
public class IdeaController {

    private final IdeaService ideaService;

    public IdeaController(IdeaService ideaService) {
        this.ideaService = ideaService;
    }

    @GetMapping("/list")
    @ApiOperation("获取所有知识点信息")
    public CommonResult listIdeas() {
        return CommonResult.ok();
    }

}
