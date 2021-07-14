package cn.lwjppz.mindstorm.education.controller;

import cn.lwjppz.mindstorm.common.core.support.CommonResult;
import cn.lwjppz.mindstorm.education.model.vo.academy.AcademyQueryVO;
import cn.lwjppz.mindstorm.education.model.vo.academy.AcademyVO;
import cn.lwjppz.mindstorm.education.service.AcademyService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 院系Api接口
 * </p>
 *
 * @author lwj
 * @since 2021-06-07
 */
@RestController
@RequestMapping("/education/academy")
public class AcademyController {

    private final AcademyService academyService;

    public AcademyController(AcademyService academyService) {
        this.academyService = academyService;
    }

    @GetMapping("/list")
    @ApiOperation("获取所有院系信息")
    public CommonResult listAcademies() {
        var academies = academyService.convertToAcademyDTO(academyService.getAcademies());
        return CommonResult.ok().data("academies", academies);
    }

    @GetMapping("/listSelect")
    @ApiOperation("获取院系信息下拉框")
    public CommonResult listSelectAcademies() {
        var academySelects = academyService.convertToAcademySelectDTO(academyService.getAcademies());
        return CommonResult.ok().data("academySelects", academySelects);
    }

    @GetMapping("/list-unDisable")
    @ApiOperation("获取所有未禁用的院系信息")
    public CommonResult listUnDisableAcademies() {
        var academies = academyService.convertToAcademyDTO(academyService.getUnDisableAcademies());
        return CommonResult.ok().data("unDisableAcademies", academies);
    }

    @GetMapping("/page/{pageNum}/{pageSize}")
    @ApiOperation("分页查询院系信息")
    public CommonResult pageAcademies(@ApiParam("页码") @PathVariable("pageNum") Integer pageNum,
                                      @ApiParam("每页条数") @PathVariable("pageSize") Integer pageSize) {
        var resPage = academyService.pageAcademies(pageNum, pageSize);
        return CommonResult.ok().data("academyPage", resPage);
    }

    @PostMapping("/query")
    @ApiOperation("根据院系信息查询院系")
    public CommonResult query(@ApiParam("院系查询信息") @RequestBody(required = false) AcademyQueryVO academyQueryVO) {
        var academyTree = academyService.generateAcademyTree(academyService.queryAcademies(academyQueryVO));
        return CommonResult.ok().data("academyTree", academyTree);
    }

    @PostMapping("/create")
    @ApiOperation("新增院系")
    public CommonResult create(@ApiParam("院系信息") @RequestBody AcademyVO academyVO) {
        var academy = academyService.insertAcademy(academyVO);
        return CommonResult.ok().data("academy", academy);
    }

    @PostMapping("/update")
    @ApiOperation("更新院系信息")
    public CommonResult update(@ApiParam("院系信息") @RequestBody AcademyVO academyVO) {
        var academy = academyService.updateAcademy(academyVO);
        return CommonResult.ok().data("updatedAcademy", academy);
    }

    @PutMapping("/info/{academyId}")
    @ApiOperation("根据院系Id获取院系信息")
    public CommonResult info(@ApiParam("院系Id") @PathVariable("academyId") String academyId) {
        var academyInfo = academyService.infoAcademy(academyId);
        return CommonResult.ok().data("academyInfo", academyInfo);
    }

    @PutMapping("/info/remote/{academyId}")
    @ApiOperation("根据院系Id获取院系信息（远程调用）")
    public CommonResult remoteInfo(@ApiParam("院系Id") @PathVariable("academyId") String academyId) {
        var academyTo = academyService.remoteInfoAcademy(academyId);
        return CommonResult.ok().data("academyTo", academyTo);
    }

    @PutMapping("/change/{academyId}/{status}")
    @ApiOperation("更改院系状态")
    public CommonResult changeAcademyStatus(@ApiParam("院系Id") @PathVariable("academyId") String academyId,
                                            @ApiParam("状态") @PathVariable("status") Integer status) {
        var success = academyService.changeStatus(academyId, status);
        return CommonResult.ok().data("success", success);
    }

    @DeleteMapping("/delete/{academyId}")
    @ApiOperation("根据院系Id删除院系信息")
    public CommonResult delete(@ApiParam("院系Id") @PathVariable("academyId") String academyId) {
        var deleted = academyService.deleteAcademy(academyId);
        return CommonResult.ok().data("deleted", deleted);
    }

    @DeleteMapping("/batchDelete")
    @ApiOperation("批量删除院系信息")
    public CommonResult batchDelete(@ApiParam("院系Id集合") @RequestParam("academyIds") List<String> academyIds) {
        var deleted = academyService.batchDeleteAcademy(academyIds);
        return CommonResult.ok().data("deleted", deleted);
    }

}
