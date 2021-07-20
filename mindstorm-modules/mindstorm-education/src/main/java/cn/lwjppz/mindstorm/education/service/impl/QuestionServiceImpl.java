package cn.lwjppz.mindstorm.education.service.impl;

import cn.lwjppz.mindstorm.education.model.entity.Question;
import cn.lwjppz.mindstorm.education.mapper.QuestionMapper;
import cn.lwjppz.mindstorm.education.service.QuestionService;
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

}
