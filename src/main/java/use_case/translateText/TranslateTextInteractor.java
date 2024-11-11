package use_case.translateText;

import entity.TextTranslator;
import interface_adapter.translateText.TranslateTextPresenter;

/**
 * The TranslateText Interactor.
 */
public class TranslateTextInteractor {

    private final TranslateTextDataAccessInterface userDataAccessObject;
    private final TranslateTextPresenter userPresenter;
    private final TextTranslator textTranslator;

    public TranslateTextInteractor(TranslateTextDataAccessInterface translateTextUserDataAccessInterface,
                                   TranslateTextPresenter translateTextPresenter,
                                   TextTranslator textTranslator) {
        this.userDataAccessObject = translateTextUserDataAccessInterface;
        this.userPresenter = translateTextPresenter;
        this.textTranslator = textTranslator;
    }

    @Override
    public void execute() {

        final String inputLanguage = textTranslator.getInputLanguage();
        final String outputLanguage = textTranslator.getOutputLanguage();
        final String translatedtext = textTranslator.getOutputText();

        try {
            if (!userDataAccessObject.getInputLanguages().contains(inputLanguage)) {
                userPresenter.prepareFailView("Selected language does not exist in translator.");
            }
            else if (!userDataAccessObject.getOutputLanguages(inputLanguage)
                    .contains(outputLanguage)) {
                userPresenter.prepareFailView("Translated language does not exist in translator.");
            }
            else {
                textTranslator.translate();
                userPresenter.prepareSuccessView(translatedtext, inputLanguage);
            }
        }
        catch (DataAccessException ex) {
            throw new RuntimeException(ex);
        }
    }
}
