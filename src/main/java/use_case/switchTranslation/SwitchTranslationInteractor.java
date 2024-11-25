package use_case.switchTranslation;

import java.util.Map;

import data_access.Constants;
import use_case.translateText.DataAccessException;

/**
 * The SwitchTranslation Interactor.
 */

public class SwitchTranslationInteractor implements SwitchTranslationInputBoundary {

    private final SwitchTranslationDataAccessInterface switchTranslationDataAccess;
    private final SwitchTranslationOutputBoundary switchTranslationOutputBoundary;
    private String translatedText;

    public SwitchTranslationInteractor(SwitchTranslationDataAccessInterface switchTranslationDataAccessObject,
                                       SwitchTranslationOutputBoundary switchTranslationPresenter) {
        this.switchTranslationDataAccess = switchTranslationDataAccessObject;
        this.switchTranslationOutputBoundary = switchTranslationPresenter;
    }

    /**
     * Executes the Switch Translation Use Case.
     *
     * @param switchTranslationInputData the input data.
     */

    @Override
    public void execute(SwitchTranslationInputData switchTranslationInputData) {
        try {
            if (switchTranslationInputData.getInputText() == null || switchTranslationInputData.getInputText().trim()
                    .isEmpty()) {
                switchTranslationOutputBoundary.prepareFailView("Input text cannot be empty. Please provide "
                        + "text to translate.");
            }
            else {
                // Proceed with the switching process
                final Map<String, String> switchedResult = switchTranslationDataAccess.switchLanguagesAndTexts(
                        switchTranslationInputData.getInputText(),
                        switchTranslationInputData.getInputLanguage(),
                        switchTranslationInputData.getOutputLanguage());

                // Get the switched translated text
                translatedText = switchedResult.get(Constants.TEXT_KEY);

                // Create the output data from the switched result
                final SwitchTranslationOutputData outputData = new SwitchTranslationOutputData(
                        translatedText,
                        switchedResult.get(Constants.LANGUAGE_KEY),
                        switchedResult.get("translatedText"),
                        switchedResult.get("outputLanguage"));

                // Prepare success view with output data
                switchTranslationOutputBoundary.prepareSuccessView(outputData);
            }
        }
        catch (DataAccessException ex) {
            // Handle errors in translation or switching
            switchTranslationOutputBoundary.prepareFailView("There seems to be an error with the switching process. "
                    + "Please try again.");
        }
    }
}
