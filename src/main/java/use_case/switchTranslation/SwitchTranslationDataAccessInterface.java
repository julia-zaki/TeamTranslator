package use_case.switchTranslation;

import use_case.translateText.DataAccessException;

import java.util.Map;

/**
 * Interface for the Image Upload DAO. It consists of methods for
 * extracting text from an image file.
 */
public interface SwitchTranslationDataAccessInterface {

    /**
     * Extract the text from an image file.
     * @param inputText the file of the image
     * @param inputLanguage the file of the image
     * @param outputLanguage the file of the image
     * @return a mapping with the keys as the output text/language and values as input text/language.
     * @throws DataAccessException if the text could not be extracted for any reason
     */
    Map<String, String> switchLanguagesAndTexts(String inputText, String inputLanguage, String outputLanguage)
            throws DataAccessException;

}
