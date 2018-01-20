package fr.aygalinc.tv.server.show.model;

import fr.aygalinc.tv.server.show.model.image.ImageFile;

public class ShowItem {

    private final ImageFile image;

    private final ShowMainInformation showInformation;

    public ShowItem(ShowMainInformation showInformation, ImageFile image){
        this.showInformation = showInformation;
        this.image = image;
    }

    public Long getId() {
        return showInformation.getId();
    }

    public ShowMainInformation getExtraInformation() {
        return showInformation;
    }

    public ImageFile getImage() {
        return image;
    }
}
