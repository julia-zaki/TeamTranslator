package interface_adapter.translateText;

/**
 * The State for a Translation.
 */
public class TranslateState {
    private String text = "";
    private String error;
    private String inputLanguage;
    private String outputLanguage;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
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
