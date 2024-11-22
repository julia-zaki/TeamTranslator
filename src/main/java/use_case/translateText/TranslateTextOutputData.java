package use_case.translateText;

/**
 * Output Data for  the TranslateText Use Case.
 */
public class TranslateTextOutputData {

    private final String outputText;
    private final String inputLanguage;

    public TranslateTextOutputData(String outputText, String inputLanguage) {
        this.outputText = outputText;
        this.inputLanguage = inputLanguage;
    }

    public String getOutputText() {
        return outputText;
    }

    public String getInputLanguage() {
        return inputLanguage;
    }
}
