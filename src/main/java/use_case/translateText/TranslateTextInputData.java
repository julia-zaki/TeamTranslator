package use_case.translateText;

/**
 * The Input Data for the TranslateText Use Case.
 */
public class TranslateTextInputData {

    private final String inputLanguage;
    private final String inputText;
    private final String outputLanguage;

    public TranslateTextInputData(String inputLanguage, String inputText, String outputLanguage) {
        this.inputLanguage = inputLanguage;
        this.inputText = inputText;
        this.outputLanguage = outputLanguage;
    }

    String getInputLanguage() {
        return inputLanguage;
    }

    String getInputText() {
        return inputText;
    }

    public String getOutputLanguage() {
        return outputLanguage;
    }
}
