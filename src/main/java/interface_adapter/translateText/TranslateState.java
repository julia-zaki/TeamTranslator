package interface_adapter.translateText;

/**
 * The State for a Translation.
 */
public class TranslateState {
    private String inputText = "";
    private String outputText = "";
    private String error;
    private String inputLanguage = "Detect Language";
    private String outputLanguage = "";

    public String getInputText() {
        return inputText;
    }

    public void setInputText(String inputText) {
        this.inputText = inputText;
    }

    public String getOutputText() {
        return outputText;
    }

    public void setOutputText(String outputText) {
        this.outputText = outputText;
    }

    public String getInputLanguage() {
        return inputLanguage;
    }

    public void setInputLanguage(String inputLanguage) {
        this.inputLanguage = inputLanguage;
    }

    public void setOutputLanguage(String outputLanguage) {
        this.outputLanguage = outputLanguage;
    }

    public String getOutputLanguage() {
        return outputLanguage;
    }

    public void setError(String errorMessage) {
        this.error = errorMessage;
    }

    public String getError() {
        return error;
    }
}
