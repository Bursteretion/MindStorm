package cn.lwjppz.mindstorm.education.controller;


import cn.lwjppz.mindstorm.common.core.support.CommonResult;
import cn.lwjppz.mindstorm.education.model.vo.course.CourseQueryVO;
import cn.lwjppz.mindstorm.education.model.vo.course.CourseVO;
import cn.lwjppz.mindstorm.education.service.CourseService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程管理API
 * </p>
 *
 * @author lwj
 * @since 2021-07-15
 */
@RestController
@RequestMapping("/education/course")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("/list")
    @ApiOperation("获取所有课程")
    public CommonResult listCourse() {
        var courses = courseService.listCourse();
        return CommonResult.ok().data("courses", courses);
    }

    @PostMapping("/query")
    @ApiOperation("条件查询课程")
    public CommonResult queryCourse(@ApiParam("查询条件") @RequestBody CourseQueryVO courseQueryVO) {
        var coursePage = courseService.queryCourse(courseQueryVO);
        return CommonResult.ok().data("coursePage", coursePage);
    }

    @PostMapping("/create")
    @ApiOperation("新增课程")
    public CommonResult createCourse(@ApiParam("课程信息") @RequestBody CourseVO courseVO) {
        var course = courseService.insertCourse(courseVO);
        return CommonResult.ok().data("course", course);
    }

    @PostMapping("/update")
    @ApiOperation("更新课程信息")
    public CommonResult updateCourse(@ApiParam("课程信息") @RequestBody CourseVO courseVO) {
        var success = courseService.updateCourse(courseVO);
        return CommonResult.ok().data("success", success);
    }

    @GetMapping("/info/{courseId}")
    @ApiOperation("查询课程信息")
    public CommonResult infoCourse(@ApiParam("课程Id") @PathVariable("courseId") String courseId) {
        var course = courseService.infoCourse(courseId);
        return CommonResult.ok().data("course", course);
    }

    @PutMapping("/change/{courseId}/{status}")
    @ApiOperation("更改课程状态")
    public CommonResult changeCourseStatus(@ApiParam("课程Id") @PathVariable("courseId") String courseId,
                                           @ApiParam("课程状态") @PathVariable("status") Integer status) {
        var success = courseService.changeCourseStatus(courseId, status);
        return CommonResult.ok().data("success", success);
    }

    @DeleteMapping("/delete/{courseId}")
    @ApiOperation("删除课程")
    public CommonResult deleteCourse(@ApiParam("课程Id") @PathVariable("courseId") String courseId) {
        var success = courseService.deleteCourse(courseId);
        return CommonResult.ok().data("success", success);
    }

}

