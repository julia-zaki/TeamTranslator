package data_access;

import use_case.translateText.DataAccessException;

/**
 * LanguageMapper Interface.
 */

public interface LanguageMapperInterface {
    /**
     * Given an input language, return what it will be when that language an output language.
     * @param inputLanguage  the language name of text (ie. English). Set to null to detect language.
     * @return what that language will be as an output language
     * @throws DataAccessException if the text could not be translated for any reason
     */
    String giveOutput(String inputLanguage)
            throws DataAccessException;

    /**
     * Given an input language, return what it will be when that language an output language.
     *
     * @param outputLanguage the language name (ie. English (American)) to which the text is translated
     * @return what that language will be as an input language
     * @throws DataAccessException if the text could not be translated for any reason
     */
    String giveInput(String outputLanguage)
            throws DataAccessException;

}
