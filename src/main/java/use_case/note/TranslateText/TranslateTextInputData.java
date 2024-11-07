package use_case.note.TranslateText;

/**
 * The Input Data for the Signup Use Case.
 */
public class TranslateTextInputData {

    private final String username;
    private final String password;
    private final String repeatPassword;

    public TranslateTextInputData(String username, String password, String repeatPassword) {
        this.username = username;
        this.password = password;
        this.repeatPassword = repeatPassword;
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
}
