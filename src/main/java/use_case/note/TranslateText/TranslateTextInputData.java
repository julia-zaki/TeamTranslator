package use_case.note.TranslateText;

/**
 * The Input Data for the Signup Use Case.
 */
public class TranslateTextInputData {

    private final String username;
    private final String password;
    private final String repeatPassword;
    private final String inputLanguage;
    private final String inputText;

    public TranslateTextInputData(String username, String password, String repeatPassword, String inputLanguage, String inputText) {
        this.username = username;
        this.password = password;
        this.repeatPassword = repeatPassword;
        this.inputLanguage = inputLanguage;
        this.inputText = inputText;
    }

    String getUsername() {
        return username;
    }

    String getPassword() {
        return password;
    }

    public String getRepeatPassword() {
        return repeatPassword;
    }

    public String getInputLanguage() { return inputLanguage; }

    public String getInputText() { return inputText; }
}
