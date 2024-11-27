package use_case.switchTranslation;

import use_case.translateText.DataAccessException;

import java.util.Map;

/**
 * Interface for the Switch Translation DAO. It consists of methods for
 * switching the texts and languages.
 */
public interface SwitchTranslationDataAccessInterface {

    /**
     * Switch the languages and texts.
     * @param inputText the input text
     * @param inputLanguage the input language
     * @param outputLanguage the output language
     * @return a mapping with the keys as the output text/language and values as input text/language.
     * @throws DataAccessException if the texts and languages could not be switched for any reason
     */
    Map<String, String> switchLanguagesAndTexts(String inputText, String inputLanguage, String outputLanguage)
            throws DataAccessException;

}
