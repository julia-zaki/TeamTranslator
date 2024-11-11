package interface_adapter.TranslateText;

import use_case.note.TranslateTextInputBoundary;
import use_case.note.TranslateTextInputData;

/**
 * The controller for the TranslateText Use Case.
 */
public class TranslateTextController implements TranslateTextInputBoundary {

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