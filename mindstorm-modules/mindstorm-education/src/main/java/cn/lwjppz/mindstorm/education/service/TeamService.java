package cn.lwjppz.mindstorm.education.service;

import cn.lwjppz.mindstorm.education.model.dto.team.TeamDTO;
import cn.lwjppz.mindstorm.education.model.dto.team.TeamDetailDTO;
import cn.lwjppz.mindstorm.education.model.entity.Team;
import cn.lwjppz.mindstorm.education.model.vo.team.TeamQueryVO;
import cn.lwjppz.mindstorm.education.model.vo.team.TeamVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author lwj
 * @since 2021-07-15
 */
public interface TeamService extends IService<Team> {

    /**
     * 获取所有班级信息
     *
     * @return 班级列表
     */
    List<Team> listTeam();

    /**
     * 条件查询班级信息
     *
     * @param teamQueryVO 查询条件
     * @return 分页信息
     */
    IPage<TeamDTO> queryTeam(TeamQueryVO teamQueryVO);

    /**
     * 新增班级
     *
     * @param teamVO 班级信息
     * @return 班级信息
     */
    Team createTeam(TeamVO teamVO);

    /**
     * 更新班级信息
     *
     * @param teamVO 班级信息
     * @return 是否更新成功
     */
    boolean updateTeam(TeamVO teamVO);

    /**
     * 根据班级Id查询班级信息
     *
     * @param teamId 班级Id
     * @return 班级信息
     */
    Team infoTeam(String teamId);

    /**
     * 根据班级Id删除班级
     *
     * @param teamId 班级Id
     * @return 是否删除成功
     */
    boolean deleteTeam(String teamId);

    /**
     * 批量删除班级
     *
     * @param teamIds 班级Id集合
     * @return 是否删除成功
     */
    boolean batchDeleteTeam(List<String> teamIds);

    /**
     * 更改班级状态
     *
     * @param teamId 班级Id
     * @param status 状态
     * @return 是否更改成功
     */
    boolean changeTeamStatus(String teamId, Integer status);

    /**
     * 将 Team 对象转为 TeamDTO 对象
     *
     * @param team Team 对象
     * @return TeamDTO 对象
     */
    TeamDTO convertToTeamDTO(Team team);

    /**
     * 将 Team 集合转为 TeamDTO 集合
     *
     * @param teams Team 集合
     * @return TeamDTO 集合
     */
    List<TeamDTO> convertToTeamDTO(List<Team> teams);

    /**
     * 将 Team 对象转为 TeamDetailDTO 对象
     *
     * @param team Team 对象
     * @return TeamDTO 对象
     */
    TeamDetailDTO convertToTeamDetailDTO(Team team);

}
