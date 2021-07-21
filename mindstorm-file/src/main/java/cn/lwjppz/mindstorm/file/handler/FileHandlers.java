package cn.lwjppz.mindstorm.file.handler;

import cn.lwjppz.mindstorm.common.core.enums.status.ResultStatus;
import cn.lwjppz.mindstorm.common.core.enums.type.ImageSourceType;
import cn.lwjppz.mindstorm.common.core.exception.FileOperationException;
import cn.lwjppz.mindstorm.common.core.exception.RepeatTypeException;
import cn.lwjppz.mindstorm.common.core.enums.type.StorageType;
import cn.lwjppz.mindstorm.common.core.support.UploadResult;
import cn.lwjppz.mindstorm.common.core.support.ValueEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

/**
 * File handler executed
 *
 * @author : lwj
 * @since : 2020-08-18
 */
@Slf4j
@Component
public class FileHandlers {

    /**
     * The file handlers container
     */
    private final ConcurrentHashMap<StorageType, FileHandler> fileHandlers = new ConcurrentHashMap<>(8);

    public FileHandlers(ApplicationContext applicationContext) {
        // add all file handlers
        addFileHandlers(applicationContext.getBeansOfType(FileHandler.class).values());
        log.info("{} file handlers has been registry.", fileHandlers.size());
    }

    /**
     * Executed upload file
     *
     * @param file        file must not be null
     * @param storageType current storage type
     * @return upload result
     * @throws FileOperationException When failed to upload attachment or no file handler available
     */
    @NonNull
    public UploadResult upload(@NonNull MultipartFile file, @NonNull StorageType storageType,
                               ImageSourceType imageSourceType) {
        return getSupportedType(storageType).upload(file, imageSourceType);
    }

    /**
     * 根据给定的FileKey删除文件
     *
     * @param fileKey     File key
     * @param storageType 存储位置类型
     */
    public void delete(String fileKey, Integer storageType) {
        getSupportedType(ValueEnum.valueToEnum(StorageType.class, storageType))
                .delete(fileKey);
    }

    /**
     * Add file handlers
     *
     * @param fileHandlers collections of file handlers
     */
    public void addFileHandlers(@Nullable Collection<FileHandler> fileHandlers) {
        if (!CollectionUtils.isEmpty(fileHandlers)) {
            for (FileHandler handler : fileHandlers) {
                if (this.fileHandlers.containsKey(handler.getStorageType())) {
                    // throws a RepeatTypeException when current file handler isn't unique
                    throw new RepeatTypeException(ResultStatus.ATTACHMENT_HANDLER_UNIQUE);
                }
                // to add
                this.fileHandlers.put(handler.getStorageType(), handler);
            }
        }
    }

    /**
     * Get file handler by storage type
     *
     * @param type current storage type
     * @return match file handler
     */
    private FileHandler getSupportedType(StorageType type) {
        FileHandler handler = fileHandlers.getOrDefault(type, fileHandlers.get(StorageType.LOCAL));
        if (handler == null) {
            throw new FileOperationException(ResultStatus.FILE_OPERATION);
        }
        return handler;
    }
}
