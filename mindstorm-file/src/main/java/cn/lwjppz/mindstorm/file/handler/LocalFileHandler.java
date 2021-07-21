package cn.lwjppz.mindstorm.file.handler;

import cn.lwjppz.mindstorm.common.core.enums.status.ResultStatus;
import cn.lwjppz.mindstorm.common.core.enums.type.ImageSourceType;
import cn.lwjppz.mindstorm.common.core.exception.FileOperationException;
import cn.lwjppz.mindstorm.common.core.enums.type.StorageType;
import cn.lwjppz.mindstorm.common.core.support.UploadResult;
import cn.lwjppz.mindstorm.common.core.utils.FileNameUtils;
import cn.lwjppz.mindstorm.common.core.utils.ImageUtils;
import cn.lwjppz.mindstorm.common.core.utils.ServiceUtils;
import cn.lwjppz.mindstorm.file.support.FileProperties;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.locks.ReentrantLock;

import static cn.lwjppz.mindstorm.common.core.constant.Constants.FILE_SEPARATOR;

/**
 * Local file handler.
 *
 * @author : lwj
 * @since : 2020-08-18
 */
@Slf4j
@Component
public class LocalFileHandler implements FileHandler {

    /**
     * upload sub dir
     */
    private final static String UPLOAD_SUB_DIR = "upload/";

    /**
     * thumbnail suffix
     */
    private final static String THUMBNAIL_SUFFIX = "-thumbnail";

    /**
     * thumbnail width
     */
    private final static int THUMB_WIDTH = 256;

    /**
     * thumbnail height
     */
    private final static int THUMB_HEIGHT = 256;

    /**
     * work dir
     */
    private final String workDir;

    private final FileProperties fileProperties;

    private final ReentrantLock lock = new ReentrantLock();

    public LocalFileHandler(FileProperties fileProperties) {
        // get work dia
        workDir = FileHandler.normalizeDirectory(fileProperties.getWorkDir());

        this.fileProperties = fileProperties;

        initDirectory();
        // check work dir
        checkWorkDir();
    }

    /**
     * 初始化各目录
     */
    private void initDirectory() {
        Path workPath = Paths.get(fileProperties.getWorkDir());
        Path backupPath = Paths.get(fileProperties.getBackupDir());
        Path dataExportPath = Paths.get(fileProperties.getDataExportDir());
        Path uploadPath = Paths.get(fileProperties.getUploadDir());
        try {
            if (Files.notExists(workPath)) {
                Files.createDirectories(workPath);
                log.info("Created work directory: [{}]", workPath);
            }
            if (Files.notExists(backupPath)) {
                Files.createDirectories(backupPath);
                log.info("Created backup directory: [{}]", backupPath);
            }
            if (Files.notExists(dataExportPath)) {
                Files.createDirectories(dataExportPath);
                log.info("Created data export directory: [{}]", dataExportPath);
            }
            if (Files.notExists(uploadPath)) {
                Files.createDirectories(uploadPath);
                log.info("Created upload directory: [{}]", uploadPath);
            }
        } catch (IOException ie) {
            throw new RuntimeException("初始化目录失败", ie);
        }
    }

    /**
     * Check work dir
     */
    private void checkWorkDir() {
        // get work path
        Path workPath = Paths.get(workDir);

        // check file type
        Assert.isTrue(Files.isDirectory(workPath), workDir + " not be a directory.");

        // check dir readable
        Assert.isTrue(Files.isReadable(workPath), workDir + " not readable.");

        // check dir writable
        Assert.isTrue(Files.isWritable(workPath), workDir + " not writable.");
    }

    @NonNull
    @Override
    public UploadResult upload(@NonNull MultipartFile file, ImageSourceType imageSourceType) {
        Assert.notNull(file, "file must not be null.");

        // get current time
        Calendar current = Calendar.getInstance(Locale.SIMPLIFIED_CHINESE);

        // get year and month
        int year = current.get(Calendar.YEAR);
        int month = current.get(Calendar.MONTH) + 1;

        String monthString = month < 10 ? "0" + month : String.valueOf(month);

        String subDir =
                UPLOAD_SUB_DIR + imageSourceType.getName() + FILE_SEPARATOR + year + FILE_SEPARATOR + monthString + FILE_SEPARATOR;

        String originalBasename = FileNameUtils.getBasename(Objects.requireNonNull(file.getOriginalFilename()));

        String basename = originalBasename + '-' + ServiceUtils.randomUuidWithoutDash();

        String extension = FileNameUtils.getExtension(file.getOriginalFilename());

        log.debug("original name：[{}]，file name after processing: [{}], extension name: [{}]",
                file.getOriginalFilename(), basename, extension);

        String subFilePath = subDir + basename + '.' + extension;

        Path uploadPath = Paths.get(workDir, subFilePath);

        log.info("uploading file: [{}] to directory: [{}]", file.getOriginalFilename(), uploadPath.toString());

        try {
            // build directory
            Files.createDirectories(uploadPath.getParent());
            Files.createFile(uploadPath);

            // upload this file
            file.transferTo(uploadPath);

            String baseUrl = "http://localhost:8000/mindstorm-file";

            // build upload result
            UploadResult uploadResult = new UploadResult();
            uploadResult.setFilename(originalBasename);
            uploadResult.setFilePath(baseUrl + FILE_SEPARATOR + subFilePath);
            uploadResult.setKey(subFilePath);
            uploadResult.setSuffix(extension);
            uploadResult.setMediaType(MediaType.valueOf(Objects.requireNonNull(file.getContentType())));
            uploadResult.setSize(file.getSize());
            
            System.out.println(file);

            boolean isSvg = "svg".equals(extension);

            // check file type
            if (FileHandler.isImageType(uploadResult.getMediaType()) && !isSvg) {
                lock.lock();
                try (InputStream uploadFileInputStream = new FileInputStream(uploadPath.toFile())) {
                    // upload thumbnail
                    String thumbnailBasename = basename + THUMBNAIL_SUFFIX;
                    String thumbnailSubFilePath = subDir + thumbnailBasename + '.' + extension;
                    Path thumbnailPath = Paths.get(workDir + thumbnailSubFilePath);

                    // read image
                    BufferedImage originalImage = ImageUtils.getImageFromFile(uploadFileInputStream, extension);

                    // set width and height
                    uploadResult.setWidth(originalImage.getWidth());
                    uploadResult.setHeight(originalImage.getHeight());

                    boolean result = generateThumbnail(originalImage, thumbnailPath);
                    if (result) {
                        // set thumbnail path
                        uploadResult.setThumbPath(baseUrl + FILE_SEPARATOR + thumbnailSubFilePath);
                    } else {
                        // if upload failed
                        uploadResult.setThumbPath(baseUrl + subFilePath);
                    }
                } finally {
                    lock.unlock();
                }
            } else {
                uploadResult.setThumbPath(baseUrl + FILE_SEPARATOR + subFilePath);
            }

            log.info("upload attachment: [{}] to directory: [{}] successfully!", file.getOriginalFilename(),
                    uploadPath.toString());
            return uploadResult;
        } catch (IOException e) {
            throw new FileOperationException(ResultStatus.UPLOAD_FILE_ERROR);
        }
    }

    @Override
    public void delete(@NonNull String key) {
        Assert.hasText(key, "attachment key must not be null.");

        // get path
        Path path = Paths.get(workDir, key);

        // delete file
        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            throw new FileOperationException("attachment: " + key + " delete failed.");
        }

        // if necessary delete thumbnails
        String basename = FileNameUtils.getBasename(key);
        String extension = FileNameUtils.getExtension(key);

        // get thumbnail name
        String thumbnailName = basename + THUMBNAIL_SUFFIX + '.' + extension;

        // get thumbnail path
        Path thumbnailPath = Paths.get(path.getParent().toString(), thumbnailName);

        // delete thumbnail
        try {
            boolean deleteResult = Files.deleteIfExists(thumbnailPath);
            if (!deleteResult) {
                log.warn("thumbnail: [{}] may not exist", thumbnailPath.toString());
            }
        } catch (IOException e) {
            throw new FileOperationException("thumbnail: " + thumbnailName + " delete failed.");
        }
    }

    @Override
    public StorageType getStorageType() {
        return StorageType.LOCAL;
    }

    private boolean generateThumbnail(BufferedImage originalImage, Path thumbPath) {
        Assert.notNull(originalImage, "image must not be null.");
        Assert.notNull(thumbPath, "thumbnail path must not be null.");

        boolean result = false;

        // upload thumbnail
        try {
            Files.createFile(thumbPath);

            // convert to thumbnail and upload
            log.debug("try to generate thumbnail: [{}]", thumbPath.toString());

            Thumbnails.of(originalImage).size(THUMB_WIDTH, THUMB_HEIGHT).keepAspectRatio(true).toFile(thumbPath.toFile());

            log.debug("generate thumbnail and has been written to [{}]", thumbPath.toString());
            result = true;
        } catch (Throwable t) {
            log.warn("failed to generate thumbnail: " + thumbPath, t);
        } finally {
            // Dispose of this graphics context and release all system resources it is using.
            originalImage.getGraphics().dispose();
        }
        return result;
    }
}
