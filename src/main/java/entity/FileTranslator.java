package entity;

import java.io.File;

import data_access.Constants;
import data_access.DBTranslateFileDataAccessObject;
import use_case.translateText.DataAccessException;
import use_case.translateFile.TranslateFileDataAccessInterface;

/**
 * The representation of a File Translator.
 */
public class FileTranslator extends TextTranslator {

    private final TranslateFileDataAccessInterface dataAccessObject = new DBTranslateFileDataAccessObject();
    private File inputFile;
    private File outputFile;
    private String documentID;
    private String documentKey;
    private String documentStatus;

    public FileTranslator() {

    }

    public void setInputFile(File inputFile) {
        this.inputFile = inputFile;
    }

    /**
     * Setter for the document ID by uploading input file.
     * @throws DataAccessException if the file could not be translated for any reason
     */
    public void setDocumentID() throws DataAccessException {
        this.documentID = dataAccessObject.translateDocumentUpload(
                this.inputFile, this.getInputLanguage(), this.getOutputLanguage()
        ).get("document_id");
    }

    /**
     * Setter for the document key by uploading input file.
     * @throws DataAccessException if the file could not be translated for any reason
     */
    public void setDocumentKey() throws DataAccessException {
        this.documentKey = dataAccessObject.translateDocumentUpload(
                this.inputFile, this.getInputLanguage(), this.getOutputLanguage()
        ).get("document_key");
    }

    public void setOutputFile(File outputFile) {
        this.outputFile = outputFile;
    }

    public File getInputFile() {
        return inputFile;
    }

    public File getOutputFile() {
        return outputFile;
    }

    public long getFileSize() {
        return inputFile.length();
    }

    public String getFileType() {
        return inputFile.getName().substring(inputFile.getName().lastIndexOf(".") + 1);
    }

    public long getMaxFileSize() {
        return Constants.FILE_SIZE_LIMITS.get(this.getFileType());
    }

    public String getDocumentID() {
        return documentID;
    }

    public String getDocumentKey() {
        return documentKey;
    }

    /**
     * Setter for the document status for the uploaded input file.
     */
    public void setStatus(String docID, String docKey) {
        this.documentStatus = dataAccessObject.getDocumentStatus(docID, docKey);
    }

    public String getStatus() {
        return documentStatus;
    }
}
