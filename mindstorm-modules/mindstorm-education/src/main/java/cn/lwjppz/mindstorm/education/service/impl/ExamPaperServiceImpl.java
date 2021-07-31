package cn.lwjppz.mindstorm.education.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.lwjppz.mindstorm.api.permission.feign.RemotePermissionFeignService;
import cn.lwjppz.mindstorm.api.permission.model.UserTo;
import cn.lwjppz.mindstorm.common.core.utils.ServiceUtils;
import cn.lwjppz.mindstorm.common.core.utils.StringUtils;
import cn.lwjppz.mindstorm.education.model.dto.exampaper.ExamPaperDTO;
import cn.lwjppz.mindstorm.education.model.entity.ExamPaper;
import cn.lwjppz.mindstorm.education.mapper.ExamPaperMapper;
import cn.lwjppz.mindstorm.education.model.vo.exampaper.ExamPaperQueryVO;
import cn.lwjppz.mindstorm.education.model.vo.exampaper.ExamPaperVO;
import cn.lwjppz.mindstorm.education.service.ExamPaperService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 试卷表 服务实现类
 * </p>
 *
 * @author lwj
 * @since 2021-07-31
 */
@Service
public class ExamPaperServiceImpl extends ServiceImpl<ExamPaperMapper, ExamPaper> implements ExamPaperService {

    private final RemotePermissionFeignService remotePermissionFeignService;

    public ExamPaperServiceImpl(@Lazy RemotePermissionFeignService remotePermissionFeignService) {
        this.remotePermissionFeignService = remotePermissionFeignService;
    }

    @Override
    public IPage<ExamPaperDTO> listExamPaper(ExamPaperQueryVO examPaperQueryVO) {
        LambdaQueryWrapper<ExamPaper> wrapper = Wrappers.lambdaQuery();
        if (StringUtils.isNotEmpty(examPaperQueryVO.getCourseId())) {
            wrapper.eq(ExamPaper::getCourseId, examPaperQueryVO.getCourseId());
        }
        if (StringUtils.isNotEmpty(examPaperQueryVO.getTitle())) {
            wrapper.like(ExamPaper::getTitle, examPaperQueryVO.getTitle());
        }

        IPage<ExamPaper> page = new Page<>(examPaperQueryVO.getPageIndex(), examPaperQueryVO.getPageSize());
        page = baseMapper.selectPage(page, wrapper);

        IPage<ExamPaperDTO> resPage = new Page<>();
        BeanUtils.copyProperties(page, resPage);
        resPage.setRecords(convertToExamPaperDTO(page.getRecords()));

        return resPage;
    }

    @Override
    public ExamPaper createExamPaper(ExamPaperVO examPaperVO) {
        var examPaper = new ExamPaper();
        BeanUtil.copyProperties(examPaperVO, examPaper);

        baseMapper.insert(examPaper);

        return examPaper;
    }

    @Override
    public boolean renameExamPaper(String examPaperId, String newName) {
        if (StringUtils.isNotEmpty(examPaperId) && StringUtils.isNotEmpty(newName)) {
            var examPaper = baseMapper.selectById(examPaperId);
            examPaper.setTitle(newName.strip());
            baseMapper.updateById(examPaper);
        }
        return true;
    }

    @Override
    public boolean updateExamPaper(ExamPaperVO examPaperVO) {
        var examPaper = new ExamPaper();
        BeanUtil.copyProperties(examPaperVO, examPaper);

        baseMapper.updateById(examPaper);
        return true;
    }

    @Override
    public ExamPaperDTO convertToExamPaperDTO(ExamPaper examPaper) {
        var examPagerDTO = new ExamPaperDTO();
        BeanUtil.copyProperties(examPaper, examPagerDTO);

        var res = remotePermissionFeignService.remoteUserInfoById(examPaper.getUserId());
        var userTo = ServiceUtils.feignValueConvert(res.getData().get("userTo"), UserTo.class);
        examPagerDTO.setUserRealName(userTo.getRealName());

        return examPagerDTO;
    }

    @Override
    public List<ExamPaperDTO> convertToExamPaperDTO(List<ExamPaper> examPapers) {
        return examPapers.stream()
                .map(this::convertToExamPaperDTO)
                .collect(Collectors.toList());
    }


}
