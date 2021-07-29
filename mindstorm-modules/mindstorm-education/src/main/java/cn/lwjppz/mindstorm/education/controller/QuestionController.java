package cn.lwjppz.mindstorm.education.controller;

import cn.lwjppz.mindstorm.common.core.support.CommonResult;
import cn.lwjppz.mindstorm.education.model.vo.question.*;
import cn.lwjppz.mindstorm.education.service.QuestionService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * <p>
 * 题目表 前端控制器
 * </p>
 *
 * @author lwj
 * @since 2021-07-20
 */
@RestController
@RequestMapping("/education/question")
public class QuestionController {

    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @PostMapping("/query")
    @ApiOperation("条件查询题库")
    public CommonResult query(@ApiParam("查询条件") @RequestBody QuestionQueryVO questionQueryVO) {
        var queryQuestionPage = questionService.listQuestions(questionQueryVO);
        return CommonResult.ok().data("queryQuestionPage", queryQuestionPage);
    }

    @GetMapping("/list/folder")
    @ApiOperation("获取该课程下树形题目文件夹信息")
    public CommonResult listFolders(@ApiParam("课程Id") @RequestParam("courseId") String courseId) {
        var treeFolders = questionService.listTreeFolders(courseId);
        return CommonResult.ok().data("treeFolders", treeFolders);
    }

    @PostMapping("/create")
    @ApiOperation("新增题目")
    public CommonResult createQuestion(@ApiParam("题目信息") @RequestBody QuestionVO questionVO) {
        var question = questionService.createQuestion(questionVO);
        return CommonResult.ok().data("question", question);
    }

    @GetMapping("/info/question/{questionId}")
    @ApiOperation("获取题目信息")
    public CommonResult infoQuestion(@ApiParam("题目Id") @PathVariable("questionId") String questionId) {
        var question = questionService.convertToQuestionDetailDTO(questionService.infoQuestion(questionId));
        return CommonResult.ok().data("question", question);
    }

    @GetMapping("/info/folder/{folderId}")
    @ApiOperation("获取题目信息")
    public CommonResult infoFolder(@ApiParam("文件夹Id") @PathVariable("folderId") String folderId) {
        var folder = questionService.infoFolder(folderId);
        return CommonResult.ok().data("folder", folder);
    }

    @PostMapping("/update/question")
    @ApiOperation("更新题目信息")
    public CommonResult updateQuestion(@ApiParam("题目信息") @RequestBody QuestionVO questionVO) {
        var success = questionService.updateQuestion(questionVO);
        return CommonResult.ok().data("success", success);
    }

    @PostMapping("/update/folder")
    @ApiOperation("重命名文件夹")
    public CommonResult renameFolder(@ApiParam("文件夹信息") @RequestBody QuestionFolderVO questionFolderVO) {
        var success = questionService.renameFolder(questionFolderVO);
        return CommonResult.ok().data("success", success);
    }

    @PostMapping("/move")
    @ApiOperation("移动文件夹/题目")
    public CommonResult moveQuestion(@ApiParam("文件夹/题目Id信息") @RequestBody QuestionMoveVO questionMoveVO) {
        var success = questionService.moveQuestion(questionMoveVO);
        return CommonResult.ok().data("success", success);
    }

    @DeleteMapping("/delete/{questionId}")
    @ApiOperation("删除题目")
    public CommonResult deleteQuestion(@ApiParam("题目Id") @PathVariable("questionId") String questionId) {
        var success = questionService.deleteQuestion(questionId);
        return CommonResult.ok().data("success", success);
    }

    @PostMapping("/import")
    @ApiOperation("导入题目")
    public CommonResult importQuestion(@ApiParam("题目导入信息") QuestionUploadVO questionUploadVO) throws IOException {
        var success = questionService.doUpload(questionUploadVO);
        return CommonResult.ok().data("success", true);
    }

}

