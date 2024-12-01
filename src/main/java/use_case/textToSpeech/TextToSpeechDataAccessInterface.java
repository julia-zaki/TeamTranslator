package use_case.textToSpeech;

import use_case.translateText.DataAccessException;

/**
 * Interface for the textToSpeechDAO. It consists of methods for
 * getting texts in the text field.
 */
public interface TextToSpeechDataAccessInterface {

    /**
     * Convert text to speech.
     * @param inputText text to be converted to speech.
     * @return the text of the field.
     * @throws DataAccessException if the text could not be translated for any reason.
     */
    String convertToSpeech(String inputText) throws DataAccessException;

}
