package com.ikubinfo.primefaces.service.helpers;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileHelper {
    public static final String _PATH = "/home/gledisgjinaj/Documents/redi/git/grupi3/src/main/webapp/resources/photos";

    public static void createDir(String pathName) {
        Path path = Paths.get(pathName);
        try {
            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveImage(InputStream inputStream, File fileImage, String contentType) throws IOException {
        BufferedImage image = null;
        image = ImageIO.read(inputStream);
        String extention = contentType.substring(contentType.lastIndexOf("/") + 1);
        ImageIO.write(image, extention, fileImage);
    }
}
