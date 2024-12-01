package use_case.textToSpeech;

/**
 * The output boundary  for the Translate Text Use Case.
 */
public interface TextToSpeechOutputBoundary {
    /**
     * Prepares the success view for the TranslateText related Use Cases.
     */
    void prepareSuccessView();

    /**
     * Prepares the failure view for the TranslateText related Use Cases.
     * @param errorMessage the explanation of the failure
     */
    void prepareFailView(String errorMessage);
}
