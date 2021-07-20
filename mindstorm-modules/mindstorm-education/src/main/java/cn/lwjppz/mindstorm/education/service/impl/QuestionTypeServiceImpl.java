package cn.lwjppz.mindstorm.education.service.impl;

import cn.lwjppz.mindstorm.education.model.entity.QuestionType;
import cn.lwjppz.mindstorm.education.mapper.QuestionTypeMapper;
import cn.lwjppz.mindstorm.education.service.QuestionTypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 题目类型（题型）表 服务实现类
 * </p>
 *
 * @author lwj
 * @since 2021-07-20
 */
@Service
public class QuestionTypeServiceImpl extends ServiceImpl<QuestionTypeMapper, QuestionType> implements QuestionTypeService {

}
