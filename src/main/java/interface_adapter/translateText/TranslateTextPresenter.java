package interface_adapter.translateText;

import use_case.translateText.TranslateTextOutputBoundary;
import use_case.translateText.TranslateTextOutputData;

/**
 * The presenter for the translation program.
 */
public class TranslateTextPresenter implements TranslateTextOutputBoundary {

    private final TranslateTextViewModel translateTextViewModel;

    public TranslateTextPresenter(TranslateTextViewModel translateTextViewModel) {
        this.translateTextViewModel = translateTextViewModel;
    }

    /**
     * Prepares the success  view for the Translate related Use Cases.
     *
     * @param translateTextOutputData the output data
     */
    @Override
    public void prepareSuccessView(TranslateTextOutputData translateTextOutputData) {
        translateTextViewModel.getState().setOutputText(translateTextOutputData.getOutputText());
        translateTextViewModel.getState().setInputLanguage(translateTextOutputData.getInputLanguage());
        translateTextViewModel.getState().setError(null);
        translateTextViewModel.firePropertyChanged();

    }

    /**
     * Prepares the failure view for the Translate related Use Cases.
     *
     * @param errorMessage the explanation of the failure
     */
    @Override
    public void prepareFailView(String errorMessage) {
        translateTextViewModel.getState().setError(errorMessage);
        translateTextViewModel.firePropertyChanged();
    }
}

