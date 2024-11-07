package entity;

/**
 * The representation of a password-protected user for our program.
 */
public class User {

    private final String name;
    private final String password;
    private final String inputLanguage;
    private final String outputLanguage;
    private final String inputText;
    private final String outputText;


    public User(String name, String password, String inputLanguage,
                String outputLanguage, String inputText, String outputText) {
        this.name = name;
        this.password = password;
        this.inputLanguage = inputLanguage;
        this.inputText = inputText;
        this.outputLanguage = outputLanguage;
        this.outputText = outputText;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getInputLanguage() {
        return inputLanguage;
    }

    public String getOutputLanguage() {
        return outputLanguage;
    }

    public String getInputText() {
        return inputText;
    }

    public String getOutputText() {
        return outputText;
    }

}
