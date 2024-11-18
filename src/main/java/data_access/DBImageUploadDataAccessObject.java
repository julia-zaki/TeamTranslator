package data_access;

import java.io.File;
import java.io.IOException;
import java.util.Base64;

import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import use_case.translateText.DataAccessException;

/**
 * The DAO for recognizing text from an image using OCR Space API.
 */
public class DBImageUploadDataAccessObject {

    /**
     * Extracts text from an image.
     *
     * @param imageFile the file of the image
     * @return a String of text from the image
     * @throws DataAccessException if text could not be extracted for any reason
     */
    public static String getText(File imageFile) throws DataAccessException {

        final String apiKey = "c0885d222488957";
        final String result;
        final String baseImage;

        // Convert image to base64code
        try {
            final byte[] image = FileUtils.readFileToByteArray(imageFile);
            baseImage = Base64.getEncoder().encodeToString(image);
        }
        catch (IOException ex) {
            throw new DataAccessException(ex.getMessage());
        }

        // POST Method
        final OkHttpClient client = new OkHttpClient().newBuilder()
                .build();

        final RequestBody formBody = new FormBody.Builder()
                .add("base64Image", "data:image/png;base64," + baseImage)
                .add("language", "eng")
                .build();

        final Request request = new Request.Builder()
                .url("https://api.ocr.space/parse/image")
                .post(formBody)
                .addHeader("apikey", apiKey)
                .build();

        try {
            final Response response = client.newCall(request).execute();

            final JSONObject responseBody = new JSONObject(response.body().string());
            final JSONArray parsedResults = responseBody.getJSONArray("ParsedResults");
            result = parsedResults.getJSONObject(0).getString("ParsedText");
        }
        catch (IOException ex) {
            throw new DataAccessException(ex.getMessage());
        }
        return result;
    }
}
