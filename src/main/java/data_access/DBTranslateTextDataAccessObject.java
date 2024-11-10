package data_access;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

import com.deepl.api.DeepLException;
import com.deepl.api.TextResult;
import com.deepl.api.Translator;
import use_case.translateText.DataAccessException;
import use_case.translateText.TranslateTextDataAccessInterface;

/**
 * The DAO for translating text using DeepL API.
 * API link: <a href="https://developers.deepl.com/docs/api-reference/translate">...</a>.
 */
public class DBTranslateTextDataAccessObject implements TranslateTextDataAccessInterface {

    public static final String LANGUAGE_KEY = "language";
    public static final String TEXT_KEY = "text";
    private static final String AUTH_KEY = "a3c3d2b6-e5e2-42ce-aac7-aba5f20a0571:fx";
    private final Map<String, String> codeToLanguage = new HashMap<>();
    private final Map<String, String> languageToCode = new HashMap<>();
    private final Map<String, Set<String>> inputLanguageToOutputLanguages = new HashMap<>();

    public DBTranslateTextDataAccessObject() {
        try {
            getCodeLanguageMaps();
            getLanguagePairs();
        }
        catch (DataAccessException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Translate a text into the specified output language.
     *
     * @param text           the text to be translated.
     * @param inputLanguage  the language name of text (ie. English). Set to null to detect language.
     * @param outputLanguage the language name (ie. English) to which the text is translated
     * @return a set withs keys "text" and "language" with their corresponding values
     * @throws DataAccessException if the text could not be translated for any reason
     */

    @Override
    public Map<String, String> translateText(String text, String inputLanguage, String outputLanguage)
            throws DataAccessException {
        final Translator translator = new Translator(AUTH_KEY);
        final TextResult translationResult;
        final Map<String, String> result = new HashMap<>();
        try {

            translationResult = translator.translateText(text, languageToCode(inputLanguage),
                    languageToCode(outputLanguage));
        }
        catch (DeepLException | InterruptedException ex) {
            throw new DataAccessException(ex.getMessage());
        }

        if (inputLanguage == null) {
            result.put(LANGUAGE_KEY, codeToLanguage(translationResult.getDetectedSourceLanguage().toUpperCase()));
        }

        else {
            result.put(LANGUAGE_KEY, inputLanguage);
        }

        result.put(TEXT_KEY, translationResult.getText());
        return result;

    }

    /**
     * Get all possible output languages for translation for each possible input language.
     * @throws DataAccessException if the languages could not be retrieved for any reason
     */
    private void getLanguagePairs() throws DataAccessException {
        final String url = "https://api-free.deepl.com/v2/glossary-language-pairs";
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
            final JSONObject json = new JSONObject(response.body());
            final JSONArray languages = (JSONArray) json.get("supported_languages");

            // Format the JSONArray as a Map
            for (int i = 0; i < languages.length(); i++) {
                final JSONObject language = languages.getJSONObject(i);
                final String inputLanguage = language.getString("source_lang").toUpperCase();
                final String outputLanguage = language.getString("target_lang").toUpperCase();

                if (!inputLanguageToOutputLanguages.containsKey(inputLanguage)) {
                    final Set<String> set = new HashSet<>();
                    set.add(outputLanguage);
                    inputLanguageToOutputLanguages.put(inputLanguage, set);
                }

                else {
                    final Set<String> set = inputLanguageToOutputLanguages.get(inputLanguage);
                    set.add(outputLanguage);
                }
            }

        }
        catch (UncheckedIOException | InterruptedException | IOException | IllegalArgumentException ex) {
            throw new DataAccessException(ex.getMessage());
        }
    }

    private void getCodeLanguageMaps() throws DataAccessException {
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
     * @throws DataAccessException if the language name could not be retrieved for any reason
     */

    private String codeToLanguage(String code) throws DataAccessException {
        String result = null;
        if (code != null) {
            result = codeToLanguage.get(code);
        }
        return result;
    }

    /**
     * Convert from language name to language code.
     *
     * @param language the language name (ie. English)
     * @return the language code (ie. en)
     * @throws DataAccessException if the language code could not be retrieved for any reason
     */
    private String languageToCode(String language) throws DataAccessException {
        return languageToCode.get(language);
    }

    /**
     * Return all possible input languages available for translation.
     *
     * @return the set of input languages
     * @throws DataAccessException if the input languages could not be retrieved for any reason
     */
    @Override
    public Set<String> getInputLanguages() throws DataAccessException {
        return inputLanguageToOutputLanguages.keySet();
    }

    /**
     * Return all possible output languages for the given input language.
     * If the input language is null, return the list of all possible output languages.
     *
     * @param inputLanguage the input language
     * @return the set of output languages
     * @throws DataAccessException if the output languages could not be retrieved for any reason
     */
    @Override
    public Set<String> getOutputLanguages(String inputLanguage) throws DataAccessException {
        final Set<String> codes;
        final Set<String> result = new HashSet<>();
        if (inputLanguage != null) {
            codes = inputLanguageToOutputLanguages.get(inputLanguage);
        }
        else {
            codes = inputLanguageToOutputLanguages.keySet();
        }

        // Convert codes to language names
        for (String code: codes) {
            result.add(codeToLanguage.get(code));
        }
        return result;
    }
}
