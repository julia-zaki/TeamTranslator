package use_case.note.TranslateText;

/**
 * Input Boundary for actions which are related to signing up.
 */
public interface TranslateTextInputBoundary {

    /**
     * Executes the signup use case.
     * @param signupInputData the input data
     */
    void execute(SignupInputData signupInputData);

    /**
     * Executes the switch to login view use case.
     */
    void switchToLoginView();
}
