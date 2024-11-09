package data_access;

import com.deepl.api.DeepLException;
import com.deepl.api.TextResult;
import com.deepl.api.Translator;
import use_case.note.DataAccessException;
import use_case.translateText.TranslateTextDataAccessInterface;

/**
 * The DAO for translating text using DeepL API.
 * API link: <a href="https://developers.deepl.com/docs/api-reference/translate">...</a>.
 */
public class DBTranslateTextDataAccessObject implements TranslateTextDataAccessInterface {

    /**
     * Translate a text into the specified output language.
     *
     * @param text           the text to be translated.
     * @param inputLanguage  the language of text. Set to null to detect language.
     * @param outputLanguage the language to which the text is translated
     * @return the text in the output language.
     * @throws DataAccessException if the text could not be translated for any reason
     */

    @Override
    public String translateText(String text, String inputLanguage, String outputLanguage) throws DataAccessException {
        final String authKey = "a3c3d2b6-e5e2-42ce-aac7-aba5f20a0571:fx";
        final Translator translator = new Translator(authKey);
        final TextResult result;
        try {
            result = translator.translateText(text, inputLanguage, outputLanguage);
        }
        catch (DeepLException | InterruptedException ex) {
            throw new DataAccessException(ex.getMessage());
        }

        return result.getText();
    }
}
