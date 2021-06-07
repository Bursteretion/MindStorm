package cn.lwjppz.mindstorm.education.service.impl;

import cn.lwjppz.mindstorm.common.core.enums.ResultStatus;
import cn.lwjppz.mindstorm.common.core.exception.EntityNotFoundException;
import cn.lwjppz.mindstorm.common.core.utils.StringUtils;
import cn.lwjppz.mindstorm.education.model.dto.AcademyDTO;
import cn.lwjppz.mindstorm.education.model.entity.Academy;
import cn.lwjppz.mindstorm.education.mapper.AcademyMapper;
import cn.lwjppz.mindstorm.education.model.vo.academy.AcademyVO;
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
public class AcademyServiceImpl extends ServiceImpl<AcademyMapper, Academy> implements AcademyService {

    @Override
    public List<Academy> getAcademies() {
        LambdaQueryWrapper<Academy> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.orderByAsc(Academy::getSort);
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public IPage<AcademyDTO> pageAcademies(int pageNum, int pageSize) {
        return queryAcademies(pageNum, pageSize, null);
    }

    @Override
    public IPage<AcademyDTO> queryAcademies(int pageNum, int pageSize, String academyName) {
        IPage<Academy> iPage = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Academy> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.orderByAsc(Academy::getSort);

        if (StringUtils.isNotEmpty(academyName)) {
            queryWrapper.like(Academy::getName, academyName);
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
                throw new EntityNotFoundException(ResultStatus.NOT_FOUND);
            }
            return academy;
        }
        return null;
    }

    @Override
    public boolean deleteAcademy(String academyId) {
        if (StringUtils.isNotEmpty(academyId)) {
            baseMapper.deleteById(academyId);
        }
        return true;
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
}
