package use_case.translateFile;

/**
 * The output boundary  for the Translate File Use Case.
 */
public interface TranslateFileOutputBoundary {
    /**
     * Prepares the success view for the TranslateFile related Use Cases.
     * @param translateFileOutputData the output data
     */
    void prepareSuccessView(TranslateFileOutputData translateFileOutputData);

    /**
     * Prepares the failure view for the TranslateFile related Use Cases.
     * @param errorMessage the explanation of the failure
     */
    void prepareFailView(String errorMessage);
}
