package cn.lwjppz.mindstorm.user.controller;


import cn.lwjppz.mindstorm.common.core.support.CommonResult;
import cn.lwjppz.mindstorm.user.model.vo.teacher.TeacherQueryVO;
import cn.lwjppz.mindstorm.user.model.vo.teacher.TeacherVO;
import cn.lwjppz.mindstorm.user.service.TeacherService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 教师表 前端控制器
 * </p>
 *
 * @author lwj
 * @since 2021-07-13
 */
@RestController
@RequestMapping("/user/teacher")
public class TeacherController {

    private final TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @GetMapping("/list")
    @ApiOperation("获取所有教师列表")
    public CommonResult listAll() {
        var teachers = teacherService.listTeachers();
        return CommonResult.ok().data("teachers", teachers);
    }

    @PostMapping("/query")
    @ApiOperation("条件查询教师列表")
    public CommonResult queryTeachers(@ApiParam("查询信息") @RequestBody TeacherQueryVO teacherQueryVO) {
        var resPage = teacherService.queryTeachers(teacherQueryVO);
        return CommonResult.ok().data("pageTeachers", resPage);
    }

    @PostMapping("/create")
    @ApiOperation("新增教师")
    public CommonResult createTeacher(@ApiParam("教师信息") @RequestBody TeacherVO teacherVO) {
        var teacher = teacherService.createTeacher(teacherVO);
        return CommonResult.ok().data("teacher", teacher);
    }

    @PostMapping("/update")
    @ApiOperation("修改教师信息")
    public CommonResult updateTeacher(@ApiParam("教师信息") @RequestBody TeacherVO teacherVO) {
        var success = teacherService.updateTeacher(teacherVO);
        return CommonResult.ok().data("success", success);
    }

    @PutMapping("/info/{teacherId}")
    @ApiOperation("获取教师信息")
    public CommonResult infoTeacher(@ApiParam("教师Id") @PathVariable("teacherId") String teacherId) {
        var teacherInfo = teacherService.convertTeacherDetailDTO(teacherService.infoTeacher(teacherId));
        return CommonResult.ok().data("teacher", teacherInfo);
    }

    @PutMapping("/change/{teacherId}/{status}")
    @ApiOperation("更改教师状态")
    public CommonResult changeTeacherStatus(@ApiParam("教师Id") @PathVariable("teacherId") String teacherId,
                                            @ApiParam("状态") @PathVariable("status") Integer status) {
        var success = teacherService.changeTeacherStatus(teacherId, status);
        return CommonResult.ok().data("success", success);
    }

    @DeleteMapping("/delete/{teacherId}")
    @ApiOperation("删除教师")
    public CommonResult deleteTeacher(@ApiParam("教师Id") @PathVariable("teacherId") String teacherId) {
        var success = teacherService.deleteTeacher(teacherId);
        return CommonResult.ok().data("success", success);
    }

    @PostMapping("/batchDelete")
    @ApiOperation("批量删除教师")
    public CommonResult batchDeleteTeacher(@ApiParam("教师Id集合") @RequestBody List<String> teacherIds) {
        var success = teacherService.batchDeleteTeacher(teacherIds);
        return CommonResult.ok().data("success", success);
    }
    
}

