package cn.lwjppz.mindstorm.education.service.impl;

import cn.lwjppz.mindstorm.common.core.enums.status.AcademyStatus;
import cn.lwjppz.mindstorm.common.core.enums.status.ResultStatus;
import cn.lwjppz.mindstorm.common.core.exception.EntityNotFoundException;
import cn.lwjppz.mindstorm.common.core.utils.StringUtils;
import cn.lwjppz.mindstorm.common.mybatis.common.BaseInterface;
import cn.lwjppz.mindstorm.education.model.dto.academy.AcademyDTO;
import cn.lwjppz.mindstorm.education.model.entity.Academy;
import cn.lwjppz.mindstorm.education.mapper.AcademyMapper;
import cn.lwjppz.mindstorm.education.model.vo.academy.AcademyQueryVO;
import cn.lwjppz.mindstorm.education.model.vo.academy.AcademyVO;
import cn.lwjppz.mindstorm.education.service.AcademyProfessionService;
import cn.lwjppz.mindstorm.education.service.AcademyService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 院系表 服务实现类
 * </p>
 *
 * @author lwj
 * @since 2021-06-07
 */
@Service
public class AcademyServiceImpl extends ServiceImpl<AcademyMapper, Academy> implements AcademyService,
        BaseInterface<Academy> {

    private final AcademyProfessionService academyProfessionService;

    public AcademyServiceImpl(AcademyProfessionService academyProfessionService) {
        this.academyProfessionService = academyProfessionService;
    }

    @Override
    public List<Academy> getAcademies() {
        var queryWrapper = getCommonQueryWrapper();
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public List<Academy> getUnDisableAcademies() {
        var queryWrapper = getCommonQueryWrapper();
        queryWrapper.ne(Academy::getStatus, AcademyStatus.FORBIDDEN.getValue());
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public IPage<AcademyDTO> pageAcademies(int pageNum, int pageSize) {
        return queryAcademies(new AcademyQueryVO(pageNum, pageSize, null, null, null, null));
    }

    @Override
    public IPage<AcademyDTO> queryAcademies(AcademyQueryVO academyQueryVO) {
        IPage<Academy> iPage = null;
        if (null != academyQueryVO.getPageIndex() && null != academyQueryVO.getPageSize()) {
            iPage = new Page<>(academyQueryVO.getPageIndex(), academyQueryVO.getPageSize());
        } else {
            iPage = new Page<>(1, 5);
        }
        var queryWrapper = getCommonQueryWrapper();

        if (StringUtils.isNotEmpty(academyQueryVO.getAcademyName())) {
            queryWrapper.like(Academy::getName, academyQueryVO.getAcademyName());
        }

        if (null != academyQueryVO.getStatus()) {
            queryWrapper.eq(Academy::getStatus, academyQueryVO.getStatus());
        }

        if (null != academyQueryVO.getStartTime() && null != academyQueryVO.getEndTime()) {
            queryWrapper.in(Academy::getGmtCreate, academyQueryVO.getStartTime(), academyQueryVO.getEndTime());
        }

        iPage = baseMapper.selectPage(iPage, queryWrapper);

        IPage<AcademyDTO> resPage = new Page<>();
        BeanUtils.copyProperties(iPage, resPage);

        resPage.setRecords(convertToAcademyDTO(iPage.getRecords()));

        return resPage;
    }

    @Override
    public Academy insertAcademy(AcademyVO academyVO) {
        Assert.notNull(academyVO, "AcademyVO must not be null.");

        var academy = new Academy();
        BeanUtils.copyProperties(academyVO, academy);

        baseMapper.insert(academy);

        return academy;
    }

    @Override
    public Academy updateAcademy(AcademyVO academyVO) {
        Assert.notNull(academyVO, "AcademyVO must not be null.");

        var academy = new Academy();
        BeanUtils.copyProperties(academyVO, academy);

        baseMapper.updateById(academy);

        return academy;
    }

    @Override
    public Academy infoAcademy(String academyId) {
        if (StringUtils.isNotEmpty(academyId)) {
            var academy = baseMapper.selectById(academyId);
            if (null == academy) {
                throw new EntityNotFoundException(ResultStatus.ENTITY_NOT_FOUND);
            }
            return academy;
        }
        return null;
    }

    @Override
    public boolean changeStatus(String academyId, Integer status) {
        if (StringUtils.isNotEmpty(academyId)) {
            Academy academy = baseMapper.selectById(academyId);
            if (null == academy) {
                throw new EntityNotFoundException(ResultStatus.ENTITY_NOT_FOUND);
            }
            academy.setStatus(status);

            return baseMapper.updateById(academy) > 0;
        }

        return true;
    }

    @Override
    public boolean deleteAcademy(String academyId) {
        if (StringUtils.isNotEmpty(academyId)) {
            // 删除院系专业关联信息
            var deleted = academyProfessionService.deleteAcademyProfessionByAcademyId(academyId);
            return baseMapper.deleteById(academyId) > 0 && deleted;
        }
        return false;
    }

    @Override
    public boolean batchDeleteAcademy(List<String> academyIds) {
        return academyIds.stream()
                .filter(StringUtils::isNotEmpty)
                .allMatch(this::deleteAcademy);
    }

    @Override
    public AcademyDTO convertToAcademyDTO(Academy academy) {
        var academyDTO = new AcademyDTO();
        BeanUtils.copyProperties(academy, academyDTO);

        return academyDTO;
    }

    @Override
    public List<AcademyDTO> convertToAcademyDTO(List<Academy> academies) {
        return academies.stream()
                .map(this::convertToAcademyDTO)
                .collect(Collectors.toList());
    }

    @Override
    public LambdaQueryWrapper<Academy> getCommonQueryWrapper() {
        LambdaQueryWrapper<Academy> wrapper = Wrappers.lambdaQuery();
        wrapper.orderByAsc(Academy::getSort);
        return wrapper;
    }
}
