package fr.aygalinc.tv.server.show.model;

import com.google.api.client.util.Key;
import fr.aygalinc.tv.server.show.model.image.ImageInformation;

/**
 * Created by aygalinc on 19/01/18.
 */
public class ShowMainInformation {

    @Key("id")
    Long id;

    @Key("name")
    String name;

    @Key("image")
    ImageInformation imageInfo;

    @Key("summary")
    String description;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ImageInformation getImageInfo() {
        return imageInfo;
    }

    public void setImageInfo(ImageInformation imageInfo) {
        this.imageInfo = imageInfo;
    }

    public String getImageUrl() {
        return imageInfo.getImageURL();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
