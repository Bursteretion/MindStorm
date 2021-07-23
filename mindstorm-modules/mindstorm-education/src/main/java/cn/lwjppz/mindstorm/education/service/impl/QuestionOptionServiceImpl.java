package cn.lwjppz.mindstorm.education.service.impl;

import cn.lwjppz.mindstorm.common.core.utils.StringUtils;
import cn.lwjppz.mindstorm.education.model.dto.questionoption.QuestionOptionDTO;
import cn.lwjppz.mindstorm.education.model.entity.QuestionOption;
import cn.lwjppz.mindstorm.education.mapper.QuestionOptionMapper;
import cn.lwjppz.mindstorm.education.model.vo.questionoption.QuestionOptionVO;
import cn.lwjppz.mindstorm.education.service.QuestionOptionService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 题目选项表 服务实现类
 * </p>
 *
 * @author lwj
 * @since 2021-07-20
 */
@Service
public class QuestionOptionServiceImpl extends ServiceImpl<QuestionOptionMapper, QuestionOption> implements QuestionOptionService {

    @Override
    public List<QuestionOptionDTO> getQuestionOptions(String questionId) {
        if (StringUtils.isNotEmpty(questionId)) {
            LambdaQueryWrapper<QuestionOption> wrapper = Wrappers.lambdaQuery();
            wrapper.eq(QuestionOption::getQuestionId, questionId);
            var questionOptions = baseMapper.selectList(wrapper);
            return questionOptions.stream()
                    .map(item -> new QuestionOptionDTO(item.getName(), item.getValue()))
                    .collect(Collectors.toList());
        }
        return null;
    }

    @Override
    public List<String> createQuestionOptions(String questionId, List<QuestionOptionVO> questionOptions) {
        // 先删除原来的题目选项
        if (StringUtils.isNotEmpty(questionId)) {
            LambdaQueryWrapper<QuestionOption> wrapper = Wrappers.lambdaQuery();
            wrapper.eq(QuestionOption::getQuestionId, questionId);
            baseMapper.delete(wrapper);
        }

        List<String> optionIds = new ArrayList<>();
        // 新增题目选项
        questionOptions.forEach(item -> {
            var questionOption = new QuestionOption();
            questionOption.setQuestionId(questionId);
            questionOption.setName(item.getOptionName());
            questionOption.setValue(item.getOptionValue());
            baseMapper.insert(questionOption);
            optionIds.add(questionOption.getId());
        });

        return optionIds;
    }
}
