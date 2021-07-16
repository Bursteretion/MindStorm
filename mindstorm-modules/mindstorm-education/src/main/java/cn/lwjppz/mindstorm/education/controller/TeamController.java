package cn.lwjppz.mindstorm.education.controller;

import cn.lwjppz.mindstorm.common.core.support.CommonResult;
import cn.lwjppz.mindstorm.education.model.vo.team.TeamQueryVO;
import cn.lwjppz.mindstorm.education.model.vo.team.TeamVO;
import cn.lwjppz.mindstorm.education.service.TeamService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 班级Api
 * </p>
 *
 * @author lwj
 * @since 2021-07-15
 */
@RestController
@RequestMapping("/education/team")
public class TeamController {

    private final TeamService teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @GetMapping("/list")
    @ApiOperation("获取所有班级")
    public CommonResult listAll() {
        var teams = teamService.listTeam();
        return CommonResult.ok().data("teams", teams);
    }

    @PostMapping("/query")
    @ApiOperation("条件查询班级信息")
    public CommonResult queryTeam(@ApiParam("查询条件") @RequestBody TeamQueryVO teamQueryVO) {
        var queryPage = teamService.queryTeam(teamQueryVO);
        return CommonResult.ok().data("queryPage", queryPage);
    }

    @PostMapping("/create")
    @ApiOperation("新增班级")
    public CommonResult createTeam(@ApiParam("班级信息") @RequestBody TeamVO teamVO) {
        var team = teamService.createTeam(teamVO);
        return CommonResult.ok().data("team", team);
    }

    @PostMapping("/update")
    @ApiOperation("更新班级信息")
    public CommonResult updateTeam(@ApiParam("班级信息") @RequestBody TeamVO teamVO) {
        var success = teamService.updateTeam(teamVO);
        return CommonResult.ok().data("success", success);
    }

    @GetMapping("/info/{teamId}")
    @ApiOperation("根据Id获取班级信息")
    public CommonResult infoTeam(@ApiParam("班级Id") @PathVariable("teamId") String teamId) {
        var team = teamService.convertToTeamDetailDTO(teamService.infoTeam(teamId));
        return CommonResult.ok().data("team", team);
    }

    @DeleteMapping("/delete/{teamId}")
    @ApiOperation("根据Id删除班级")
    public CommonResult deleteTeam(@ApiParam("班级Id") @PathVariable("teamId") String teamId) {
        var success = teamService.deleteTeam(teamId);
        return CommonResult.ok().data("success", success);
    }

    @PostMapping("/batchDelete")
    @ApiOperation("批量删除班级")
    public CommonResult batchDeleteTeam(@ApiParam("班级Id集合") @RequestBody List<String> teamIds) {
        var success = teamService.batchDeleteTeam(teamIds);
        return CommonResult.ok().data("success", success);
    }

}

