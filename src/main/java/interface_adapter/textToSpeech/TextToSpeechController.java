package interface_adapter.textToSpeech;

import use_case.textToSpeech.TextToSpeechInputBoundary;

/**
 * Controller for Text to Speech use case.
 */
public class TextToSpeechController {
    private final TextToSpeechInputBoundary textToSpeechUseCaseInteractor;

    public TextToSpeechController(TextToSpeechInputBoundary textToSpeechUseCaseInteractor) {
        this.textToSpeechUseCaseInteractor = textToSpeechUseCaseInteractor;
    }

    /**
     * Executes the Text To Speech use case.
     * @param inputText the text used for text to speech conversion.
     */
    public void execute(String inputText) {
        textToSpeechUseCaseInteractor.execute(inputText);
    }
}