package interface_adapter.switchTranslation;

import use_case.switchTranslation.SwitchTranslationInputBoundary;
import use_case.switchTranslation.SwitchTranslationInputData;

/**
 * The controller for  the SwitchTranslation Use Case.
 */
public class SwitchTranslationController {
    private final SwitchTranslationInputBoundary switchTranslarionUseCaseInteractor;

    public SwitchTranslationController(SwitchTranslationInputBoundary switchTranslarionUseCaseInteractor) {
        this.switchTranslarionUseCaseInteractor = switchTranslarionUseCaseInteractor;
    }

    /**
     * Executes the SwitchTranslation Use Case.
     * @param inputLanguage the language of the input text
     * @param inputText the input text for the translation
     * @param outputLanguage the language of the translation
     */
    public void execute(String inputText, String inputLanguage, String outputLanguage) {
        final SwitchTranslationInputData switchTranslationInputData = new SwitchTranslationInputData(
                inputText, inputLanguage, outputLanguage);

        switchTranslarionUseCaseInteractor.execute(switchTranslationInputData);
    }
}
