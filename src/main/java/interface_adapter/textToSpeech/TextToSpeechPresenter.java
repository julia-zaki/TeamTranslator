package interface_adapter.textToSpeech;

import interface_adapter.translateText.TranslateTextViewModel;
import use_case.textToSpeech.TextToSpeechOutputBoundary;

/**
 * Presenter for Text To Speech viewing and editing program.
 */
public class TextToSpeechPresenter implements TextToSpeechOutputBoundary {
    private final TranslateTextViewModel textToSpeechViewModel;

    public TextToSpeechPresenter(TranslateTextViewModel textToSpeechViewModel) {
        this.textToSpeechViewModel = textToSpeechViewModel;
    }

    /**
     * Prepares success view for Text To Speech related use cases.
     */
    public void prepareSuccessView() {
        textToSpeechViewModel.getState().setError(null);
        textToSpeechViewModel.firePropertyChanged();
    }

    /**
     * Prepares failure view for Text To Speech related use cases.
     * @param errorMessage explanation of the conversion failure.
     */
    public void prepareFailView(String errorMessage) {
        textToSpeechViewModel.getState().setError(errorMessage);
        textToSpeechViewModel.firePropertyChanged();
    }
}
