package cn.lwjppz.mindstorm.education.controller;


import cn.lwjppz.mindstorm.common.core.support.CommonResult;
import cn.lwjppz.mindstorm.education.model.vo.student.StudentQueryVO;
import cn.lwjppz.mindstorm.education.model.vo.student.StudentVO;
import cn.lwjppz.mindstorm.education.service.StudentService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 学生表 前端控制器
 * </p>
 *
 * @author lwj
 * @since 2021-07-13
 */
@RestController
@RequestMapping("/education/student")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/list")
    @ApiOperation("获取所有学生列表")
    public CommonResult listAll() {
        var students = studentService.listStudents();
        return CommonResult.ok().data("students", students);
    }

    @PostMapping("/query")
    @ApiOperation("条件查询学生列表")
    public CommonResult queryStudents(@ApiParam("查询信息") @RequestBody StudentQueryVO studentQueryVO) {
        var resPage = studentService.queryStudents(studentQueryVO);
        return CommonResult.ok().data("pageStudents", resPage);
    }

    @PostMapping("/create")
    @ApiOperation("新增学生")
    public CommonResult createStudent(@ApiParam("学生信息") @RequestBody StudentVO studentVO) {
        var student = studentService.createStudent(studentVO);
        return CommonResult.ok().data("student", student);
    }

    @PostMapping("/update")
    @ApiOperation("修改学生信息")
    public CommonResult updateStudent(@ApiParam("学生信息") @RequestBody StudentVO studentVO) {
        var success = studentService.updateStudent(studentVO);
        return CommonResult.ok().data("success", success);
    }

    @GetMapping("/info/{studentId}")
    @ApiOperation("获取学生信息")
    public CommonResult infoStudent(@ApiParam("学生Id") @PathVariable("studentId") String studentId) {
        var studentInfo = studentService.convertStudentDetailDTO(studentService.infoStudent(studentId));
        return CommonResult.ok().data("student", studentInfo);
    }

    @PutMapping("/change/{studentId}/{status}")
    @ApiOperation("更改学生状态")
    public CommonResult changeStudentStatus(@ApiParam("学生Id") @PathVariable("studentId") String studentId,
                                            @ApiParam("状态") @PathVariable("status") Integer status) {
        var success = studentService.changeStudentStatus(studentId, status);
        return CommonResult.ok().data("success", success);
    }

    @DeleteMapping("/delete/{studentId}")
    @ApiOperation("删除学生")
    public CommonResult deleteStudent(@ApiParam("学生Id") @PathVariable("studentId") String studentId) {
        var success = studentService.deleteStudent(studentId);
        return CommonResult.ok().data("success", success);
    }

    @PostMapping("/batchDelete")
    @ApiOperation("批量删除学生")
    public CommonResult batchDeleteStudent(@ApiParam("学生Id集合") @RequestBody List<String> studentIds) {
        var success = studentService.batchDeleteStudent(studentIds);
        return CommonResult.ok().data("success", success);
    }

}

