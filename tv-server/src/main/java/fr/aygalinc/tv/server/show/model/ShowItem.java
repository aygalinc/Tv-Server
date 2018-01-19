package fr.aygalinc.tv.server.show.model;

import fr.aygalinc.tv.server.show.model.image.ImageFile;

public class ShowItem {

    private final ImageFile image;

    private final ShowExtraInformation showInformation;

    public ShowItem(ShowExtraInformation mainInformation, ImageFile image){
        this.showInformation = mainInformation;
        this.image = image;
    }

    public Long getId() {
        return showInformation.getId();
    }

    public ShowExtraInformation getExtraInformation() {
        return showInformation;
    }

    public ImageFile getImage() {
        return image;
    }
}
