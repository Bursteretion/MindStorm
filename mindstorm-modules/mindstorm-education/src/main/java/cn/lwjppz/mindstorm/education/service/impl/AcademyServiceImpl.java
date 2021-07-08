package cn.lwjppz.mindstorm.education.service.impl;

import cn.lwjppz.mindstorm.common.core.enums.status.AcademyStatus;
import cn.lwjppz.mindstorm.common.core.enums.status.ResultStatus;
import cn.lwjppz.mindstorm.common.core.exception.EntityNotFoundException;
import cn.lwjppz.mindstorm.common.core.utils.StringUtils;
import cn.lwjppz.mindstorm.common.mybatis.common.BaseInterface;
import cn.lwjppz.mindstorm.education.model.dto.academy.AcademyDTO;
import cn.lwjppz.mindstorm.education.model.dto.academy.AcademyDetailDTO;
import cn.lwjppz.mindstorm.education.model.dto.academy.AcademySelectDTO;
import cn.lwjppz.mindstorm.education.model.dto.academy.AcademyTreeSelectDTO;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    public List<Academy> pageAcademies(int pageNum, int pageSize) {
        return queryAcademies(new AcademyQueryVO(null, null, null, null));
    }

    @Override
    public List<Academy> queryAcademies(AcademyQueryVO academyQueryVO) {
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

        return baseMapper.selectList(queryWrapper);
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
    public AcademySelectDTO convertToAcademySelectDTO(Academy academy) {
        AcademySelectDTO academySelectDTO = new AcademySelectDTO();
        academySelectDTO.setLabel(academy.getName());
        academySelectDTO.setValue(academy.getId());
        return academySelectDTO;
    }

    @Override
    public List<AcademySelectDTO> convertToAcademySelectDTO(List<Academy> academies) {
        return academies.stream()
                .filter(item -> !"0".equals(item.getPid()))
                .map(this::convertToAcademySelectDTO)
                .collect(Collectors.toList());
    }

    @Override
    public LambdaQueryWrapper<Academy> getCommonQueryWrapper() {
        LambdaQueryWrapper<Academy> wrapper = Wrappers.lambdaQuery();
        wrapper.orderByAsc(Academy::getSort);
        return wrapper;
    }

    @Override
    public List<AcademyDetailDTO> generateAcademyTree(List<Academy> academies) {
        Map<String, AcademyDetailDTO> hash = new HashMap<>(academies.size());
        List<AcademyDetailDTO> academyDetails = convertToAcademyDetailDTO(academies);
        academyDetails.forEach(item -> {
            hash.put(item.getId(), item);
            item.setChildren(new ArrayList<>());
        });

        List<AcademyDetailDTO> res = new ArrayList<>();
        academyDetails.forEach(item -> {
            if (StringUtils.isNotEmpty(item.getPid()) && hash.containsKey(item.getPid())) {
                AcademyDetailDTO academyDetailDTO = hash.get(item.getPid());
                List<AcademyDetailDTO> children = academyDetailDTO.getChildren();
                children.add(item);
                academyDetailDTO.setChildren(children);
            } else {
                res.add(item);
            }
        });

        return res;
    }

    private AcademyDetailDTO convertToAcademyDetailDTO(Academy academy) {
        AcademyDetailDTO academyDetailDTO = new AcademyDetailDTO();
        BeanUtils.copyProperties(academy, academyDetailDTO);

        return academyDetailDTO;
    }

    private List<AcademyDetailDTO> convertToAcademyDetailDTO(List<Academy> academies) {
        return academies.stream()
                .map(this::convertToAcademyDetailDTO)
                .collect(Collectors.toList());
    }
}
