package cn.lwjppz.mindstorm.permission.controller;

import cn.lwjppz.mindstorm.api.permission.model.Loginuser;
import cn.lwjppz.mindstorm.common.core.enums.UserStatus;
import cn.lwjppz.mindstorm.common.core.enums.UserType;
import cn.lwjppz.mindstorm.common.core.support.CommonResult;
import cn.lwjppz.mindstorm.common.core.support.ValueEnum;
import cn.lwjppz.mindstorm.permission.model.dto.user.UserDTO;
import cn.lwjppz.mindstorm.permission.model.dto.user.UserDetailDTO;
import cn.lwjppz.mindstorm.permission.model.entity.User;
import cn.lwjppz.mindstorm.permission.model.vo.user.SearchUserVO;
import cn.lwjppz.mindstorm.permission.model.vo.user.UserVO;
import cn.lwjppz.mindstorm.permission.service.UserService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ApiOperation("测试方法")
    @GetMapping("/test")
    public CommonResult test() {
        return CommonResult.ok();
    }

    @ApiOperation("根据用户类型获取用户信息")
    @GetMapping("/list/{pageIndex}/{pageSize}/{userType}")
    public CommonResult pageBy(@ApiParam("第几页") @PathVariable("pageIndex") Integer pageIndex,
                               @ApiParam("每页条数") @PathVariable("pageSize") Integer pageSize,
                               @ApiParam("用户类型") @PathVariable("userType") Integer userType) {
        UserType value = ValueEnum.valueToEnum(UserType.class, userType);
        IPage<UserDTO> page = userService.pageByUsersByType(pageIndex, pageSize, value);
        return CommonResult.ok().data("page", page);
    }

    @ApiOperation("多条件查询用户信息")
    @PostMapping("/search/{pageIndex}/{pageSize}")
    public CommonResult pageBySearch(@ApiParam("第几页") @PathVariable("pageIndex") Integer pageIndex,
                                     @ApiParam("每页条数") @PathVariable("pageSize") Integer pageSize,
                                     @ApiParam("查询表单信息") @RequestBody SearchUserVO searchUserVO) {
        IPage<UserDTO> page = userService.pageBySearchUser(pageIndex, pageSize, searchUserVO);
        return CommonResult.ok().data("page", page);
    }

    @ApiOperation("获取用户信息")
    @GetMapping("/info/{username}")
    public CommonResult infoByUserName(@ApiParam("用户名") @PathVariable("username") String username) {
        Loginuser loginUserDTO = userService.selectUserByUserName(username);
        return CommonResult.ok().data("user", loginUserDTO);
    }

    @ApiOperation("根据用户Id获取用户信息")
    @GetMapping("/infoById/{userId}")
    public CommonResult infoByUserId(@ApiParam("用户Id") @PathVariable("userId") String userId) {
        UserDetailDTO user = userService.convertToUserDetailDTO(userService.selectUserByUserId(userId));
        return CommonResult.ok().data("user", user);
    }

    @ApiOperation("新增学生")
    @PostMapping("/create/student")
    public CommonResult createStudent(@ApiParam("学生信息") @RequestBody UserVO userVO) {
        User student = userService.insertStudent(userVO);
        return CommonResult.ok().data("student", student);
    }

    @ApiOperation("新增教师")
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
        boolean b = userService.deleteUser(userId);
        return CommonResult.ok().data("delete", b);
    }

    @ApiOperation("更改用户状态")
    @GetMapping("/change")
    public CommonResult change(@ApiParam("用户Id") @RequestParam("userId") String userId,
                               @ApiParam("用户状态") @RequestParam("status") Integer status) {
        UserStatus userStatus = ValueEnum.valueToEnum(UserStatus.class, status);
        boolean b = userService.changeUserStatus(userId, userStatus);
        return CommonResult.ok().data("change", b);
    }

}
