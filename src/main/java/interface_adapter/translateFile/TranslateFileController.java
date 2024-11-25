package interface_adapter.translateFile;

import java.io.File;

import use_case.translateFile.TranslateFileInputBoundary;
import use_case.translateFile.TranslateFileInputData;
import use_case.translateText.DataAccessException;

/**
 * The controller for the TranslateFile Use Case.
 */
public class TranslateFileController {

    private final TranslateFileInputBoundary translateFileUseCaseInteractor;

    public TranslateFileController(TranslateFileInputBoundary translateFileUseCaseInteractor) {
        this.translateFileUseCaseInteractor = translateFileUseCaseInteractor;
    }

    /**
     * Executes the TranslateFile Use Case for uploading the file.
     * @param inputLanguage the language of the input File
     * @param inputFile the input file for the translation
     * @param outputLanguage the language of the translation
     */
    public void executeUpload(String inputLanguage, File inputFile, String outputLanguage) {
        final TranslateFileInputData translateFileInputData = new TranslateFileInputData(
                inputLanguage, inputFile, outputLanguage, null, null);

        translateFileUseCaseInteractor.executeUpload(translateFileInputData);
    }

    /**
     * Executes the TranslateFile Use Case for downloading the file.
     * @param inputLanguage the language of the input File
     * @param inputFile the input file for the translation
     * @param outputLanguage the language of the translation
     * @param documentID the document id.
     * @param documentKey the document key.
     * @throws DataAccessException if the file cannot be downloaded for any reason.
     */
    public void executeDownload(String inputLanguage, File inputFile, String outputLanguage,
                                String documentID, String documentKey)
            throws DataAccessException {
        final TranslateFileInputData translateFileInputData = new TranslateFileInputData(
                inputLanguage, inputFile, outputLanguage, documentID, documentKey);

        translateFileUseCaseInteractor.executeDownload(translateFileInputData);
    }
}
