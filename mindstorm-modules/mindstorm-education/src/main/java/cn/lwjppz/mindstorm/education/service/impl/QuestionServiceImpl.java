package cn.lwjppz.mindstorm.education.service.impl;

import cn.lwjppz.mindstorm.common.core.utils.StringUtils;
import cn.lwjppz.mindstorm.education.model.entity.Question;
import cn.lwjppz.mindstorm.education.mapper.QuestionMapper;
import cn.lwjppz.mindstorm.education.service.QuestionService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 题目表 服务实现类
 * </p>
 *
 * @author lwj
 * @since 2021-07-20
 */
@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question> implements QuestionService {

    @Override
    public Integer getCountByQuestionTypeId(String questionTypeId) {
        if (StringUtils.isNotEmpty(questionTypeId)) {
            LambdaQueryWrapper<Question> wrapper = Wrappers.lambdaQuery();
            wrapper.eq(Question::getQuestionTypeId, questionTypeId);
            return baseMapper.selectCount(wrapper);
        }
        return null;
    }
}
