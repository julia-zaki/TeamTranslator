package use_case.textToSpeech;

/**
 * Input Boundary for actions which are related to translating text.
 */
public interface TextToSpeechInputBoundary {

    /**
     * Executes the textToSpeech use case.
     * @param inputText the input data.
     */
    void execute(String inputText);

}
