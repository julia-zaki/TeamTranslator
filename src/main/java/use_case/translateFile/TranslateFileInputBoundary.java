package use_case.translateFile;

import use_case.translateText.DataAccessException;

/**
 * Input Boundary for actions which are related to translating file.
 */
public interface TranslateFileInputBoundary {

    /**
     * Executes the translatefile use case for uploading the document.
     * @param translateFileInputData the input data.
     */
    void executeUpload(TranslateFileInputData translateFileInputData);

    /**
     * Executes the translatefile use case for downloading the translated document.
     * @param translateFileInputData the input data.
     * @throws DataAccessException if the output file could not be retrieved for any reason
     */
    void executeDownload(TranslateFileInputData translateFileInputData) throws DataAccessException;

}
