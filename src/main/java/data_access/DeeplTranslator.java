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

import com.deepl.api.Translator;
import org.json.JSONArray;

import com.deepl.api.DeepLException;
import com.deepl.api.Language;
import use_case.translateText.DataAccessException;

/**
 * A DeeplTranslator that contains methods for getting all target and source languages compatible
 * with DeepL's API, and methods for converting between DeepL's language codes and language names.
 */
public class DeeplTranslator {

    private static final String AUTH_KEY = "a3c3d2b6-e5e2-42ce-aac7-aba5f20a0571:fx";
    private final Map<String, String> codeToLanguage = new HashMap<>();
    private final Map<String, String> languageToCode = new HashMap<>();
    private List<String> inputLanguages = new ArrayList<>();
    private List<String> outputLanguages = new ArrayList<>();
    private final Translator translator;

    public DeeplTranslator() {
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

    protected Translator getTranslator() {
        return translator;
    }

    /**
     * Return all possible input languages available for translation.
     * @return the list of input languages
     */
    protected List<String> getInputLanguages() {
        return inputLanguages;
    }

    /**
     * Return all possible output languages for the given input language.
     * If the input language is null, return the list of all possible output languages.
     *
     * @param inputLanguage the input language
     * @return the list of output languages
     */
    protected List<String> getOutputLanguages(String inputLanguage) {
        return outputLanguages;
    }
}
