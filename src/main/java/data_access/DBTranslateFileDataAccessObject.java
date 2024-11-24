package data_access;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;

import org.json.JSONArray;
import org.json.JSONObject;

import com.deepl.api.*;
import com.deepl.api.parsing.Parser;
import use_case.translateFile.DataAccessException;
import use_case.translateFile.TranslateFileDataAccessInterface;

/**
 * The DAO for translating file using DeepL API.
 * API links: <a href="https://developers.deepl.com/docs/api-reference/translate">...</a>.
 *            <a href="https://developers.deepl.com/docs/api-reference/document">...</a>.
 */
public class DBTranslateFileDataAccessObject implements TranslateFileDataAccessInterface {

    private static final String AUTH_KEY = "a3c3d2b6-e5e2-42ce-aac7-aba5f20a0571:fx";
    private static final String BOUNDARY = "----WebKitFormBoundary"
            + Long.toHexString(System.currentTimeMillis()) + new Random().nextInt(1000);
    private final Map<String, String> codeToLanguage = new HashMap<>();
    private final Map<String, String> languageToCode = new HashMap<>();

    private List<String> inputLanguages = new ArrayList<>();
    private List<String> outputLanguages = new ArrayList<>();
    private final Translator translator;
    private Parser jsonParser;

    public DBTranslateFileDataAccessObject() {
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

    public Map<String, String> translateDocumentUpload(File inputFile, String inputLanguage, String outputLanguage)
            throws DataAccessException {

        final HttpClient client;
        final Map<String, String> docInfo = new HashMap<>();
        inputLanguage = languageToCode.get(inputLanguage);
        outputLanguage = languageToCode.get(outputLanguage);

        if (inputLanguage == null || inputLanguage.isEmpty()) {
            inputLanguage = "";
        }

        try {
            final HttpRequest request = buildMultipartRequest(inputFile, inputLanguage, outputLanguage);
            client = HttpClient.newHttpClient();

            final HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            final JSONObject docresponse = new JSONObject(response.body());
            docInfo.put("document_id", docresponse.get("document_id").toString());
            docInfo.put("document_key", docresponse.get("document_key").toString());

        }
        catch (IOException | InterruptedException ex) {
            throw new DataAccessException(ex.getMessage());
        }

        return docInfo;
    }

    private HttpRequest buildMultipartRequest(File inputFile, String inputLanguage, String outputLanguage)
            throws IOException {

        final String body = createMultipartBody(inputFile, inputLanguage, outputLanguage);
        final String url = "https://api-free.deepl.com/v2/document";

        return HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Authorization", "DeepL-Auth-Key " + AUTH_KEY)
                .header("Content-Type", "multipart/form-data; boundary=" + BOUNDARY)
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();
    }

    private String createMultipartBody(File inputFile, String inputLanguage, String outputLanguage) throws IOException {
        final StringBuilder body = new StringBuilder();

        body.append("--").append(BOUNDARY).append("\r\n");
        body.append("Content-Disposition: form-data; name=\"file\"; filename=\"").
                append(inputFile.getName()).append("\"\r\n");
        body.append("Content-Type: application/octet-stream\r\n\r\n");

        final byte[] fileBytes = Files.readAllBytes(inputFile.toPath());
        body.append(new String(fileBytes, StandardCharsets.UTF_8)).append("\r\n");

        body.append("--").append(BOUNDARY).append("\r\n");
        body.append("Content-Disposition: form-data; name=\"source_lang\"\r\n\r\n");
        body.append(inputLanguage).append("\r\n");

        body.append("--").append(BOUNDARY).append("\r\n");
        body.append("Content-Disposition: form-data; name=\"target_lang\"\r\n\r\n");
        body.append(outputLanguage).append("\r\n");

        body.append("--").append(BOUNDARY).append("--\r\n");

        return body.toString();
    }

    public String getDocumentStatus(String documentId, String documentKey) {
        try {
            final URI url = new URI("https://api-free.deepl.com/v2/document/" + documentId);

            final HttpClient client = HttpClient.newHttpClient();
            final String body = "{\"document_key\":\"" + documentKey + "\"}";
            final HttpRequest request = HttpRequest.newBuilder(url)
                    .header("Authorization", "DeepL-Auth-Key " + AUTH_KEY)
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(body))
                    .build();

            final HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            final JSONObject status = new JSONObject(response.body());

            if ( ! "done".equals(status.get("status").toString())) {
                return status.get("error_message").toString();
            }
            return status.get("status").toString();
        }
        catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    @Override
    public File downloadDocument(String documentId, String documentKey) throws DataAccessException {
        final File outputFile = new File("temp_translated_file");
        try {
            final URI url = new URI("https://api-free.deepl.com/v2/document/" + documentId + "/result");

            // Send a HTTP request and get the binary
            final HttpClient client = HttpClient.newHttpClient();
            final String body = "{\"document_key\":\"" + documentKey + "\"}";
            final HttpRequest request = HttpRequest.newBuilder(url)
                    .header("Authorization", "DeepL-Auth-Key " + AUTH_KEY)
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(body))
                    .build();

            final HttpResponse<InputStream> response = client.send(request, HttpResponse.BodyHandlers.ofInputStream());

            final InputStream inputStream = response.body();
            final FileOutputStream outputStream = new FileOutputStream(outputFile);
            final byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            return outputFile;
        }
        catch (Exception ex) {
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
     */
    private String codeToLanguage(String code) {
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
    private String languageToCode(String language) {
        final String result;
        if (Constants.DETECT.equals(language)) {
            result = null;
        }

        else {
            result = languageToCode.get(language);
        }
        return result;
    }

    private void getSourceLanguages() throws DataAccessException {
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

    private void getTargetLanguages() throws DataAccessException {
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
    public List<String> getOutputLanguages(String inputLanguage) {
        return outputLanguages;
    }
    
}
