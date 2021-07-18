package cn.lwjppz.mindstorm.education.controller;


import cn.lwjppz.mindstorm.common.core.support.CommonResult;
import cn.lwjppz.mindstorm.education.model.vo.courseclass.CourseClassVO;
import cn.lwjppz.mindstorm.education.service.CourseClassService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程班级表 前端控制器
 * </p>
 *
 * @author lwj
 * @since 2021-07-15
 */
@Api(tags = "课程班级Api")
@RestController
@RequestMapping("/education/course-class")
public class CourseClassController {

    private final CourseClassService courseClassService;

    public CourseClassController(CourseClassService courseClassService) {
        this.courseClassService = courseClassService;
    }

    @GetMapping("/list/{courseId}")
    @ApiOperation("根据课程Id获取该课程所有班级")
    public CommonResult listCourseClass(@ApiParam("课程Id") @PathVariable("courseId") String courseId) {
        var courseClasses = courseClassService.convertCourseClassDTO(courseClassService.listCourseClasses(courseId));
        return CommonResult.ok().data("courseClasses", courseClasses);
    }

    @GetMapping("/query")
    @ApiOperation("条件查询课程班级信息")
    public CommonResult queryCourseClass(@ApiParam("课程Id") @RequestParam("courseId") String courseId,
                                         @ApiParam("班级名称") @RequestParam(value = "className", required = false) String className) {
        var courseClasses = courseClassService.convertCourseClassDTO(courseClassService.queryCourseClasses(courseId,
                className));
        return CommonResult.ok().data("courseClasses", courseClasses);
    }

    @PostMapping("/create")
    @ApiOperation("新增班级")
    public CommonResult createCourseClass(@ApiParam("班级信息") @RequestBody CourseClassVO courseClassVO) {
        var courseClass = courseClassService.insertCourseClass(courseClassVO);
        return CommonResult.ok().data("courseClass", courseClass);
    }

    @PostMapping("/update")
    @ApiOperation("更新班级信息")
    public CommonResult updateCourseClass(@ApiParam("班级信息") @RequestBody CourseClassVO courseClassVO) {
        var success = courseClassService.updateCourseClass(courseClassVO);
        return CommonResult.ok().data("success", success);
    }

    @DeleteMapping("/delete/{classId}")
    @ApiOperation("根据班级Id删除班级")
    public CommonResult deleteCourseClass(@ApiParam("班级Id") @PathVariable("classId") String classId) {
        var success = courseClassService.deleteCourseClassById(classId);
        return CommonResult.ok().data("success", success);
    }

}

