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
     * Executes the Image Upload Use Case.
     *
     * @param imageUploadInputData the input data.
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
                final String inputText = imageUploadDataAccess.getText(imageUploadInputData.getImageFile());
                final ImageUploadOutputData outputData = new ImageUploadOutputData(inputText);

                // If no text was found in image, prepare fail view
                if ("".equals(inputText)) {
                    imageUploadOutputBoundary.prepareFailView("No text detected from image.",
                            imageUploadInputData.getInputText());
                }

                // Else, prepare success view
                else {
                    imageUploadOutputBoundary.prepareSuccessView(outputData, imageUploadInputData.getInputText());
                }
            }

            catch (DataAccessException ex) {
                imageUploadOutputBoundary.prepareFailView(ex.getMessage(), imageUploadInputData.getInputText());
            }
        }

    }
}
