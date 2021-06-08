package cn.lwjppz.mindstorm.education.service;

import cn.lwjppz.mindstorm.education.model.dto.academy.AcademyDTO;
import cn.lwjppz.mindstorm.education.model.entity.Academy;
import cn.lwjppz.mindstorm.education.model.vo.academy.AcademyQueryVO;
import cn.lwjppz.mindstorm.education.model.vo.academy.AcademyVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 院系 服务类
 * </p>
 *
 * @author lwj
 * @since 2021-06-07
 */
public interface AcademyService extends IService<Academy> {

    /**
     * 获取所有院系信息
     *
     * @return 院系列表
     */
    List<Academy> getAcademies();

    /**
     * 获取所有未禁用的院系信息
     *
     * @return 未经用院系列表
     */
    List<Academy> getUnDisableAcademies();

    /**
     * 分页查询院系信息
     *
     * @param pageNum  页码
     * @param pageSize 每页条数
     * @return 分页信息
     */
    IPage<AcademyDTO> pageAcademies(int pageNum, int pageSize);

    /**
     * 根据院系信息查询学院
     *
     * @param academyQueryVO 院系查询信息
     * @return 分页信息
     */
    IPage<AcademyDTO> queryAcademies(AcademyQueryVO academyQueryVO);

    /**
     * 新增学院
     *
     * @param academyVO 学院信息
     * @return 学院信息
     */
    Academy insertAcademy(AcademyVO academyVO);

    /**
     * 更新学院信息
     *
     * @param academyVO 学院信息
     * @return 学院信息
     */
    Academy updateAcademy(AcademyVO academyVO);

    /**
     * 根据学院Id获取学院信息
     *
     * @param academyId 学院Id
     * @return 学院信息
     */
    Academy infoAcademy(String academyId);

    /**
     * 根据学院Id删除学院信息
     *
     * @param academyId 学院Id
     * @return 是否删除成功
     */
    boolean deleteAcademy(String academyId);

    /**
     * 批量删除学院信息
     *
     * @param academyIds 学院Id集合
     * @return 是否删除成功
     */
    boolean batchDeleteAcademy(List<String> academyIds);

    /**
     * 将 Academy 对象转为 AcademyDTO 对象
     *
     * @param academy Academy 对象
     * @return AcademyDTO 对象
     */
    AcademyDTO convertToAcademyDTO(Academy academy);

    /**
     * 将 Academy 对象集合转为 AcademyDTO 对象集合
     *
     * @param academies Academy 对象集合
     * @return AcademyDTO 对象集合
     */
    List<AcademyDTO> convertToAcademyDTO(List<Academy> academies);

}
