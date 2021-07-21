package cn.lwjppz.mindstorm.education.controller;


import cn.lwjppz.mindstorm.common.core.support.CommonResult;
import cn.lwjppz.mindstorm.education.model.vo.questiontype.QuestionTypeSimpleVO;
import cn.lwjppz.mindstorm.education.model.vo.questiontype.QuestionTypeVO;
import cn.lwjppz.mindstorm.education.service.QuestionTypeService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 题目类型（题型）表 前端控制器
 * </p>
 *
 * @author lwj
 * @since 2021-07-20
 */
@RestController
@RequestMapping("/education/question-type")
public class QuestionTypeController {

    private final QuestionTypeService questionTypeService;

    public QuestionTypeController(QuestionTypeService questionTypeService) {
        this.questionTypeService = questionTypeService;
    }

    @GetMapping("/list")
    @ApiOperation("获取所有题型")
    public CommonResult listQuestionTypes() {
        var questionTypes = questionTypeService.convertQuestionTypeDTO(questionTypeService.listQuestionTypes());
        return CommonResult.ok().data("questionTypes", questionTypes);
    }

    @GetMapping("/list/select")
    @ApiOperation("获取所有题型（Select选择器组件）")
    public CommonResult listQuestionTypesSelect() {
        var questionTypeSelects =
                questionTypeService.convertToQuestionTypeSelectDTO(questionTypeService.listQuestionTypes());
        return CommonResult.ok().data("questionTypeSelects", questionTypeSelects);
    }

    @PostMapping("/create")
    @ApiOperation("新增题型")
    public CommonResult createQuestionType(@ApiParam("题型信息") @RequestBody QuestionTypeVO questionTypeVO) {
        var questionType = questionTypeService.createQuestionType(questionTypeVO);
        return CommonResult.ok().data("questionType", questionType);
    }

    @PostMapping("/update")
    @ApiOperation("更新题型")
    public CommonResult updateQuestionType(@ApiParam("题型信息") @RequestBody QuestionTypeSimpleVO questionTypeSimpleVO) {
        var success = questionTypeService.updateQuestionType(questionTypeSimpleVO);
        return CommonResult.ok().data("success", success);
    }

    @DeleteMapping("/delete/{questionTypeId}")
    @ApiOperation("删除题型")
    public CommonResult deleteQuestionType(@ApiParam("题型Id") @PathVariable("questionTypeId") String questionTypeId) {
        var success = questionTypeService.deleteQuestionType(questionTypeId);
        return CommonResult.ok().data("success", success);
    }

}

