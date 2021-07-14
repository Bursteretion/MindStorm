package cn.lwjppz.mindstorm.education.controller;

import cn.lwjppz.mindstorm.common.core.support.CommonResult;
import cn.lwjppz.mindstorm.education.model.dto.idea.IdeaTreeDTO;
import cn.lwjppz.mindstorm.education.model.vo.ProfessionIdeaVO;
import cn.lwjppz.mindstorm.education.service.ProfessionIdeaService;
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
@RequestMapping("/education/profession-idea")
@Api(tags = "专业知识点Api控制器")
public class ProfessionIdeaController {

    private final ProfessionIdeaService professionIdeaService;

    public ProfessionIdeaController(ProfessionIdeaService professionIdeaService) {
        this.professionIdeaService = professionIdeaService;
    }

    @GetMapping("/treeProfessionIdea/{professionId}")
    @ApiOperation("通过专业Id获取该学科的知识点树")
    public CommonResult treeProfessionIdea(@ApiParam("学科Id") @PathVariable("professionId") String professionId) {
        List<IdeaTreeDTO> treeIdeas = professionIdeaService.getTreeIdeas(professionId);
        return CommonResult.ok().data("treeIdeas", treeIdeas);
    }

    @PostMapping("/create")
    @ApiOperation("新增专业知识点关联信息")
    public CommonResult create(@ApiParam("学科知识点关联信息") @RequestBody ProfessionIdeaVO professionIdeaVO) {
        var professionIdea = professionIdeaService.insertProfessionIdea(professionIdeaVO);
        return CommonResult.ok().data("professionIdea", professionIdea);
    }

}

