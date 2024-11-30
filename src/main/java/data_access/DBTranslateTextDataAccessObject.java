package data_access;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deepl.api.DeepLException;
import com.deepl.api.TextResult;
import use_case.switchTranslation.SwitchTranslationDataAccessInterface;
import use_case.translateText.DataAccessException;
import use_case.translateText.TranslateTextDataAccessInterface;

/**
 * The DAO for translating text using DeepL API.
 * API link: <a href="https://developers.deepl.com/docs/api-reference/translate">...</a>.
 */
public class DBTranslateTextDataAccessObject extends DeeplTranslator implements TranslateTextDataAccessInterface,
        SwitchTranslationDataAccessInterface {

    public DBTranslateTextDataAccessObject() {
    }

    /**
     * Translate a text into the specified output language.
     *
     * @param text           the text to be translated.
     * @param inputLanguage  the language name of text (ie. English). Can be "Detect Language".
     * @param outputLanguage the language name (ie. English) to which the text is translated
     * @return a set withs keys "text" and "language" with their corresponding values
     * @throws DataAccessException if the text could not be translated for any reason
     */

    @Override
    public Map<String, String> translateText(String text, String inputLanguage, String outputLanguage)
            throws DataAccessException {
        final Map<String, String> result = new HashMap<>();
        final TextResult translationResult;
        try {
            translationResult = super.getTranslator().translateText(text, languageToCode(inputLanguage),
                    languageToCode(outputLanguage));
        }
        catch (DeepLException | InterruptedException ex) {
            throw new DataAccessException(ex.getMessage());
        }

        if (Constants.DETECT.equalsIgnoreCase(inputLanguage)) {
            result.put(Constants.LANGUAGE_KEY, codeToLanguage(translationResult.getDetectedSourceLanguage()
                    .toUpperCase()));
        }

        else {
            result.put(Constants.LANGUAGE_KEY, inputLanguage);
        }

        result.put(Constants.TEXT_KEY, translationResult.getText());
        return result;

    }

    /**
     * Return all possible input languages available for translation.
     * @return the list of input languages
     */
    @Override
    public List<String> getInputLanguages() {
        return super.getInputLanguages();
    }

    /**
     * Return all possible output languages for the given input language.
     * If the input language is null, return the list of all possible output languages.
     *
     * @param inputLanguage the input language
     * @return the list of output languages
     */
    @Override
    public List<String> getOutputLanguages(String inputLanguage) {
        return super.getOutputLanguages(inputLanguage);
    }

    /**
     * Switch the input language to be the output language and vice versa. Switch the input text to be the output
     * text and vice versa
     *
     * @param inputText the input language.
     * @param inputLanguage the input language.
     * @param outputLanguage the input language.
     * @return a mapping with the keys as the output text/language and values as input text/language.
     * @throws DataAccessException if the text could not be translated for any reason
     */

    @Override
    public Map<String, String> switchLanguagesAndTexts(String inputText, String inputLanguage, String outputLanguage)
            throws DataAccessException {
        // translating the text using the translateText method
        final Map<String, String> translationResult = translateText(inputText, inputLanguage, outputLanguage);

        // Getting the translated text
        final String translatedText = translationResult.get(Constants.TEXT_KEY);

        // Creating new variables for the switch

        // switching.
        final Map<String, String> switchedResult = new HashMap<>();
        switchedResult.put(Constants.TEXT_KEY, translatedText);

        if ("English (British)".equals(outputLanguage) || "English (American)".equals(outputLanguage)) {
            final String newOutputLanguage = new String("English");
            switchedResult.put(Constants.LANGUAGE_KEY, newOutputLanguage);
        }
        else if ("Portuguese (Brazilian)".equals(outputLanguage) || "Portuguese (European)".equals(outputLanguage)) {
            final String newOutputLanguage = new String("Portuguese");
            switchedResult.put(Constants.LANGUAGE_KEY, newOutputLanguage);
        }
        else if ("Chinese (simplified)".equals(outputLanguage)) {
            final String newOutputLanguage = new String("Chinese");
            switchedResult.put(Constants.LANGUAGE_KEY, newOutputLanguage);
        }
        else {
            switchedResult.put(Constants.LANGUAGE_KEY, outputLanguage);
        }

        switchedResult.put("translatedText", inputText);

        if ("English".equals(inputLanguage)) {
            final String newInputLanguage = new String("English (American)");
            switchedResult.put("outputLanguage", newInputLanguage);
        }
        else if ("Chinese".equals(inputLanguage)) {
            final String newInputLanguage = new String("Chinese (simplified)");
            switchedResult.put("outputLanguage", newInputLanguage);
        }
        else if ("Portuguese".equals(inputLanguage)) {
            final String newInputLanguage = new String("Portuguese (Brazilian)");
            switchedResult.put("outputLanguage", newInputLanguage);
        }
        else {
            switchedResult.put("outputLanguage", inputLanguage);
        }

        return switchedResult;
    }
}
