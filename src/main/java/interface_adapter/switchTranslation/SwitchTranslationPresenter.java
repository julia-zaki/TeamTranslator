package interface_adapter.switchTranslation;

import interface_adapter.translateText.TranslateTextViewModel;
import use_case.switchTranslation.SwitchTranslationOutputBoundary;
import use_case.switchTranslation.SwitchTranslationOutputData;

/**
 * The presenter for the  SwitchTranslation Use Case.
 */

public class SwitchTranslationPresenter implements SwitchTranslationOutputBoundary {
    private final TranslateTextViewModel switchTranslationTextViewModel;

    public SwitchTranslationPresenter(TranslateTextViewModel switchTranslationTextViewModel) {
        this.switchTranslationTextViewModel = switchTranslationTextViewModel;
    }

    /**
     * Prepares the success  view for the SwitchTranslation related Use Cases.
     *
     * @param switchTranslationOutputData the output data
     */
    @Override
    public void prepareSuccessView(SwitchTranslationOutputData switchTranslationOutputData) {
        switchTranslationTextViewModel.getState().setOutputText(switchTranslationOutputData.getTranslatedText());
        switchTranslationTextViewModel.getState().setInputLanguage(switchTranslationOutputData.getInputLanguage());
        switchTranslationTextViewModel.getState().setInputText(switchTranslationOutputData.getInputText());
        switchTranslationTextViewModel.getState().setOutputLanguage(switchTranslationOutputData.getOutputLanguage());
        switchTranslationTextViewModel.getState().setError(null);
        switchTranslationTextViewModel.firePropertyChanged();

    }

    /**
     * Prepares the failure view for the SwitchTranslation related Use Cases.
     *
     * @param errorMessage the explanation of the failure
     */
    @Override
    public void prepareFailView(String errorMessage) {
        switchTranslationTextViewModel.getState().setError(errorMessage);
        switchTranslationTextViewModel.firePropertyChanged();
    }
}
