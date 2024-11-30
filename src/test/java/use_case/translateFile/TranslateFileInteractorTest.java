package use_case.translateFile;

import data_access.DBTranslateFileDataAccessObject;
import entity.FileTranslator;
import org.junit.Test;
import use_case.translateText.DataAccessException;

import java.io.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class TranslateFileInteractorTest {

    private static final String INPUT_LANG = "Detect Language";
    private static final String OUTPUT_LANG = "French";
    private static final File INPUT_FILE = new File("TestFileTranslation.txt");
    private static final File OUTPUT_FILE = new File("Result_TestFileTranslation.txt");

    @Test
    public void successTest() throws DataAccessException {

        TranslateFileDataAccessInterface translateFileDAO = new DBTranslateFileDataAccessObject() {

            //Mock a successfully translated file by replacing it with in-memory file
            @Override
            public File downloadDocument(String documentID, String documentKey) {
                return OUTPUT_FILE;
            }

        };

        FileTranslator fileTranslator = new FileTranslator(translateFileDAO);
        String docID = translateFileDAO.translateDocumentUpload(INPUT_FILE, INPUT_LANG, OUTPUT_LANG).get("document_id");
        String docKey = translateFileDAO.translateDocumentUpload(INPUT_FILE, INPUT_LANG, OUTPUT_LANG).get("document_key");
        TranslateFileInputData inputData = new TranslateFileInputData(INPUT_LANG, INPUT_FILE, OUTPUT_LANG, docID, docKey);

        TranslateFileOutputBoundary translateFileOB = new TranslateFileOutputBoundary() {

            @Override
            public void prepareSuccessView(TranslateFileOutputData translateFileOutputData) {
                assertEquals(readFileContent(OUTPUT_FILE), readFileContent(translateFileOutputData.getOutputFile()));
            }

            @Override
            public void prepareFailView(String errorMessage) {
                fail("Use case failure is unexpected.");
            }
        };

        TranslateFileInputBoundary translateFileInteractor = new TranslateFileInteractor
                (translateFileDAO, translateFileOB, fileTranslator);

        translateFileInteractor.executeDownload(inputData);

    }

    @Test
    public void unsupportedFileTypeTest() {

        TranslateFileDataAccessInterface translateFileDAO = new DBTranslateFileDataAccessObject();

        final File inputImage = new File("Images/imageInFrench.png");
        FileTranslator fileTranslator = new FileTranslator(translateFileDAO);
        TranslateFileInputData inputData = new TranslateFileInputData(INPUT_LANG, inputImage, OUTPUT_LANG,
                null, null);

        TranslateFileOutputBoundary translateFileOB = new TranslateFileOutputBoundary() {

            @Override
            public void prepareSuccessView(TranslateFileOutputData translateFileOutputData) {
                fail("Use case success is unexpected");
            }

            @Override
            public void prepareFailView(String errorMessage) {
                assertEquals("Unsupported file type.", errorMessage);
            }
        };

        TranslateFileInputBoundary translateFileInteractor = new TranslateFileInteractor
                (translateFileDAO, translateFileOB, fileTranslator);

        translateFileInteractor.executeUpload(inputData);

    }

    @Test
    public void noFileUploadedTest() {

        TranslateFileDataAccessInterface translateFileDAO = new DBTranslateFileDataAccessObject();

        FileTranslator fileTranslator = new FileTranslator(translateFileDAO);
        TranslateFileInputData inputData = new TranslateFileInputData(INPUT_LANG,
                null, OUTPUT_LANG, null, null);

        TranslateFileOutputBoundary translateFileOB = new TranslateFileOutputBoundary() {

            @Override
            public void prepareSuccessView(TranslateFileOutputData translateFileOutputData) {
                fail("Use case success is unexpected");
            }

            @Override
            public void prepareFailView(String errorMessage) {
                assertEquals("No file is uploaded.", errorMessage);
            }
        };

        TranslateFileInputBoundary translateFileInteractor = new TranslateFileInteractor
                (translateFileDAO, translateFileOB, fileTranslator);

        translateFileInteractor.executeUpload(inputData);

    }


    private String readFileContent(File file) {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return content.toString();
    }
}
