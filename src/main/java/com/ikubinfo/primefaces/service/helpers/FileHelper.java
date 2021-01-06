package com.ikubinfo.primefaces.service.helpers;

import sun.util.resources.cldr.pa.CurrencyNames_pa;

import javax.faces.context.FacesContext;
import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class FileHelper {
    public static final String _PATH="/home/gledisgjinaj/hotel/";

    public static void saveImage(InputStream inputStream, File fileImage) throws IOException {
        BufferedImage image = null;
        image = ImageIO.read(inputStream);
        ImageIO.write(image, "png",fileImage);
    }
}
