package cn.lwjppz.mindstorm.permission.controller;

import cn.lwjppz.mindstorm.common.core.support.CommonResult;
import cn.lwjppz.mindstorm.permission.model.dto.LoginUserDTO;
import cn.lwjppz.mindstorm.permission.model.entity.User;
import cn.lwjppz.mindstorm.permission.model.vo.user.UserVO;
import cn.lwjppz.mindstorm.permission.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author lwj
 * @since 2021-05-09
 */
@Api(tags = "用户控制器")
@RestController
@RequestMapping("permission/user")
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation("测试方法")
    @GetMapping("/test")
    public CommonResult test() {
        return CommonResult.ok();
    }

    @ApiOperation("获取用户信息")
    @GetMapping("/info/{username}")
    public CommonResult info(@ApiParam("用户名") @PathVariable("username") String username) {
        LoginUserDTO loginUserDTO = userService.selectUserByUserName(username);
        return CommonResult.ok().data("user", loginUserDTO);
    }

    @ApiOperation("新增学生")
    @PostMapping("/create/student")
    public CommonResult createStudent(@ApiParam("学生信息") @RequestBody UserVO userVO) {
        User student = userService.insertStudent(userVO);
        return CommonResult.ok().data("student", student);
    }

    @ApiOperation("新增学生")
    @PostMapping("/create/teacher")
    public CommonResult createTeacher(@ApiParam("教师信息") @RequestBody UserVO userVO) {
        User teacher = userService.insertTeacher(userVO);
        return CommonResult.ok().data("teacher", teacher);
    }

    @ApiOperation("新增管理员")
    @PostMapping("/create/admin")
    public CommonResult createAdmin(@ApiParam("管理员信息") @RequestBody UserVO userVO) {
        User admin = userService.insertAdmin(userVO);
        return CommonResult.ok().data("admin", admin);
    }

    @ApiOperation("修改用户信息")
    @PostMapping("/update")
    public CommonResult update(@ApiParam("用户信息") @RequestBody UserVO userVO) {
        User user = userService.updateUser(userVO);
        return CommonResult.ok().data("user", user);
    }

    @ApiOperation("删除用户")
    @DeleteMapping("/delete/{userId}")
    public CommonResult delete(@ApiParam("用户Id") @PathVariable("userId") String userId) {
        userService.deleteUser(userId);
        return CommonResult.ok();
    }

}
