package data_access;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;

import com.deepl.api.DeepLException;
import com.deepl.api.Language;
import com.deepl.api.TextResult;
import com.deepl.api.Translator;
import use_case.switchTranslation.SwitchTranslationDataAccessInterface;
import use_case.translateText.DataAccessException;
import use_case.translateText.TranslateTextDataAccessInterface;

/**
 * The DAO for translating text using DeepL API.
 * API link: <a href="https://developers.deepl.com/docs/api-reference/translate">...</a>.
 */
public class DBTranslateTextDataAccessObject implements TranslateTextDataAccessInterface,
        SwitchTranslationDataAccessInterface {

    private static final String AUTH_KEY = "a3c3d2b6-e5e2-42ce-aac7-aba5f20a0571:fx";
    private final Map<String, String> codeToLanguage = new HashMap<>();
    private final Map<String, String> languageToCode = new HashMap<>();
    private List<String> inputLanguages = new ArrayList<>();
    private List<String> outputLanguages = new ArrayList<>();
    private final Translator translator;

    public DBTranslateTextDataAccessObject() {
        try {
            translator = new Translator(AUTH_KEY);
            getCodeLanguageMaps();
            getSourceLanguages();
            getTargetLanguages();
        }
        catch (DataAccessException ex) {
            throw new RuntimeException(ex);
        }
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
            translationResult = translator.translateText(text, languageToCode(inputLanguage),
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

    protected void getCodeLanguageMaps() throws DataAccessException {
        final String url = "https://api-free.deepl.com/v2/languages";
        final HttpClient client;

        // Get the http client
        try {
            client = HttpClient.newHttpClient();
            final HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Authorization", "DeepL-Auth-Key " + AUTH_KEY)
                    .GET()
                    .build();

            final HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            final JSONArray languageCodes = new JSONArray(response.body());

            for (int i = 0; i < languageCodes.length(); i++) {

                final String language = languageCodes.getJSONObject(i).getString("name");
                final String code = languageCodes.getJSONObject(i).getString("language");
                codeToLanguage.put(code, language);
                languageToCode.put(language, code);
            }
        }
        catch (InterruptedException | IOException | IllegalArgumentException ex) {
            throw new DataAccessException(ex.getMessage());
        }

    }

    /**
     * Convert from language code to language name.
     *
     * @param code the language code (ie. en)
     * @return the language name (ie. English)
     */

    protected String codeToLanguage(String code) {
        String result = null;
        if (code != null) {
            result = codeToLanguage.get(code);
        }
        return result;
    }

    /**
     * Convert from language name to language code that is understood by the API methods.
     *
     * @param language the language name (example: English)
     * @return the language code (example: en)
     */
    protected String languageToCode(String language) {
        final String result;
        if (Constants.DETECT.equals(language)) {
            result = null;
        }

        else {
            result = languageToCode.get(language);
        }
        return result;
    }

    protected void getSourceLanguages() throws DataAccessException {
        final List<Language> sourceLanguages;
        try {
            sourceLanguages = translator.getSourceLanguages();
        }
        catch (DeepLException | InterruptedException ex) {
            throw new DataAccessException(ex.getMessage());
        }

        final String[] result = new String[sourceLanguages.size() + 1];
        result[0] = Constants.DETECT;
        for (int i = 0; i < sourceLanguages.size(); i++) {
            result[i + 1] = sourceLanguages.get(i).getName();
            codeToLanguage.put(sourceLanguages.get(i).getCode(), sourceLanguages.get(i).getName());
            languageToCode.put(sourceLanguages.get(i).getName(), sourceLanguages.get(i).getCode());
        }

        inputLanguages = List.of(result);
    }

    protected void getTargetLanguages() throws DataAccessException {
        final List<Language> targetLanguages;
        try {
            targetLanguages = translator.getTargetLanguages();
        }
        catch (DeepLException | InterruptedException ex) {
            throw new DataAccessException(ex.getMessage());
        }

        final List<String> result = new ArrayList<>();
        for (Language targetLanguage : targetLanguages) {
            result.add(targetLanguage.getName());

            codeToLanguage.put(targetLanguage.getCode(), targetLanguage.getName());
            languageToCode.put(targetLanguage.getName(), targetLanguage.getCode());
        }

        outputLanguages = result;
    }

    /**
     * Return all possible input languages available for translation.
     * @return the list of input languages
     */
    @Override
    public List<String> getInputLanguages() throws DataAccessException {
        return inputLanguages;
    }

    /**
     * Return all possible output languages for the given input language.
     * If the input language is null, return the list of all possible output languages.
     *
     * @param inputLanguage the input language
     * @return the list of output languages
     */
    @Override
    public List<String> getOutputLanguages(String inputLanguage) throws DataAccessException {
        return outputLanguages;
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
