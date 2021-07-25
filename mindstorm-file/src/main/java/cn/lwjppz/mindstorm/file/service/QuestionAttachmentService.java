package cn.lwjppz.mindstorm.file.service;

import cn.lwjppz.mindstorm.file.model.entity.QuestionAttachment;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p></p>
 *
 * @author : lwj
 * @since : 2021-07-16
 */
public interface QuestionAttachmentService extends IService<QuestionAttachment> {

    /**
     * 上传题目图片
     *
     * @param image 图片
     * @return 图片信息
     */
    QuestionAttachment uploadQuestionImage(MultipartFile image);

}
