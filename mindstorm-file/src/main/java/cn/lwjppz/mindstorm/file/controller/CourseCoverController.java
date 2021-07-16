package cn.lwjppz.mindstorm.file.controller;

import cn.lwjppz.mindstorm.common.core.support.CommonResult;
import cn.lwjppz.mindstorm.file.service.CourseCoverService;
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
 * @since : 2021-07-15
 */
@RestController
@RequestMapping("/file/course-cover")
@Api(tags = "课程封面Api")
public class CourseCoverController {

    private final CourseCoverService courseCoverService;

    public CourseCoverController(@Lazy CourseCoverService courseCoverService) {
        this.courseCoverService = courseCoverService;
    }

    @PostMapping("/upload")
    @ApiOperation("上传课程封面")
    public CommonResult upload(@ApiParam("课程封面") MultipartFile image) {
        var courseCover = courseCoverService.uploadCourseCover(image);
        return CommonResult.ok().data("courseCover", courseCover);
    }

}
