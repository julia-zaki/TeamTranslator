package use_case.switchTranslation;

/**
 * Input Boundary for actions which are related to switching the translation.
 */
public interface SwitchTranslationInputBoundary {

    /**
     * Executes the switchTranslation use case.
     * @param switchTranslationInputData the input data.
     */
    void execute(SwitchTranslationInputData switchTranslationInputData);

}

