package interface_adapter.translateText;

import use_case.translateText.TranslateTextInputBoundary;
import use_case.translateText.TranslateTextInputData;

/**
 * The controller for the TranslateText Use Case.
 */
public class TranslateTextController {

    private final TranslateTextInputBoundary translateTextUseCaseInteractor;

    public TranslateTextController(TranslateTextInputBoundary translateTextUseCaseInteractor) {
        this.translateTextUseCaseInteractor = translateTextUseCaseInteractor;
    }

    /**
     * Executes the TranslateText Use Case.
     * @param inputLanguage the language of the input text
     * @param inputText the input text for the translation
     * @param outputLanguage the language of the translation
     */
    public void execute(String inputLanguage, String inputText, String outputLanguage) {
        final TranslateTextInputData translateTextInputData = new TranslateTextInputData(
                inputLanguage, inputText, outputLanguage);

        translateTextUseCaseInteractor.execute(translateTextInputData);
    }
}
