package cn.lwjppz.mindstorm.knowledge.controller;


import cn.lwjppz.mindstorm.common.core.support.CommonResult;
import cn.lwjppz.mindstorm.knowledge.model.dto.idea.IdeaDTO;
import cn.lwjppz.mindstorm.knowledge.model.entity.SubjectIdea;
import cn.lwjppz.mindstorm.knowledge.model.vo.SubjectIdeaVO;
import cn.lwjppz.mindstorm.knowledge.service.SubjectIdeaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 学科知识点Api接口
 * </p>
 *
 * @author lwj
 * @since 2021-05-27
 */
@RestController
@RequestMapping("/knowledge/subject-idea")
@Api(tags = "学科知识点Api控制器")
public class SubjectIdeaController {

    private final SubjectIdeaService subjectIdeaService;

    public SubjectIdeaController(SubjectIdeaService subjectIdeaService) {
        this.subjectIdeaService = subjectIdeaService;
    }

    @GetMapping("/treeSubjectIdea/{subjectId}")
    @ApiOperation("通过学科Id获取该学科的知识点树")
    public CommonResult treeSubjectIdea(@ApiParam("学科Id") @PathVariable("subjectId") String subjectId) {
        List<IdeaDTO> treeIdeas = subjectIdeaService.getTreeIdeas(subjectId);
        return CommonResult.ok().data("treeIdeas", treeIdeas);
    }

    @PostMapping("/create")
    @ApiOperation("新增学科知识点关联信息")
    public CommonResult create(@ApiParam("学科知识点关联信息") @RequestBody SubjectIdeaVO subjectIdeaVO) {
        var subjectIdea = subjectIdeaService.insertSubjectIdea(subjectIdeaVO);
        return CommonResult.ok().data("subjectIdea", subjectIdea);
    }

}

