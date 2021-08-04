package cn.lwjppz.mindstorm.education.controller;


import cn.lwjppz.mindstorm.common.core.support.CommonResult;
import cn.lwjppz.mindstorm.education.model.vo.exampaper.ExamPaperQueryVO;
import cn.lwjppz.mindstorm.education.model.vo.exampaper.ExamPaperVO;
import cn.lwjppz.mindstorm.education.service.ExamPaperService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 试卷表 前端控制器
 * </p>
 *
 * @author lwj
 * @since 2021-07-31
 */
@RestController
@RequestMapping("/education/exam-paper")
@Api(tags = "试卷Api")
public class ExamPaperController {

    private final ExamPaperService examPaperService;

    public ExamPaperController(ExamPaperService examPaperService) {
        this.examPaperService = examPaperService;
    }

    @PostMapping("/query")
    @ApiOperation(value = "查询某课程所有试卷")
    public CommonResult listExamPapers(@ApiParam("试卷查询条件") @RequestBody ExamPaperQueryVO examPaperQueryVO) {
        var examPaperPage = examPaperService.listExamPaper(examPaperQueryVO);
        return CommonResult.ok().data("examPaperPage", examPaperPage);
    }

    @PostMapping("/create")
    @ApiOperation(value = "新建试卷")
    public CommonResult createExamPaper(@ApiParam("试卷信息") @RequestBody ExamPaperVO examPaperVO) {
        var examPaper = examPaperService.createExamPaper(examPaperVO);
        return CommonResult.ok().data("examPaper", examPaper);
    }

    @GetMapping("/info")
    @ApiOperation(value = "获取试卷信息")
    public CommonResult infoExamPaper(@ApiParam("试卷Id") @RequestParam("examPaperId") String examPaperId) {
        var examPaper = examPaperService.convertToExamPaperDetailDTO(examPaperService.infoExamPaper(examPaperId));
        return CommonResult.ok().data("examPaper", examPaper);
    }

    @PostMapping("/update")
    @ApiOperation(value = "更新试卷信息")
    public CommonResult updateExamPaper(@ApiParam("试卷信息") @RequestBody ExamPaperVO examPaperVO) {
        var success = examPaperService.updateExamPaper(examPaperVO);
        return CommonResult.ok().data("success", success);
    }

    @DeleteMapping("/delete/{examPaperId}")
    @ApiOperation(value = "删除试卷")
    public CommonResult deleteExamPaper(@ApiParam("试卷Id") @PathVariable("examPaperId") String examPaperId) {
        var success = examPaperService.deleteExamPaper(examPaperId);
        return CommonResult.ok().data("success", success);
    }

}

