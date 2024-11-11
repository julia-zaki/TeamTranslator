package use_case.translateText;

import entity.TextTranslator;
import interface_adapter.translateText.TranslateTextPresenter;

/**
 * The TranslateText Interactor.
 */
public class TranslateTextInteractor implements TranslateTextInputBoundary {

    private final TranslateTextDataAccessInterface dataAccessObject;
    private final TranslateTextPresenter presenter;
    private final TextTranslator textTranslator;

    public TranslateTextInteractor(TranslateTextDataAccessInterface translateTextDataAccessInterface,
                                   TranslateTextPresenter translateTextPresenter,
                                   TextTranslator textTranslator) {
        this.dataAccessObject = translateTextDataAccessInterface;
        this.presenter = translateTextPresenter;
        this.textTranslator = textTranslator;
    }

    @Override
    public void execute(String inputLanguage, String inputText, String outputLanguage) {

        try {
            if (!dataAccessObject.getInputLanguages().contains(textTranslator.getInputLanguage())) {
                presenter.prepareFailView("Selected language does not exist in translator.");
            }
            else if (!dataAccessObject.getOutputLanguages(textTranslator.getInputLanguage())
                    .contains(textTranslator.getOutputLanguage())) {
                presenter.prepareFailView("Translated language does not exist in translator.");
            }
            else {
                textTranslator.setInputLanguage(inputLanguage);
                textTranslator.setOutputLanguage(outputLanguage);
                textTranslator.setInputText(inputText);
                textTranslator.translate();
                presenter.prepareSuccessView(textTranslator.getOutputText(), textTranslator.getInputLanguage());
            }
        }
        catch (DataAccessException ex) {
            throw new RuntimeException(ex);
        }
    }
}
