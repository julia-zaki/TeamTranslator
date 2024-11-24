package interface_adapter.imageUpload;

import interface_adapter.translateText.TranslateTextViewModel;
import use_case.imageUpload.ImageUploadOutputBoundary;
import use_case.imageUpload.ImageUploadOutputData;

/**
 * The presenter for the Image Upload Use Case.
 */
public class ImageUploadPresenter implements ImageUploadOutputBoundary {

    // Uses the same view model as translate text
    private final TranslateTextViewModel imageUploadViewModel;

    public ImageUploadPresenter(TranslateTextViewModel imageUploadViewModel) {
        this.imageUploadViewModel = imageUploadViewModel;
    }

    /**
     * Prepares the success view for the ImageUpload related Use Cases.
     *
     * @param imageUploadOutputData the output data
     * @param inputText the input text prior to ImageUpload Use Case
     */
    @Override
    public void prepareSuccessView(ImageUploadOutputData imageUploadOutputData, String inputText) {
        // Set current input text to the previous input text concatenated with new text from image
        imageUploadViewModel.getState().setInputText(inputText + "\n" + imageUploadOutputData.getInputText());
        imageUploadViewModel.getState().setError(null);
        imageUploadViewModel.firePropertyChanged();

    }

    /**
     * Prepares the failure view for the Image Upload related Use Cases.
     *
     * @param errorMessage the explanation of the failure
     * @param inputText the input text prior to ImageUpload Use Case
     */
    @Override
    public void prepareFailView(String errorMessage, String inputText) {
        imageUploadViewModel.getState().setInputText(inputText);
        imageUploadViewModel.getState().setError(errorMessage);
        imageUploadViewModel.firePropertyChanged();
    }
}

