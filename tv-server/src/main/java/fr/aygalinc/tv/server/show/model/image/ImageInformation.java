package fr.aygalinc.tv.server.show.model.image;

import com.google.api.client.util.Key;

/**
 * Created by aygalinc on 19/01/18.
 */
public class ImageInformation {

    @Key("medium")
    String imageURL;

    public String getImageURL() {
        return imageURL;
    }

}
