package fr.aygalinc.tv.server.show.model;

import fr.aygalinc.tv.server.show.model.image.ImageFile;

public class ShowItem {

    private final ImageFile image;

    private ShowMainInformation showInformation;

    public ShowItem(ShowMainInformation showInformation, ImageFile image){
        this.showInformation = showInformation;
        this.image = image;
    }

    public Long getId() {
        return showInformation.getId();
    }

    public ShowMainInformation getInformation() {
        return showInformation;
    }

    public void setInformation(ShowMainInformation showInformation) {
        this.showInformation = showInformation;
    }

    public ImageFile getImage() {
        return image;
    }
}
