package use_case.imageUpload;

/**
 * Output Data for the ImageUpload Use Case.
 */
public class ImageUploadOutputData {

    private final String inputText;
    private final String imageText;

    public ImageUploadOutputData(String inputText, String imageText) {
        this.inputText = inputText;
        this.imageText = imageText;
    }

    public String getInputText() {
        return inputText;
    }

    public String getImageText() {
        return imageText;
    }

}
