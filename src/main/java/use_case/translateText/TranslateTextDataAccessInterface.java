package use_case.translateText;

import use_case.note.DataAccessException;

/**
 * Interface for the translateTextDAO. It consists of methods for
 * translating text.
 */
public interface TranslateTextDataAccessInterface {

    /**
     * Translate a text into the specified output language.
     * @param text the text to be translated.
     * @param inputLanguage the language of text. Set to null to detect language.
     * @param outputLanguage the language to which the text is translated
     * @return the text in the output language.
     * @throws DataAccessException if the text could not be translated for any reason
     */
    String translateText(String text, String inputLanguage, String outputLanguage) throws DataAccessException;
}
