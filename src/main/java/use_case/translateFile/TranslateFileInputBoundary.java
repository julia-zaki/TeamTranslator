package use_case.translateFile;

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
     */
    void executedownload(TranslateFileInputData translateFileInputData) throws DataAccessException;

}
