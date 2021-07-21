package cn.lwjppz.mindstorm.file.handler;

import cn.lwjppz.mindstorm.common.core.enums.type.ImageSourceType;
import cn.lwjppz.mindstorm.common.core.enums.type.StorageType;
import cn.lwjppz.mindstorm.common.core.support.UploadResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import static cn.lwjppz.mindstorm.common.core.constant.Constants.FILE_SEPARATOR;


/**
 * File handler interface.
 *
 * @author : lwj
 * @since : 2020-08-18
 */
public interface FileHandler {

    /**
     * Image type
     */
    MediaType IMAGE_TYPE = MediaType.valueOf("image/*");

    /**
     * Check whether the attachment type provided is an image type
     *
     * @param mediaType a media type
     * @return true if it is an ImageType
     */
    static boolean isImageType(@Nullable String mediaType) {
        return mediaType != null && IMAGE_TYPE.includes(MediaType.valueOf(mediaType));
    }

    /**
     * Check whether the media type provided is an image type
     *
     * @param mediaType a media type
     * @return true if it is an ImageType
     */
    static boolean isImageType(@Nullable MediaType mediaType) {
        return mediaType != null && IMAGE_TYPE.includes(mediaType);
    }

    /**
     * The full name of the directory, be sure to end with a path separator
     *
     * @param dir full name must not be mull
     * @return full directory nameW wth ending path separator
     */
    @NonNull
    static String normalizeDirectory(@NonNull String dir) {
        Assert.hasText(dir, "the full name od the directory must not be a blank string.");

        return StringUtils.appendIfMissing(dir, FILE_SEPARATOR);
    }

    /**
     * Upload attachment
     *
     * @param file MultipartFile
     * @return UploadResult
     */
    @NonNull
    UploadResult upload(@NonNull MultipartFile file, ImageSourceType imageSourceType);

    /**
     * Delete attachment
     *
     * @param key attachment key
     */
    void delete(@NonNull String key);

    /**
     * Get attachment storage type
     *
     * @return current attachment storage type
     */
    StorageType getStorageType();

}
