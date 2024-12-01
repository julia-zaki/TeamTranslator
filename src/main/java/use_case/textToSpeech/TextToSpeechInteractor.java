package use_case.textToSpeech;

import use_case.translateText.DataAccessException;

/**
 * The TextToSpeech Interactor.
 */
public class TextToSpeechInteractor implements TextToSpeechInputBoundary {

    private final TextToSpeechDataAccessInterface speechDataAccessInterface;
    private final TextToSpeechOutputBoundary textToSpeechOutputBoundary;

    public TextToSpeechInteractor(TextToSpeechDataAccessInterface textToSpeechDataAccessInterface,
                                  TextToSpeechOutputBoundary textToSpeechOutputBoundary) {
        this.speechDataAccessInterface = textToSpeechDataAccessInterface;
        this.textToSpeechOutputBoundary = textToSpeechOutputBoundary;
    }

    /**
     * Executes the TextToSpeech Use Case.
     * @param inputText the input data.
     */
    @Override
    public void execute(String inputText) {
        try {
            if ("".equals(inputText)) {
                textToSpeechOutputBoundary.prepareFailView("Text is empty.");
            }
            else {
                speechDataAccessInterface.convertToSpeech(inputText);

                textToSpeechOutputBoundary.prepareSuccessView();
            }
        }
        catch (DataAccessException ex) {
            textToSpeechOutputBoundary.prepareFailView("There seems to be an error with conversion"
                    + "Please try again.");
        }
    }
}
