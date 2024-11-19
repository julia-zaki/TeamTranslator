package use_case.imageUpload;

import java.io.File;

/**
 * The Input Data for the ImageUpload Use Case.
 */
public class ImageUploadInputData {

    private final File imageFile;

    public ImageUploadInputData(File imageFile) {
        this.imageFile = imageFile;
    }

    File getImageFile() {
        return imageFile;
    }
}
