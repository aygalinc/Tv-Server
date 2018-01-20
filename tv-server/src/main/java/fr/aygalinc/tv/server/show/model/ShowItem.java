package fr.aygalinc.tv.server.show.model;

import fr.aygalinc.tv.server.show.model.image.Image;

public class ShowItem {

    private final Image image;

    private ShowMainInformation showInformation;

    public ShowItem(ShowMainInformation showInformation, Image image){
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

    public Image getImage() {
        return image;
    }
}
