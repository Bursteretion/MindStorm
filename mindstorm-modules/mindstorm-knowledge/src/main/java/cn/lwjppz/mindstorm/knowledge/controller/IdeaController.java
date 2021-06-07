package cn.lwjppz.mindstorm.knowledge.controller;

import cn.lwjppz.mindstorm.common.core.support.CommonResult;
import cn.lwjppz.mindstorm.knowledge.model.vo.idea.IdeaVO;
import cn.lwjppz.mindstorm.knowledge.service.IdeaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 知识点Api接口
 * </p>
 *
 * @author lwj
 * @since 2021-05-27
 */
@RestController
@RequestMapping("/knowledge/idea")
@Api(tags = "知识点Api控制器")
public class IdeaController {

    private final IdeaService ideaService;

    public IdeaController(IdeaService ideaService) {
        this.ideaService = ideaService;
    }

    @GetMapping("/tree")
    @ApiOperation("获取知识点树")
    public CommonResult treeIdeas() {
        var ideas = ideaService.getTreeIdeas();
        return CommonResult.ok().data("treeIdeas", ideas);
    }

    @PostMapping("/create")
    @ApiOperation("新增知识点")
    public CommonResult create(@ApiParam("知识点信息") @RequestBody IdeaVO ideaVO) {
        var idea = ideaService.insertIdea(ideaVO);
        return CommonResult.ok().data("idea", idea);
    }

    @GetMapping("/info/{ideaId}")
    @ApiOperation("根据知识点Id获取知识点信息")
    public CommonResult info(@ApiParam("知识点Id") @PathVariable("ideaId") String ideaId) {
        var idea = ideaService.getIdea(ideaId);
        return CommonResult.ok().data("idea", idea);
    }

    @PostMapping("/update")
    @ApiOperation("更新知识点")
    public CommonResult update(@ApiParam("知识点信息") @RequestBody IdeaVO ideaVO) {
        var idea = ideaService.updateIdea(ideaVO);
        return CommonResult.ok().data("updatedIdea", idea);
    }

    @DeleteMapping("/delete/{ideaId}")
    @ApiOperation("根据知识点Id删除知识点")
    public CommonResult delete(@ApiParam("知识点Id") @PathVariable("ideaId") String ideaId) {
        var deleted = ideaService.deleteIdea(ideaId);
        return CommonResult.ok().data("deleted", deleted);
    }

    @DeleteMapping("/deleteBatch")
    @ApiOperation("根据知识点Id集合删除知识点")
    public CommonResult deleteBatch(@ApiParam("知识点Id集合") @RequestBody List<String> ideaIds) {
        var deleted = ideaService.batchDeleteIdea(ideaIds);
        return CommonResult.ok().data("deleted", deleted);
    }

}

