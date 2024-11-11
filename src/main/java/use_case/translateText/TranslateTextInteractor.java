package use_case.translateText;

import entity.TextTranslator;
import interface_adapter.translateText.TranslateTextOutputBoundary;

/**
 * The TranslateText Interactor.
 */
public class TranslateTextInteractor implements TranslateTextInputBoundary {

    private final TranslateTextDataAccessInterface dataAccessObject;
    private final TranslateTextOutputBoundary translateTextOutputBoundary;
    private final TextTranslator textTranslator;

    public TranslateTextInteractor(TranslateTextDataAccessInterface translateTextDataAccessInterface,
                                   TranslateTextOutputBoundary translateTextPresenter,
                                   TextTranslator textTranslator) {
        this.dataAccessObject = translateTextDataAccessInterface;
        this.translateTextOutputBoundary = translateTextPresenter;
        this.textTranslator = textTranslator;
    }

    @Override
    public void execute(TranslateTextInputData translateTextInputData) {

        try {
            if (!dataAccessObject.getInputLanguages().contains(textTranslator.getInputLanguage())) {
                translateTextOutputBoundary.prepareFailView("Selected language does not exist in translator.");
            }
            else if (!dataAccessObject.getOutputLanguages(textTranslator.getInputLanguage())
                    .contains(textTranslator.getOutputLanguage())) {
                translateTextOutputBoundary.prepareFailView("Translated language does not exist in translator.");
            }
            else {
                textTranslator.setInputLanguage(translateTextInputData.getInputLanguage());
                textTranslator.setOutputLanguage(translateTextInputData.getOutputLanguage());
                textTranslator.setInputText(translateTextInputData.getInputText());
                textTranslator.translate();
                translateTextOutputBoundary.prepareSuccessView(textTranslator.getOutputText(),
                        textTranslator.getInputLanguage());
            }
        }
        catch (DataAccessException ex) {
            throw new RuntimeException(ex);
        }
    }
}
