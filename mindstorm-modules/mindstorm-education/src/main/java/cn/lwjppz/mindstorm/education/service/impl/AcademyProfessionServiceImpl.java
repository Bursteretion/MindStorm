package cn.lwjppz.mindstorm.education.service.impl;

import cn.lwjppz.mindstorm.common.core.utils.StringUtils;
import cn.lwjppz.mindstorm.education.model.entity.AcademyProfession;
import cn.lwjppz.mindstorm.education.mapper.AcademyProfessionMapper;
import cn.lwjppz.mindstorm.education.service.AcademyProfessionService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public List<AcademyProfession> getAcademyProfessionsByAcademyId(String academyId) {
        LambdaQueryWrapper<AcademyProfession> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(AcademyProfession::getAcademyId, academyId);

        return baseMapper.selectList(queryWrapper);
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
