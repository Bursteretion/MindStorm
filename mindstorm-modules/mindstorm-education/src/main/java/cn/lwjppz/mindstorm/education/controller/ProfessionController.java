package cn.lwjppz.mindstorm.education.controller;

import cn.lwjppz.mindstorm.common.core.support.CommonResult;
import cn.lwjppz.mindstorm.education.model.vo.profession.ProfessionQueryVO;
import cn.lwjppz.mindstorm.education.model.vo.profession.ProfessionVO;
import cn.lwjppz.mindstorm.education.service.ProfessionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 开设专业表 前端控制器
 * </p>
 *
 * @author lwj
 * @since 2021-06-07
 */
@RestController
@RequestMapping("/education/profession")
@Api(tags = "开设专业Api接口")
public class ProfessionController {

    private final ProfessionService professionService;

    public ProfessionController(ProfessionService professionService) {
        this.professionService = professionService;
    }

    @GetMapping("/list")
    @ApiOperation("获取所有专业信息")
    public CommonResult listProfessions() {
        var professions = professionService.convertToProfessionDTO(professionService.getProfessions());
        return CommonResult.ok().data("professions", professions);
    }

    @GetMapping("/listSelect")
    @ApiOperation("获取所有专业信息（选择组件）")
    public CommonResult listSelectProfessions() {
        var professions = professionService.convertToSimpleProfessionDTO(professionService.getProfessions());
        return CommonResult.ok().data("professions", professions);
    }

    @GetMapping("list-unDisable")
    @ApiOperation("获取所有未禁用的专业信息")
    public CommonResult listUnDisableProfessions() {
        var unDisableProfessions =
                professionService.convertToProfessionDTO(professionService.getUnDisableProfessions());
        return CommonResult.ok().data("unDisableProfessions", unDisableProfessions);
    }

    @GetMapping("/list/{academyId}")
    @ApiOperation("根据院系Id获取该院系开设的所有专业")
    public CommonResult listProfessionsByAcademyId(@ApiParam("院系Id") @PathVariable("academyId") String academyId) {
        var professions =
                professionService.convertToSimpleProfessionDTO(professionService.getProfessionsByAcademyId(academyId));
        return CommonResult.ok().data("professions", professions);
    }

    @GetMapping("/page/{pageNum}/{pageSize}")
    @ApiOperation("分页查询专业信息")
    public CommonResult pageProfessions(@ApiParam("页码") @PathVariable("pageNum") Integer pageNums,
                                        @ApiParam("每页条数") @PathVariable("pageSize") Integer pageSize) {
        var resPage = professionService.pageProfessions(pageNums, pageSize);
        return CommonResult.ok().data("pageProfessions", resPage);
    }

    @PostMapping("/query")
    @ApiOperation("根据查询条件查询专业信息")
    public CommonResult query(@ApiParam("查询专业信息") @RequestBody ProfessionQueryVO professionQueryVO) {
        var resPage = professionService.queryProfessions(professionQueryVO);
        return CommonResult.ok().data("queryProfessions", resPage);
    }

    @PostMapping("/create")
    @ApiOperation("新增专业")
    public CommonResult create(@ApiParam("专业信息") @RequestBody ProfessionVO professionVO) {
        var profession = professionService.insertProfession(professionVO);
        return CommonResult.ok().data("profession", profession);
    }

    @PostMapping("update")
    @ApiOperation("更新专业信息")
    public CommonResult update(@ApiParam("专业信息") @RequestBody ProfessionVO professionVO) {
        var updatedProfession = professionService.updateProfession(professionVO);
        return CommonResult.ok().data("updatedProfession", updatedProfession);
    }

    @GetMapping("/info/{professionId}")
    @ApiOperation("根据专业Id获取专业信息")
    public CommonResult info(@ApiParam("专业Id") @PathVariable("professionId") String professionId) {
        var professionInfo = professionService.convertToProfessionDTO(professionService.infoProfession(professionId));
        return CommonResult.ok().data("professionInfo", professionInfo);
    }

    @PutMapping("/change/{professionId}/{status}")
    @ApiOperation("改变专业状态")
    public CommonResult changeProfessionStatus(@ApiParam("专业Id") @PathVariable("professionId") String professionId,
                                               @ApiParam("状态") @PathVariable("status") Integer status) {
        var success = professionService.changeProfessionStatus(professionId, status);
        return CommonResult.ok().data("success", success);
    }

    @DeleteMapping("/delete/{professionId}")
    @ApiOperation("根据专业Id删除该专业")
    public CommonResult delete(@ApiParam("专业Id") @PathVariable("professionId") String professionId) {
        var deleted = professionService.deleteProfession(professionId);
        return CommonResult.ok().data("deleted", deleted);
    }

    @DeleteMapping("/batchDelete")
    @ApiOperation("批量删除专业信息")
    public CommonResult batchDelete(@ApiParam("专业Id集合") @RequestParam("professionIds") List<String> professionIds) {
        var deleted = professionService.batchDeleteProfession(professionIds);
        return CommonResult.ok().data("deleted", deleted);
    }

}

