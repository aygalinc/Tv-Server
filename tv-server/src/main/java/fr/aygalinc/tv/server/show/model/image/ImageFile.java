package fr.aygalinc.tv.server.show.model.image;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageFile implements Image {

    private final File file;

    public ImageFile(File f){
        this.file = f;
    }

    public File getFile() {
        return file;
    }

    @Override
    public BufferedImage getBufferedImage() throws IOException {
        return ImageIO.read(file);
    }
}
