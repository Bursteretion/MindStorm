package cn.lwjppz.mindstorm.file.service.impl;

import cn.lwjppz.mindstorm.common.core.enums.type.ImageSourceType;
import cn.lwjppz.mindstorm.common.core.enums.type.StorageType;
import cn.lwjppz.mindstorm.common.core.support.UploadResult;
import cn.lwjppz.mindstorm.common.core.utils.ServiceUtils;
import cn.lwjppz.mindstorm.file.handler.FileHandlers;
import cn.lwjppz.mindstorm.file.mapper.CourseCoverMapper;
import cn.lwjppz.mindstorm.file.mapper.QuestionAttachmentMapper;
import cn.lwjppz.mindstorm.file.model.entity.CourseCover;
import cn.lwjppz.mindstorm.file.model.entity.QuestionAttachment;
import cn.lwjppz.mindstorm.file.service.CourseCoverService;
import cn.lwjppz.mindstorm.file.service.QuestionAttachmentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p></p>
 *
 * @author : lwj
 * @since : 2021-07-25
 */
@Slf4j
@Service
public class QuestionAttachmentServiceImpl extends ServiceImpl<QuestionAttachmentMapper, QuestionAttachment>
        implements QuestionAttachmentService {

    private final FileHandlers fileHandlers;

    public QuestionAttachmentServiceImpl(FileHandlers fileHandlers) {
        this.fileHandlers = fileHandlers;
    }

    @Override
    public QuestionAttachment uploadQuestionImage(MultipartFile image) {
        // TODO 从系统设置中获取获取存储位置
        StorageType storageType = StorageType.LOCAL;
        log.debug("开始上传附件... 附件存储位置: [{}], 文件: [{}]", storageType, image.getOriginalFilename());

        // 上传附件
        UploadResult uploadResult = fileHandlers.upload(image, storageType, ImageSourceType.QUESTION_IMAGE);

        log.debug("附件存储位置: [{}]", storageType);
        log.debug("上传结果: [{}]", uploadResult);

        QuestionAttachment questionAttachment = QuestionAttachment.builder()
                .name(uploadResult.getFilename())
                // 转换分隔符
                .path(ServiceUtils.changeFileSeparatorToUrlSeparator(uploadResult.getFilePath()))
                .fileKey(uploadResult.getKey())
                .thumbPath(uploadResult.getThumbPath())
                .mediaType(uploadResult.getMediaType().toString())
                .suffix(uploadResult.getSuffix())
                .width(uploadResult.getWidth())
                .height(uploadResult.getHeight())
                .size(uploadResult.getSize())
                .storageType(storageType.getValue())
                .build();

        log.debug("创建附件信息: [{}]", questionAttachment);

        // 新增并返回
        baseMapper.insert(questionAttachment);
        return questionAttachment;
    }
}
