package interface_adapter.translateText;

import use_case.translateText.TranslateTextOutputBoundary;

/**
 * The presenter for the translation program.
 */
public class TranslateTextPresenter implements TranslateTextOutputBoundary {

    private final TranslateTextViewModel translateTextViewModel;

    public TranslateTextPresenter(TranslateTextViewModel translateTextViewModel) {
        this.translateTextViewModel = translateTextViewModel;
    }

    /**
     * Prepares the success view for the Translate related Use Cases.
     *
     * @param translatedText the output data
     */
    @Override
    public void prepareSuccessView(String translatedText, String inputLanguage) {
        translateTextViewModel.getState().setText(translatedText);
        translateTextViewModel.getState().setInputLanguage(inputLanguage);
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
