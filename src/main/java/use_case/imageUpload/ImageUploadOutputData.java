package use_case.imageUpload;

/**
 * Output Data for the ImageUpload Use Case.
 */
public class ImageUploadOutputData {

    private final String inputText;

    public ImageUploadOutputData(String inputText) {
        this.inputText = inputText;
    }

    public String getInputText() {
        return inputText;
    }

}
