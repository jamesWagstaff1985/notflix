package com.notflix.streaming.utils;

import com.mortennobel.imagescaling.ResampleFilters;
import com.mortennobel.imagescaling.ResampleOp;
import lombok.extern.slf4j.Slf4j;

import java.awt.image.BufferedImage;
import java.io.IOException;

@Slf4j
public class Images {


    private static final int DVD_WIDTH = 135;
    private static final int DVD_HEIGHT = 185;

    public static byte[] resizeImage(byte[] originalImage, String title) {
        try {
            BufferedImage bufferedImage = Encoding.toBufferedImage(originalImage);
            BufferedImage resizedBufferedImage = resizeImage(bufferedImage, DVD_WIDTH, DVD_HEIGHT);
            return Encoding.toByteArray(resizedBufferedImage, "jpg");
        } catch (IOException | NullPointerException e) {
            log.error("Error for {}: {}", title, e.getMessage());
            return null; // The image will now be empty
        }
    }

    public static BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) throws IOException {
        ResampleOp resizeOp = new ResampleOp(targetWidth, targetHeight);
        resizeOp.setFilter(ResampleFilters.getLanczos3Filter());
        return resizeOp.filter(originalImage, null);
    }

}
