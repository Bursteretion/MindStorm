package cn.lwjppz.mindstorm.education.service.impl;

import cn.lwjppz.mindstorm.common.core.utils.StringUtils;
import cn.lwjppz.mindstorm.education.model.entity.AcademyProfession;
import cn.lwjppz.mindstorm.education.mapper.AcademyProfessionMapper;
import cn.lwjppz.mindstorm.education.service.AcademyProfessionService;
import cn.lwjppz.mindstorm.education.service.AcademyService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.context.annotation.Lazy;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * 学院专业关联表 服务实现类
 * </p>
 *
 * @author lwj
 * @since 2021-06-07
 */
@Service
public class AcademyProfessionServiceImpl extends ServiceImpl<AcademyProfessionMapper, AcademyProfession> implements AcademyProfessionService {

    private final AcademyService academyService;

    public AcademyProfessionServiceImpl(@Lazy AcademyService academyService) {
        this.academyService = academyService;
    }

    @Override
    public List<AcademyProfession> getAcademyProfessionsByAcademyId(String academyId) {
        return baseMapper.selectList(getAcademyProfessionLambdaQueryWrapper(academyId));
    }

    @Override
    public IPage<AcademyProfession> getAcademyProfessionsByAcademyId(String academyId,
                                                                     @NonNull Integer pageIndex,
                                                                     @NonNull Integer pageSize) {
        LambdaQueryWrapper<AcademyProfession> queryWrapper = getAcademyProfessionLambdaQueryWrapper(academyId);
        IPage<AcademyProfession> page = new Page<>(pageIndex, pageSize);
        return baseMapper.selectPage(page, queryWrapper);
    }

    private LambdaQueryWrapper<AcademyProfession> getAcademyProfessionLambdaQueryWrapper(String academyId) {
        LambdaQueryWrapper<AcademyProfession> queryWrapper = Wrappers.lambdaQuery();
        if (StringUtils.isNotEmpty(academyId)) {
            Set<String> academyIdSet = new HashSet<>();
            academyIdSet.add(academyId);
            var academies = academyService.getAcademies();
            academies.stream()
                    .filter(item -> academyIdSet.contains(item.getPid()) || academyIdSet.contains(item.getId()))
                    .forEach(item -> academyIdSet.add(item.getId()));

            academies.forEach(item -> {
                if (academyIdSet.contains(item.getId()) || academyIdSet.contains(item.getPid())) {
                    academyIdSet.add(item.getId());
                }
            });
            queryWrapper.in(AcademyProfession::getAcademyId, academyIdSet);
        }
        return queryWrapper;
    }

    @Override
    public AcademyProfession getAcademyProfessionsByProfessionId(String professionId) {
        LambdaQueryWrapper<AcademyProfession> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(AcademyProfession::getProfessionId, professionId);

        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public AcademyProfession updateAcademyProfession(String academyId, String professionId) {
        LambdaQueryWrapper<AcademyProfession> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(AcademyProfession::getProfessionId, professionId);

        AcademyProfession academyProfession = baseMapper.selectOne(queryWrapper);
        academyProfession.setAcademyId(academyId);

        baseMapper.updateById(academyProfession);

        return academyProfession;
    }

    @Override
    public AcademyProfession insertAcademyProfession(String academyId, String professionId) {
        if (StringUtils.isNotEmpty(academyId) && StringUtils.isNotEmpty(professionId)) {
            var academyProfession = new AcademyProfession();
            academyProfession.setAcademyId(academyId);
            academyProfession.setProfessionId(professionId);
            baseMapper.insert(academyProfession);

            return academyProfession;
        }
        return null;
    }


    @Override
    public boolean deleteAcademyProfessionByAcademyId(String academyId) {
        LambdaQueryWrapper<AcademyProfession> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(AcademyProfession::getAcademyId, academyId);

        return baseMapper.delete(queryWrapper) > 0;
    }

    @Override
    public boolean deleteAcademyProfessionByProfessionId(String professionId) {
        LambdaQueryWrapper<AcademyProfession> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(AcademyProfession::getProfessionId, professionId);

        return baseMapper.delete(queryWrapper) > 0;
    }
}
