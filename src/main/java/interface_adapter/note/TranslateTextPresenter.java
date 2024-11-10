package interface_adapter.note;

import use_case.note.TranslateTextOutputBoundary;

/**
 * The presenter for our translation program.
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
    public void prepareSuccessView(String translatedText) {
        translateTextViewModel.getState().setText(translatedText);
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
