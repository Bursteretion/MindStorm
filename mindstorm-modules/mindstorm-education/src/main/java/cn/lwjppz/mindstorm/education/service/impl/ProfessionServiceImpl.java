package cn.lwjppz.mindstorm.education.service.impl;

import cn.lwjppz.mindstorm.common.core.enums.status.ProfessionStatus;
import cn.lwjppz.mindstorm.common.core.enums.status.ResultStatus;
import cn.lwjppz.mindstorm.common.core.exception.EntityNotFoundException;
import cn.lwjppz.mindstorm.common.core.utils.StringUtils;
import cn.lwjppz.mindstorm.common.mybatis.common.BaseInterface;
import cn.lwjppz.mindstorm.education.model.dto.academy.AcademyDTO;
import cn.lwjppz.mindstorm.education.model.dto.profession.ProfessionDTO;
import cn.lwjppz.mindstorm.education.model.entity.Academy;
import cn.lwjppz.mindstorm.education.model.entity.Profession;
import cn.lwjppz.mindstorm.education.mapper.ProfessionMapper;
import cn.lwjppz.mindstorm.education.model.vo.profession.ProfessionQueryVO;
import cn.lwjppz.mindstorm.education.model.vo.profession.ProfessionVO;
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
    public IPage<ProfessionDTO> pageProfessions(int pageNum, int pageSize) {
        return queryProfessions(new ProfessionQueryVO(pageNum, pageSize, null, null, null, null));
    }

    @Override
    public IPage<ProfessionDTO> queryProfessions(ProfessionQueryVO professionQueryVO) {
        IPage<Profession> iPage = new Page<>(professionQueryVO.getPageNum(), professionQueryVO.getPageSize());
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

        baseMapper.insert(profession);

        return profession;
    }

    @Override
    public Profession updateProfession(ProfessionVO professionVO) {
        var profession = new Profession();
        BeanUtils.copyProperties(professionVO, profession);

        baseMapper.updateById(profession);

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
            return baseMapper.deleteById(professionId) > 0;
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
        ProfessionDTO professionDTO = new ProfessionDTO();
        BeanUtils.copyProperties(profession, professionDTO);

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
