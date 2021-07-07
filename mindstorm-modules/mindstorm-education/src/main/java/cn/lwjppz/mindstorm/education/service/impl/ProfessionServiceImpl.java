package cn.lwjppz.mindstorm.education.service.impl;

import cn.lwjppz.mindstorm.common.core.enums.status.ProfessionStatus;
import cn.lwjppz.mindstorm.common.core.enums.status.ResultStatus;
import cn.lwjppz.mindstorm.common.core.exception.EntityNotFoundException;
import cn.lwjppz.mindstorm.common.core.utils.ServiceUtils;
import cn.lwjppz.mindstorm.common.core.utils.StringUtils;
import cn.lwjppz.mindstorm.common.mybatis.common.BaseInterface;
import cn.lwjppz.mindstorm.education.model.dto.academy.AcademyDTO;
import cn.lwjppz.mindstorm.education.model.dto.profession.ProfessionDTO;
import cn.lwjppz.mindstorm.education.model.entity.Academy;
import cn.lwjppz.mindstorm.education.model.entity.AcademyProfession;
import cn.lwjppz.mindstorm.education.model.entity.Profession;
import cn.lwjppz.mindstorm.education.mapper.ProfessionMapper;
import cn.lwjppz.mindstorm.education.model.vo.profession.ProfessionQueryVO;
import cn.lwjppz.mindstorm.education.model.vo.profession.ProfessionVO;
import cn.lwjppz.mindstorm.education.service.AcademyProfessionService;
import cn.lwjppz.mindstorm.education.service.ProfessionService;
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
 * 开设专业表 服务实现类
 * </p>
 *
 * @author lwj
 * @since 2021-06-07
 */
@Service
public class ProfessionServiceImpl extends ServiceImpl<ProfessionMapper, Profession> implements ProfessionService,
        BaseInterface<Profession> {

    private final AcademyProfessionService academyProfessionService;

    public ProfessionServiceImpl(AcademyProfessionService academyProfessionService) {
        this.academyProfessionService = academyProfessionService;
    }

    @Override
    public List<Profession> getProfessions() {
        var queryWrapper = getCommonQueryWrapper();
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public List<Profession> getUnDisableProfessions() {
        var queryWrapper = getCommonQueryWrapper();
        queryWrapper.ne(Profession::getStatus, ProfessionStatus.FORBIDDEN.getValue());
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public List<Profession> getProfessionsByAcademyId(String academyId) {
        List<Profession> professions = null;
        if (StringUtils.isNotEmpty(academyId)) {
            // 根据院系Id获取该院所有院系专业关联信息
            var academyProfessions = academyProfessionService.getAcademyProfessionsByAcademyId(academyId);
            // 提取所有专业Id
            var professionIds = ServiceUtils.fetchProperty(academyProfessions, AcademyProfession::getProfessionId);
            // 根据Id集合查询所有专业信息
            professions = professionIds.stream()
                    .map(baseMapper::selectById)
                    .collect(Collectors.toList());
        }
        return professions;
    }

    @Override
    public IPage<ProfessionDTO> pageProfessions(int pageNum, int pageSize) {
        return queryProfessions(new ProfessionQueryVO(pageNum, pageSize, null, null, null, null));
    }

    @Override
    public IPage<ProfessionDTO> queryProfessions(ProfessionQueryVO professionQueryVO) {
        IPage<Profession> iPage;
        if (null != professionQueryVO.getPageIndex() && null != professionQueryVO.getPageSize()) {
            iPage = new Page<>(professionQueryVO.getPageIndex(), professionQueryVO.getPageSize());
        } else {
            iPage = new Page<>(1, 5);
        }
        var queryWrapper = getCommonQueryWrapper();

        if (StringUtils.isNotEmpty(professionQueryVO.getProfessionName())) {
            queryWrapper.like(Profession::getName, professionQueryVO.getProfessionName());
        }

        if (null != professionQueryVO.getStartTime() && null != professionQueryVO.getEndTime()) {
            queryWrapper.in(Profession::getGmtCreate, professionQueryVO.getStartTime(), professionQueryVO.getEndTime());
        }

        if (null != professionQueryVO.getStatus()) {
            queryWrapper.eq(Profession::getStatus, professionQueryVO.getStatus());
        }

        iPage = baseMapper.selectPage(iPage, queryWrapper);

        IPage<ProfessionDTO> resPage = new Page<>();
        BeanUtils.copyProperties(iPage, resPage);

        resPage.setRecords(convertToProfessionDTO(iPage.getRecords()));

        return resPage;
    }

    @Override
    public Profession insertProfession(ProfessionVO professionVO) {
        var profession = new Profession();
        BeanUtils.copyProperties(professionVO, profession);

        // 新增专业
        baseMapper.insert(profession);

        // 新增院系专业关联
        academyProfessionService.insertAcademyProfession(professionVO.getAcademyId(), profession.getId());

        return profession;
    }

    @Override
    public Profession updateProfession(ProfessionVO professionVO) {
        var profession = new Profession();
        BeanUtils.copyProperties(professionVO, profession);

        baseMapper.updateById(profession);

        // 更新关联信息
        academyProfessionService.updateAcademyProfession(professionVO.getAcademyId(), professionVO.getId());

        return profession;
    }

    @Override
    public Profession infoProfession(String professionId) {
        if (StringUtils.isNotEmpty(professionId)) {
            var profession = baseMapper.selectById(professionId);
            if (null == profession) {
                throw new EntityNotFoundException(ResultStatus.ENTITY_NOT_FOUND);
            }
            return profession;
        }
        return null;
    }

    @Override
    public boolean deleteProfession(String professionId) {
        if (StringUtils.isNotEmpty(professionId)) {
            // 删除院系专业关联信息
            var deleted = academyProfessionService.deleteAcademyProfessionByProfessionId(professionId);
            return baseMapper.deleteById(professionId) > 0 && deleted;
        }
        return false;
    }

    @Override
    public boolean batchDeleteProfession(List<String> professionIds) {
        return professionIds.stream()
                .filter(StringUtils::isNotEmpty)
                .allMatch(this::deleteProfession);
    }

    @Override
    public ProfessionDTO convertToProfessionDTO(Profession profession) {
        var professionDTO = new ProfessionDTO();
        BeanUtils.copyProperties(profession, professionDTO);

        professionDTO.setAcademyId(academyProfessionService.getAcademyProfessionsByProfessionId(profession.getId()).getAcademyId());
        return professionDTO;
    }

    @Override
    public List<ProfessionDTO> convertToProfessionDTO(List<Profession> professions) {
        return professions.stream()
                .map(this::convertToProfessionDTO)
                .collect(Collectors.toList());
    }

    @Override
    public LambdaQueryWrapper<Profession> getCommonQueryWrapper() {
        LambdaQueryWrapper<Profession> wrapper = Wrappers.lambdaQuery();
        wrapper.orderByAsc(Profession::getSort);
        return wrapper;
    }
}
