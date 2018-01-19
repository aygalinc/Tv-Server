package fr.aygalinc.tv.server.show.model.image;


import java.io.File;

public class ImageFile {

    private File file;

    public ImageFile(File f){
        this.file = f;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

}
