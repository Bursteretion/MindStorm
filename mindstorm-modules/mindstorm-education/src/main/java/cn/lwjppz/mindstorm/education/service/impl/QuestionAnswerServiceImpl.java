package cn.lwjppz.mindstorm.education.service.impl;

import cn.lwjppz.mindstorm.common.core.utils.StringUtils;
import cn.lwjppz.mindstorm.education.model.dto.questionanswer.QuestionAnswerDTO;
import cn.lwjppz.mindstorm.education.model.entity.QuestionAnswer;
import cn.lwjppz.mindstorm.education.mapper.QuestionAnswerMapper;
import cn.lwjppz.mindstorm.education.model.vo.questionanswer.QuestionAnswerVO;
import cn.lwjppz.mindstorm.education.service.QuestionAnswerService;
import cn.lwjppz.mindstorm.education.service.QuestionOptionService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 题目答案表 服务实现类
 * </p>
 *
 * @author lwj
 * @since 2021-07-20
 */
@Service
public class QuestionAnswerServiceImpl extends ServiceImpl<QuestionAnswerMapper, QuestionAnswer> implements QuestionAnswerService {

    private final QuestionOptionService questionOptionService;

    public QuestionAnswerServiceImpl(QuestionOptionService questionOptionService) {
        this.questionOptionService = questionOptionService;
    }

    @Override
    public QuestionAnswerDTO getQuestionAnswer(String questionId) {
        if (StringUtils.isNotEmpty(questionId)) {
            LambdaQueryWrapper<QuestionAnswer> wrapper = Wrappers.lambdaQuery();
            wrapper.eq(QuestionAnswer::getQuestionId, questionId);
            var questionAnswer = baseMapper.selectOne(wrapper);

            var questionAnswerDTO = new QuestionAnswerDTO();
            BeanUtils.copyProperties(questionAnswer, questionAnswerDTO);

            var questionOption = questionOptionService.getById(questionAnswer.getOptionId());
            questionAnswerDTO.setOptionId(questionOption.getId());
            questionAnswerDTO.setOptionName(questionOption.getName());
            return questionAnswerDTO;
        }
        return null;
    }

    @Override
    public boolean createQuestionAnswer(QuestionAnswerVO questionAnswerVO) {
        // 先删除原来的题目答案
        var questionId = questionAnswerVO.getQuestionId();
        if (StringUtils.isNotEmpty(questionId)) {
            LambdaQueryWrapper<QuestionAnswer> wrapper = Wrappers.lambdaQuery();
            wrapper.eq(QuestionAnswer::getQuestionId, questionAnswerVO);
            baseMapper.delete(wrapper);
        }

        // 新增题目答案
        var questionAnswer = new QuestionAnswer();
        BeanUtils.copyProperties(questionAnswerVO, questionAnswer);
        questionAnswer.setQuestionId(questionId);
        baseMapper.insert(questionAnswer);

        return true;
    }

}
