package use_case.translateText;

/**
 * Output Data for  the TranslateText Use Case.
 */
public class TranslateTextOutputData {

    private final String inputText;
    private final String outputText;
    private final String inputLanguage;

    public TranslateTextOutputData(String inputText, String outputText, String inputLanguage) {
        this.inputText = inputText;
        this.outputText = outputText;
        this.inputLanguage = inputLanguage;
    }

    public String getInputText() {
        return inputText;
    }

    public String getOutputText() {
        return outputText;
    }

    public String getInputLanguage() {
        return inputLanguage;
    }
}
