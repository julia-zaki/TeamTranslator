package use_case.translateFile;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * Interface for the translateFileDAO. It consists of methods for
 * uploading a file for translation, downloading the translated file.
 */
public interface TranslateFileDataAccessInterface {

    /**
     * Return all possible input languages available for translation.
     * @return the set of input languages
     * @throws DataAccessException if the input languages could not be retrieved for any reason
     */
    List<String> getInputLanguages() throws DataAccessException;

    /**
     * Return all possible output languages for the given input language.
     * If the input language is null, return the list of all possible output languages.
     * @param inputLanguage the input language
     * @return the set of output languages
     * @throws DataAccessException if the output languages could not be retrieved for any reason
     */
    List<String> getOutputLanguages(String inputLanguage) throws DataAccessException;

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
