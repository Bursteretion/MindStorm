package cn.lwjppz.mindstorm.education.service.impl;

import cn.lwjppz.mindstorm.common.core.enums.status.ResultStatus;
import cn.lwjppz.mindstorm.common.core.exception.AlreadyExistsException;
import cn.lwjppz.mindstorm.common.core.exception.EntityNotFoundException;
import cn.lwjppz.mindstorm.common.core.utils.StringUtils;
import cn.lwjppz.mindstorm.education.model.dto.team.TeamDTO;
import cn.lwjppz.mindstorm.education.model.dto.team.TeamDetailDTO;
import cn.lwjppz.mindstorm.education.model.entity.Team;
import cn.lwjppz.mindstorm.education.mapper.TeamMapper;
import cn.lwjppz.mindstorm.education.model.vo.team.TeamQueryVO;
import cn.lwjppz.mindstorm.education.model.vo.team.TeamVO;
import cn.lwjppz.mindstorm.education.service.TeamService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author lwj
 * @since 2021-07-15
 */
@Service
public class TeamServiceImpl extends ServiceImpl<TeamMapper, Team> implements TeamService {

    @Override
    public List<Team> listTeam() {
        return baseMapper.selectList(getCommonQueryWrapper());
    }

    @Override
    public IPage<TeamDTO> queryTeam(TeamQueryVO teamQueryVO) {
        LambdaQueryWrapper<Team> wrapper = getCommonQueryWrapper();
        if (StringUtils.isNotEmpty(teamQueryVO.getTeamName())) {
            wrapper.like(Team::getName, teamQueryVO.getTeamName());
        }
        if (null != teamQueryVO.getStatus()) {
            wrapper.eq(Team::getStatus, teamQueryVO.getStatus());
        }
        if (null != teamQueryVO.getStartTime() && null != teamQueryVO.getEndTime()) {
            wrapper.in(Team::getGmtCreate, teamQueryVO.getStartTime(), teamQueryVO.getEndTime());
        }
        IPage<Team> page = new Page<>(teamQueryVO.getPageIndex(), teamQueryVO.getPageSize());
        page = baseMapper.selectPage(page, wrapper);

        IPage<TeamDTO> resPage = new Page<>();
        BeanUtils.copyProperties(page, resPage);
        resPage.setRecords(convertToTeamDTO(page.getRecords()));
        return resPage;
    }

    @Override
    public Team createTeam(TeamVO teamVO) {
        if (StringUtils.isNotEmpty(teamVO.getName())) {
            LambdaQueryWrapper<Team> queryWrapper = Wrappers.lambdaQuery();
            queryWrapper.eq(Team::getName, teamVO.getName());
            var team = baseMapper.selectOne(queryWrapper);
            if (null != team) {
                throw new AlreadyExistsException(ResultStatus.ENTITY_EXIT);
            }
            var teamCreate = new Team();
            BeanUtils.copyProperties(teamVO, teamCreate);
            baseMapper.insert(teamCreate);

            return teamCreate;
        }
        return null;
    }

    @Override
    public boolean updateTeam(TeamVO teamVO) {
        var team = new Team();
        BeanUtils.copyProperties(teamVO, team);
        return baseMapper.updateById(team) > 0;
    }

    @Override
    public Team infoTeam(String teamId) {
        if (StringUtils.isNotEmpty(teamId)) {
            var team = baseMapper.selectById(teamId);
            if (null == team) {
                throw new EntityNotFoundException(ResultStatus.ENTITY_NOT_FOUND);
            }
            return team;
        }
        return null;
    }

    @Override
    public boolean deleteTeam(String teamId) {
        if (StringUtils.isNotEmpty(teamId)) {
            return baseMapper.deleteById(teamId) > 0;
        }
        return false;
    }

    @Override
    public boolean batchDeleteTeam(List<String> teamIds) {
        return teamIds.stream()
                .filter(StringUtils::isNotEmpty)
                .allMatch(this::deleteTeam);
    }

    @Override
    public boolean changeTeamStatus(String teamId, Integer status) {
        if (StringUtils.isNotEmpty(teamId)) {
            var team = baseMapper.selectById(teamId);
            team.setStatus(status);
            return baseMapper.updateById(team) > 0;
        }
        return false;
    }

    @Override
    public TeamDTO convertToTeamDTO(Team team) {
        var teamDTO = new TeamDTO();
        BeanUtils.copyProperties(team, teamDTO);
        return teamDTO;
    }

    @Override
    public List<TeamDTO> convertToTeamDTO(List<Team> teams) {
        return teams.stream()
                .map(this::convertToTeamDTO)
                .collect(Collectors.toList());
    }

    @Override
    public TeamDetailDTO convertToTeamDetailDTO(Team team) {
        var teamDetailDTO = new TeamDetailDTO();
        BeanUtils.copyProperties(team, teamDetailDTO);
        return teamDetailDTO;
    }

    private LambdaQueryWrapper<Team> getCommonQueryWrapper() {
        LambdaQueryWrapper<Team> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.orderByAsc(Team::getSort);
        return queryWrapper;
    }
}
