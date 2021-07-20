package cn.lwjppz.mindstorm.education.controller;

import cn.lwjppz.mindstorm.common.core.support.CommonResult;
import cn.lwjppz.mindstorm.common.core.utils.ServiceUtils;
import cn.lwjppz.mindstorm.education.model.entity.CourseClassStudent;
import cn.lwjppz.mindstorm.education.model.vo.courseclassstudent.CourseClassStudentBatchVO;
import cn.lwjppz.mindstorm.education.model.vo.courseclassstudent.CourseClassStudentQueryVO;
import cn.lwjppz.mindstorm.education.model.vo.courseclassstudent.CourseClassStudentVO;
import cn.lwjppz.mindstorm.education.service.CourseClassStudentService;
import cn.lwjppz.mindstorm.education.service.StudentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.stream.Collectors;

/**
 * <p>
 * 课程班级学生Api
 * </p>
 *
 * @author lwj
 * @since 2021-07-15
 */
@Api(tags = "课程班级学生Api")
@RestController
@RequestMapping("/education/course-class-student")
public class CourseClassStudentController {

    private final CourseClassStudentService courseClassStudentService;

    public CourseClassStudentController(CourseClassStudentService courseClassStudentService) {
        this.courseClassStudentService = courseClassStudentService;
    }

    @GetMapping("/list/{classId}")
    @ApiOperation("根据班级Id获取该班级所有学生")
    public CommonResult listCourseClassStudent(@ApiParam("班级Id") @PathVariable("classId") String classId) {
        var courseClassStudents =
                courseClassStudentService.convertCourseClassStudentDTO(courseClassStudentService.listCourseClassStudent(classId));
        return CommonResult.ok().data("courseClassStudents", courseClassStudents);
    }

    @PostMapping("/list/student")
    @ApiOperation("获取学生列表和当前已经加入此班级的学生Id集合")
    public CommonResult listStudentAndCheckedIds(@ApiParam("查询条件") @RequestBody CourseClassStudentQueryVO courseClassStudentQueryVO) {
        var students = courseClassStudentService.listNonClassStudents(courseClassStudentQueryVO);
        return CommonResult.ok().data("students", students);
    }

    @PostMapping("/query")
    @ApiOperation("条件查询班级学生")
    public CommonResult queryCourseClassStudent(@ApiParam("查询条件") @RequestBody CourseClassStudentQueryVO courseClassStudentQueryVO) {
        var queryCourseClassStudentPage = courseClassStudentService.queryCourseClassStudent(courseClassStudentQueryVO);
        return CommonResult.ok().data("queryCourseClassStudentPage", queryCourseClassStudentPage);
    }

    @PostMapping("/create")
    @ApiOperation("新增班级学生关联")
    public CommonResult createCourseClassStudent(@ApiParam("班级学生关联信息") @RequestBody CourseClassStudentVO courseClassStudentVO) {
        var success = courseClassStudentService.insertCourseClassStudent(courseClassStudentVO);
        return CommonResult.ok().data("success", success);
    }

    @PostMapping("/create/batch")
    @ApiOperation("批量新增班级学生关联")
    public CommonResult createBatchCourseClassStudent(@ApiParam("班级学生关联信息") @RequestBody CourseClassStudentBatchVO courseClassStudentBatchVO) {
        var success = courseClassStudentService.insertBatchCourseClassStudent(courseClassStudentBatchVO);
        return CommonResult.ok().data("success", success);
    }

    @DeleteMapping("/delete/{courseClassStudentId}")
    @ApiOperation("删除班级学生关联")
    public CommonResult deleteCourseClassStudent(@ApiParam("班级学生关联信息") @PathVariable("courseClassStudentId") String courseClassStudentId) {
        var success = courseClassStudentService.deleteCourseClassStudentById(courseClassStudentId);
        return CommonResult.ok().data("success", success);
    }

}

