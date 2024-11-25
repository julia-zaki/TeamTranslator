package use_case.translateFile;

import java.io.File;
import java.util.Map;

import use_case.translateText.DataAccessException;
import use_case.translateText.TranslateTextDataAccessInterface;

/**
 * Interface for the translateFileDAO. It consists of methods for
 * uploading a file for translation, downloading the translated file.
 */
public interface TranslateFileDataAccessInterface extends TranslateTextDataAccessInterface {

    /**
     * Uploads a document to the DeepL API for translation and returns the document handle with a document ID
     * and a document key.
     *
     * @param inputFile the input file.
     * @param inputLanguage the input language
     * @param outputLanguage the language to be translated.
     * @return DocumentHandle containing the document ID and status.
     * @throws DataAccessException if the file could not be translated for any reason
     */
    Map<String, String> translateDocumentUpload(File inputFile, String inputLanguage, String outputLanguage)
            throws DataAccessException;

    /**
     * Return document status given the document id and document key from to the DeepL API.
     *
     * @param documentId  the document id.
     * @param documentKey the document key.
     * @return the document status for the uploaded document.
     */
    String getDocumentStatus(String documentId, String documentKey);

    /**
     * Downloads the translated document from the DeepL API using document ID and document key
     * and returns the file object.
     *
     * @param documentId the unique identifier of the document to be downloaded.
     * @param documentKey the document key used for authentication in the API request.
     * @return File object representing the translated document.
     * @throws DataAccessException  if the file could not be downloaded for any reason
     */
    File downloadDocument(String documentId, String documentKey) throws DataAccessException;

}
