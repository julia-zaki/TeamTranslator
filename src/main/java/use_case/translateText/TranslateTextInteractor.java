package use_case.translateText;

import entity.TextTranslator;

/**
 * The TranslateText  Interactor.
 */
public class TranslateTextInteractor implements TranslateTextInputBoundary {

    private final TranslateTextDataAccessInterface dataAccessObject;
    private final use_case.translateText.TranslateTextOutputBoundary translateTextOutputBoundary;
    private final TextTranslator textTranslator;

    public TranslateTextInteractor(TranslateTextDataAccessInterface translateTextDataAccessInterface,
                                   use_case.translateText.TranslateTextOutputBoundary translateTextPresenter,
                                   TextTranslator textTranslator) {
        this.dataAccessObject = translateTextDataAccessInterface;
        this.translateTextOutputBoundary = translateTextPresenter;
        this.textTranslator = textTranslator;
    }

    @Override
    public void execute(TranslateTextInputData translateTextInputData) {

        try {
            textTranslator.setInputLanguage(translateTextInputData.getInputLanguage());
            textTranslator.setOutputLanguage(translateTextInputData.getOutputLanguage());
            textTranslator.setInputText(translateTextInputData.getInputText());

            if (!dataAccessObject.getInputLanguages().contains(textTranslator.getInputLanguage())) {
                translateTextOutputBoundary.prepareFailView("Selected language does not exist in translator.");
            }
            else if (!dataAccessObject.getOutputLanguages(textTranslator.getInputLanguage())
                    .contains(textTranslator.getOutputLanguage())) {
                translateTextOutputBoundary.prepareFailView("Translated language does not exist in translator.");
            }

            else if ("".equals(textTranslator.getInputText())) {
                translateTextOutputBoundary.prepareFailView("Input text is empty.");
            }

            else {

                textTranslator.translate();
                final TranslateTextOutputData translateTextOutputData = new TranslateTextOutputData(
                        textTranslator.getInputText(),
                        textTranslator.getOutputText(),
                        textTranslator.getInputLanguage(),
                        textTranslator.getOutputLanguage());
                translateTextOutputBoundary.prepareSuccessView(translateTextOutputData);
            }
        }
        catch (DataAccessException ex) {
            translateTextOutputBoundary.prepareFailView(ex.getMessage());
        }
    }
}
