package use_case.translateText;

import java.util.Map;
import java.util.Set;

/**
 * Interface for the translateTextDAO. It consists of methods for
 * translating text, getting output languages for each input language,
 * and converting between language codes and language names.
 */
public interface TranslateTextDataAccessInterface {

    /**
     * Translate a text into the specified output language.
     * @param text the text to be translated.
     * @param inputLanguage  the language name of text (ie. English). Set to null to detect language.
     * @param outputLanguage the language name (ie. English) to which the text is translated
     * @return a set withs keys "text" and "language" with their corresponding values
     * @throws DataAccessException if the text could not be translated for any reason
     */
    Map<String, String> translateText(String text, String inputLanguage, String outputLanguage)
            throws DataAccessException;

    /**
     * Return all possible input languages available for translation.
     * @return the set of input languages
     * @throws DataAccessException if the input languages could not be retrieved for any reason
     */
    Set<String> getInputLanguages() throws DataAccessException;

    /**
     * Return all possible output languages for the given input language.
     * If the input language is null, return the list of all possible output languages.
     * @param inputLanguage the input language
     * @return the set of output languages
     * @throws DataAccessException if the output languages could not be retrieved for any reason
     */
    Set<String> getOutputLanguages(String inputLanguage) throws DataAccessException;

}
