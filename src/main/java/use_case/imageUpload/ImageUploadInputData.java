package use_case.imageUpload;

import java.io.File;

/**
 * The Input Data for the ImageUpload Use Case.
 */
public class ImageUploadInputData {

    private final File imageFile;
    private final String inputText;

    public ImageUploadInputData(File imageFile, String inputText) {
        this.imageFile = imageFile;
        this.inputText = inputText;
    }

    File getImageFile() {
        return imageFile;
    }

    String getInputText() {
        return inputText;
    }
}
