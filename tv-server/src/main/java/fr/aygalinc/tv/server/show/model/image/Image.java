package fr.aygalinc.tv.server.show.model.image;

import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Created by aygalinc on 20/01/18.
 */
public interface Image {

    BufferedImage getBufferedImage() throws IOException;

}
