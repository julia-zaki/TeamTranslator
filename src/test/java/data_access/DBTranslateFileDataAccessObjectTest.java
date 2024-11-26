package data_access;

import org.junit.Test;
import use_case.translateText.DataAccessException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;


public class DBTranslateFileDataAccessObjectTest {
    DBTranslateFileDataAccessObject translateFileDAO = new DBTranslateFileDataAccessObject();
    @Test
    public void testTranslateFile() throws DataAccessException, IOException {

        File inputfile = new File("TestingFileTranslation.txt");
        Map<String, String> docInfo =
                translateFileDAO.translateDocumentUpload(inputfile,
                        "Detect Language", "English");
        String docId = docInfo.get("document_id");
        String docKey = docInfo.get("document_key");
        File fileTranslationResult = translateFileDAO.downloadDocument(docId, docKey);

        while (!"done".equals(translateFileDAO.getDocumentStatus(docId, docKey))){
            fileTranslationResult = translateFileDAO.downloadDocument(docId, docKey);
        }

        String translatedFileContent = readFileContent(fileTranslationResult);
        String expectedTranslation = "It's time. it's 2AM" + "\n"
                + "Can you lower your voice and whisper more?" + "\n"
                + "When this night gets a little deeper" + "\n"
                + "I feel like I can be more honest with you" + "\n";
        assert(translatedFileContent.equals(expectedTranslation));
    }

    private String readFileContent(File file) throws IOException {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        }
        return content.toString();
    }

}




