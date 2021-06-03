package cn.lwjppz.mindstorm.knowledge.service.impl;

import cn.lwjppz.mindstorm.knowledge.model.dto.subject.SubjectDTO;
import cn.lwjppz.mindstorm.knowledge.model.entity.Subject;
import cn.lwjppz.mindstorm.knowledge.mapper.SubjectMapper;
import cn.lwjppz.mindstorm.knowledge.service.SubjectService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  学科服务实现类
 * </p>
 *
 * @author lwj
 * @since 2021-05-27
 */
@Service
public class SubjectServiceImpl extends ServiceImpl<SubjectMapper, Subject> implements SubjectService {

    @Override
    public List<Subject> getSubjects() {
        return null;
    }

    @Override
    public List<Subject> getSubjects(Integer level) {
        return null;
    }

    @Override
    public IPage<SubjectDTO> pageBy(int pageIndex, int pageSize) {
        return null;
    }
}
