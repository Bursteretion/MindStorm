package cn.lwjppz.mindstorm.file.controller;

import cn.lwjppz.mindstorm.common.core.support.CommonResult;
import cn.lwjppz.mindstorm.file.service.QuestionAttachmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p></p>
 *
 * @author : lwj
 * @since : 2021-07-25
 */
@RestController
@RequestMapping("/file/question-attachment")
@Api(tags = "题目附件API")
public class QuestionAttachmentController {

    private final QuestionAttachmentService questionAttachmentService;

    public QuestionAttachmentController(@Lazy QuestionAttachmentService questionAttachmentService) {
        this.questionAttachmentService = questionAttachmentService;
    }

    @PostMapping("/upload/image")
    @ApiOperation("上传课程封面")
    public CommonResult upload(@ApiParam("课程封面") MultipartFile image) {
        var questionAttachment = questionAttachmentService.uploadQuestionImage(image);
        return CommonResult.ok().data("questionAttachment", questionAttachment);
    }

}
