package use_case.switchTranslation;

/**
 * The output boundary  for the SwitchTranslation Use Case.
 */
public interface SwitchTranslationOutputBoundary {
    /**
     * Prepares the success view for the SwitchTranslation related Use Cases.
     * @param switchTranslationOutputData the output data
     */
    void prepareSuccessView(SwitchTranslationOutputData switchTranslationOutputData);

    /**
     * Prepares the failure view for the SwitchTranslation related Use Cases.
     * @param errorMessage the explanation of the failure
     */
    void prepareFailView(String errorMessage);
}
