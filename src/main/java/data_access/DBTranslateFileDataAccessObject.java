package data_access;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.json.JSONObject;

import com.deepl.api.Translator;
import use_case.translateFile.TranslateFileDataAccessInterface;
import use_case.translateText.DataAccessException;

/**
 * The DAO for translating file using DeepL API.
 * API links: <a href="https://developers.deepl.com/docs/api-reference/translate">...</a>.
 *            <a href="https://developers.deepl.com/docs/api-reference/document">...</a>.
 */
public class DBTranslateFileDataAccessObject extends DBTranslateTextDataAccessObject implements TranslateFileDataAccessInterface {

    private static final String AUTH_KEY = "a3c3d2b6-e5e2-42ce-aac7-aba5f20a0571:fx";
    private static final String BOUNDARY = "----WebKitFormBoundary"
            + Long.toHexString(System.currentTimeMillis()) + new Random().nextInt(1000);
    private static final String AUTHORIZATION = "Authorization";
    private static final String DEEP_L_AUTH_KEY = "DeepL-Auth-Key ";
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String STR1 = "--";
    private static final String STR2 = "\r\n";

    public DBTranslateFileDataAccessObject() {
        super();
    }

    @Override
    public Map<String, String> translateDocumentUpload(File inputFile, String inputLanguage, String outputLanguage)
            throws DataAccessException {

        final HttpClient client;
        final Map<String, String> docInfo = new HashMap<>();

        try {
            final HttpRequest request = buildMultipartRequest(inputFile, languageToCode.get(inputLanguage),
                    languageToCode.get(outputLanguage));

            client = HttpClient.newHttpClient();

            final HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            final JSONObject docResponse = new JSONObject(response.body());
            docInfo.put("document_id", docResponse.get("document_id").toString());
            docInfo.put("document_key", docResponse.get("document_key").toString());

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
                .header(AUTHORIZATION, DEEP_L_AUTH_KEY + AUTH_KEY)
                .header(CONTENT_TYPE, "multipart/form-data; boundary=" + BOUNDARY)
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();
    }

    private String createMultipartBody(File inputFile, String inputLanguage, String outputLanguage) throws IOException {
        final StringBuilder body = new StringBuilder();

        body.append(STR1).append(BOUNDARY).append(STR2);
        body.append("Content-Disposition: form-data; name=\"file\"; filename=\"")
                .append(inputFile.getName()).append("\"\r\n");
        body.append("Content-Type: application/octet-stream\r\n\r\n");

        final byte[] fileBytes = Files.readAllBytes(inputFile.toPath());
        body.append(new String(fileBytes, StandardCharsets.UTF_8)).append(STR2);

        body.append(STR1).append(BOUNDARY).append(STR2);
        body.append("Content-Disposition: form-data; name=\"source_lang\"\r\n\r\n");
        body.append(inputLanguage).append(STR2);

        body.append(STR1).append(BOUNDARY).append(STR2);
        body.append("Content-Disposition: form-data; name=\"target_lang\"\r\n\r\n");
        body.append(outputLanguage).append(STR2);

        body.append(STR1).append(BOUNDARY).append("--\r\n");

        return body.toString();
    }

    @Override
    public String getDocumentStatus(String documentId, String documentKey) {
        try {
            final URI url = new URI("https://api-free.deepl.com/v2/document/" + documentId);

            final HttpClient client = HttpClient.newHttpClient();
            final String body = "{\"document_key\":\"" + documentKey + "\"}";
            final HttpRequest request = HttpRequest.newBuilder(url)
                    .header(AUTHORIZATION, DEEP_L_AUTH_KEY + AUTH_KEY)
                    .header(CONTENT_TYPE, "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(body))
                    .build();

            final HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            final JSONObject status = new JSONObject(response.body());
            String docStatus = status.get("status").toString();

            if (!"done".equals(docStatus)) {
                docStatus = status.get("error_message").toString();
            }
            return docStatus;
        }
        catch (IOException | InterruptedException | URISyntaxException ex) {
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
                    .header(AUTHORIZATION, DEEP_L_AUTH_KEY + AUTH_KEY)
                    .header(CONTENT_TYPE, "application/json")
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
        catch (IOException | InterruptedException | URISyntaxException ex) {
            throw new DataAccessException(ex.getMessage());
        }
    }

}
