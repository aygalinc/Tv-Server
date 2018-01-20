package fr.aygalinc.tv.server.show.model.image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by aygalinc on 20/01/18.
 */
public class DefaultImage  implements Image {

    private static final DefaultImage singleton = new DefaultImage();

    private DefaultImage(){

    }

   public static DefaultImage getDefaultImage(){
       return singleton ;
   }

    @Override
    public BufferedImage getBufferedImage() throws IOException {
        return ImageIO.read(DefaultImage.class.getResourceAsStream("/image/DefaultImage.jpg"));
    }

}
