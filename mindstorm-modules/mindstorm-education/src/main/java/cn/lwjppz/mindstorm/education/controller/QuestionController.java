package cn.lwjppz.mindstorm.education.controller;


import cn.lwjppz.mindstorm.common.core.support.CommonResult;
import cn.lwjppz.mindstorm.education.model.vo.question.QuestionQueryVO;
import cn.lwjppz.mindstorm.education.model.vo.question.QuestionVO;
import cn.lwjppz.mindstorm.education.service.QuestionService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/create")
    @ApiOperation("新增题目")
    public CommonResult createQuestion(@ApiParam("题目信息") @RequestBody QuestionVO questionVO) {
        var success = questionService.createQuestion(questionVO);
        return CommonResult.ok().data("success", success);
    }

    @PostMapping("/update")
    @ApiOperation("更新题目信息")
    public CommonResult updateQuestion(@ApiParam("题目信息") @RequestBody QuestionVO questionVO) {
        var success = questionService.updateQuestion(questionVO);
        return CommonResult.ok().data("success", success);
    }

    @DeleteMapping("/delete/{questionId}")
    @ApiOperation("删除题目")
    public CommonResult deleteQuestion(@ApiParam("题目Id") @PathVariable("questionId") String questionId) {
        var success = questionService.deleteQuestion(questionId);
        return CommonResult.ok().data("success", success);
    }

}

