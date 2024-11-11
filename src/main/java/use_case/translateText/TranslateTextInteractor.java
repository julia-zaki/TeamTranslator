package use_case.translateText;

import entity.TextTranslator;
import interface_adapter.translateText.TranslateTextOutputBoundary;

/**
 * The TranslateText Interactor.
 */
public class TranslateTextInteractor {

    private final TranslateTextDataAccessInterface dataAccessObject;
    private final TranslateTextOutputBoundary presenter;
    private final TextTranslator textTranslator;

    public TranslateTextInteractor(TranslateTextDataAccessInterface translateTextDataAccessInterface,
                                   TranslateTextOutputBoundary translateTextPresenter,
                                   TextTranslator textTranslator) {
        this.dataAccessObject = translateTextDataAccessInterface;
        this.presenter = translateTextPresenter;
        this.textTranslator = textTranslator;
    }

    @Override
    public void execute() {

        try {
            if (!dataAccessObject.getInputLanguages().contains(textTranslator.getInputLanguage())) {
                presenter.prepareFailView("Selected language does not exist in translator.");
            }
            else if (!dataAccessObject.getOutputLanguages(textTranslator.getInputLanguage())
                    .contains(textTranslator.getOutputLanguage())) {
                presenter.prepareFailView("Translated language does not exist in translator.");
            }
            else {
                textTranslator.translate();
                presenter.prepareSuccessView(textTranslator.getOutputText(), textTranslator.getInputLanguage());
            }
        }
        catch (DataAccessException ex) {
            throw new RuntimeException(ex);
        }
    }
}
