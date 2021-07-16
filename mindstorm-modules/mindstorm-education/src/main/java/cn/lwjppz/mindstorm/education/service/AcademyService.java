package cn.lwjppz.mindstorm.education.service;

import cn.lwjppz.mindstorm.education.model.dto.academy.AcademyDTO;
import cn.lwjppz.mindstorm.education.model.dto.academy.AcademyDetailDTO;
import cn.lwjppz.mindstorm.education.model.dto.academy.AcademySelectDTO;
import cn.lwjppz.mindstorm.education.model.entity.Academy;
import cn.lwjppz.mindstorm.education.model.vo.academy.AcademyQueryVO;
import cn.lwjppz.mindstorm.education.model.vo.academy.AcademyVO;
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
     * 查询院系信息
     *
     * @param pageNum  页码
     * @param pageSize 每页条数
     * @return 院系信息
     */
    List<Academy> pageAcademies(int pageNum, int pageSize);

    /**
     * 根据院系信息查询学院
     *
     * @param academyQueryVO 院系查询信息
     * @return 院系信息
     */
    List<Academy> queryAcademies(AcademyQueryVO academyQueryVO);

    /**
     * 获取该院系及下属院系Id集合
     *
     * @param academyId 院系Id
     * @return 院系id集合
     */
    List<String> getAcademyIdsById(String academyId);

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
     * 将院系信息转为树形结构
     *
     * @param academies 院系列表
     * @return 树形院系列表
     */
    List<AcademyDetailDTO> generateAcademyTree(List<Academy> academies);

    /**
     * 更改院系状态
     *
     * @param academyId 院系Id
     * @param status    院系状态
     * @return 是否更改成功
     */
    boolean changeStatus(String academyId, Integer status);

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

    /**
     * 将 Academy 对象转为 下拉选择类型对象
     *
     * @param academy Academy 对象
     * @return 拉选择类型对象
     */
    AcademySelectDTO convertToAcademySelectDTO(Academy academy);

    /**
     * 将 Academy 对象集合转为 下拉选择类型对象集合
     *
     * @param academies Academy 对象集合
     * @return 下拉选择类型对象集合
     */
    List<AcademySelectDTO> convertToAcademySelectDTO(List<Academy> academies);

}
