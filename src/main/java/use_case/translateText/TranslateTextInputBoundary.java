package use_case.translateText;

/**
 * Input Boundary for actions which are related to translating text.
 */
public interface TranslateTextInputBoundary {

    /**
     * Executes the translatetext use case.
     * @param translateTextInputData the input data.
     */
    void execute(TranslateTextInputData translateTextInputData);

}
