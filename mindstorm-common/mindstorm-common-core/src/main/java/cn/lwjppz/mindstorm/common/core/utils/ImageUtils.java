package cn.lwjppz.mindstorm.common.core.utils;

import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

/**
 * @author : lwj
 * @since : 2020-08-18
 */
@Slf4j
public class ImageUtils {

    public static final String EXTENSION_ICO = "ico";

    public static BufferedImage getImageFromFile(InputStream is, String extension) throws IOException {
        log.debug("Current File type is : [{}]", extension);
        return ImageIO.read(is);
    }

    public static ImageReader getImageReaderFromFile(InputStream is, String formatName) {
        try {
            Iterator<ImageReader> readerIterator = ImageIO.getImageReadersByFormatName(formatName);
            ImageReader reader = readerIterator.next();
            ImageInputStream stream = ImageIO.createImageInputStream(is);
            ImageIO.getImageReadersByFormatName(formatName);
            reader.setInput(stream, true);
            return reader;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
