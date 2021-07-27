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

import java.util.ArrayList;
import java.util.List;

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
    public List<QuestionAnswerDTO> getQuestionAnswers(String questionId) {
        if (StringUtils.isNotEmpty(questionId)) {
            LambdaQueryWrapper<QuestionAnswer> wrapper = Wrappers.lambdaQuery();
            wrapper.eq(QuestionAnswer::getQuestionId, questionId);
            wrapper.orderByAsc(QuestionAnswer::getGmtCreate);
            var answers = baseMapper.selectList(wrapper);

            List<QuestionAnswerDTO> res = new ArrayList<>();
            answers.forEach(answer -> {
                var questionAnswerDTO = new QuestionAnswerDTO();
                BeanUtils.copyProperties(answer, questionAnswerDTO);

                if (StringUtils.isNotEmpty(answer.getOptionId())) {
                    var questionOption = questionOptionService.getById(answer.getOptionId());
                    questionAnswerDTO.setOptionId(questionOption.getId());
                    questionAnswerDTO.setOptionName(questionOption.getName());
                }
                res.add(questionAnswerDTO);
            });
            return res;
        }
        return null;
    }

    @Override
    public boolean createQuestionAnswer(QuestionAnswerVO questionAnswerVO) {
        var questionId = questionAnswerVO.getQuestionId();
        // 新增题目答案
        var questionAnswer = new QuestionAnswer();
        BeanUtils.copyProperties(questionAnswerVO, questionAnswer);
        questionAnswer.setQuestionId(questionId);
        baseMapper.insert(questionAnswer);

        return true;
    }

    @Override
    public boolean deleteQuestionAnswers(String questionId) {
        if (StringUtils.isNotEmpty(questionId)) {
            LambdaQueryWrapper<QuestionAnswer> wrapper = Wrappers.lambdaQuery();
            wrapper.eq(QuestionAnswer::getQuestionId, questionId);
            baseMapper.delete(wrapper);
        }
        return true;
    }

}
