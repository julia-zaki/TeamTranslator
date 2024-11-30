package use_case.imageUpload;

import use_case.translateText.DataAccessException;

/**
 * The ImageUpload Interactor.
 */
public class ImageUploadInteractor implements ImageUploadInputBoundary {

    private final ImageUploadDataAccessInterface imageUploadDataAccess;
    private final ImageUploadOutputBoundary imageUploadOutputBoundary;

    public ImageUploadInteractor(ImageUploadDataAccessInterface imageUploadDataAccessObject,
                                 ImageUploadOutputBoundary imageUploadPresenter) {
        this.imageUploadDataAccess = imageUploadDataAccessObject;
        this.imageUploadOutputBoundary = imageUploadPresenter;
    }

    /**
     * Executes the Image Upload Use Case given ImageUploadInputData which contains imageFile and inputText.
     * InputText is passed to keep the text previously entered in the input text field.
     * Any text detected from the image will be concatenated with inputText on a new line.
     * The presenter will prepare a fail view if the given file does not exist, if no text
     * is detected in the image, or if a DataAccessException is thrown.
     *
     * @param imageUploadInputData the input data containing imageFile and inputText
     */
    @Override
    public void execute(ImageUploadInputData imageUploadInputData) {

        // Check if file exists in user's local directory
        if (!imageUploadInputData.getImageFile().exists()) {
            imageUploadOutputBoundary.prepareFailView("The file does not exist.",
                    imageUploadInputData.getInputText());
        }

        // Otherwise, use the DAO's getText method to retrieve the input image file's text
        else {
            try {
                final String imageText = imageUploadDataAccess.getText(imageUploadInputData.getImageFile());
                final ImageUploadOutputData outputData = new ImageUploadOutputData(
                        imageUploadInputData.getInputText(), imageText);

                // If no text was found in image, prepare fail view
                if ("".equals(imageText)) {
                    imageUploadOutputBoundary.prepareFailView("No text detected from image.",
                            imageUploadInputData.getInputText());
                }

                // Else, prepare success view
                else {
                    imageUploadOutputBoundary.prepareSuccessView(outputData);
                }
            }

            catch (DataAccessException ex) {
                imageUploadOutputBoundary.prepareFailView(ex.getMessage(), imageUploadInputData.getInputText());
            }
        }

    }
}
