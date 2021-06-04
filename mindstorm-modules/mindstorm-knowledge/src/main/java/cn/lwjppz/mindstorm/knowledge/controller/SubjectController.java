package cn.lwjppz.mindstorm.knowledge.controller;


import cn.lwjppz.mindstorm.common.core.support.CommonResult;
import cn.lwjppz.mindstorm.knowledge.model.dto.subject.SubjectDTO;
import cn.lwjppz.mindstorm.knowledge.model.entity.Subject;
import cn.lwjppz.mindstorm.knowledge.model.vo.subject.SubjectVO;
import cn.lwjppz.mindstorm.knowledge.service.SubjectService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 学科Api接口
 * </p>
 *
 * @author lwj
 * @since 2021-05-27
 */
@RestController
@RequestMapping("/knowledge/subject")
public class SubjectController {

    private final SubjectService subjectService;

    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @GetMapping("/list")
    @ApiOperation("获取所有学科信息")
    public CommonResult listSubjects() {
        List<SubjectDTO> subjectDtoS = subjectService.convertToSubjectDTO(subjectService.getSubjects());
        return CommonResult.ok().data("subjectDtoS", subjectDtoS);
    }

    @GetMapping("/listByLevel")
    @ApiOperation("根据年级获取所有学科信息")
    public CommonResult listSubjects(@ApiParam("年级") @RequestParam("level") Integer level) {
        List<SubjectDTO> subjectDtoS = subjectService.convertToSubjectDTO(subjectService.getSubjects(level));
        return CommonResult.ok().data("subjectDtoS", subjectDtoS);
    }

    @GetMapping("/page/{pageIndex}/{pageSize}")
    @ApiOperation("分页学科信息")
    public CommonResult pageBy(@ApiParam("第几页") @PathVariable("pageIndex") Integer pageIndex,
                               @ApiParam("每页条数") @PathVariable("pageSize") Integer pageSize) {
        IPage<SubjectDTO> iPage = subjectService.pageBy(pageIndex, pageSize);
        return CommonResult.ok().data("page", iPage);
    }

    @PostMapping("/create")
    @ApiOperation("新建学科")
    public CommonResult create(@ApiParam("学科信息") @RequestBody SubjectVO subjectVO) {
        Subject subject = subjectService.insertSubject(subjectVO);
        return CommonResult.ok().data("subject", subject);
    }

    @PutMapping("/info")
    @ApiOperation("根据学科Id获取学科信息")
    public CommonResult info(@ApiParam("学科Id") @RequestParam("subjectId") String subjectId) {
        Subject subject = subjectService.getSubject(subjectId);
        return CommonResult.ok().data("subjectInfo", subject);
    }

    @PostMapping("/update")
    @ApiOperation("更新学科信息")
    public CommonResult update(@ApiParam("学科信息") @RequestBody SubjectVO subjectVO) {
        Subject subject = subjectService.updateSubject(subjectVO);
        return CommonResult.ok().data("subjectUpdated", subject);
    }

    @DeleteMapping("/delete/{subjectId}")
    @ApiOperation("根据学科Id删除学科信息")
    public CommonResult delete(@ApiParam("学科Id") @PathVariable("subjectId") String subjectId) {
        boolean deleted = subjectService.deleteSubject(subjectId);
        return CommonResult.ok().data("deleted", deleted);
    }

    @DeleteMapping("/deleteBatch")
    @ApiOperation("根据学科Id集合批量删除学科信息")
    public CommonResult delete(@ApiParam("学科Id集合") @RequestBody List<String> subjectIds) {
        boolean deleted = subjectService.deleteBatchSubjects(subjectIds);
        return CommonResult.ok().data("deleted", deleted);
    }

}

