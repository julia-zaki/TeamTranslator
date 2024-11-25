package use_case.switchTranslation;

/**
 * The Input Data for the SwitchTranslation Use Case.
 */
public class SwitchTranslationInputData {
    private final String inputText;
    private final String inputLanguage;
    private final String outputLanguage;

    public SwitchTranslationInputData(String inputText, String inputLanguage, String outputLanguage) {
        this.inputText = inputText;
        this.inputLanguage = inputLanguage;
        this.outputLanguage = outputLanguage;
    }

    public String getInputText() {
        return inputText;
    }

    public String getInputLanguage() {
        return inputLanguage;
    }

    public String getOutputLanguage() {
        return outputLanguage;
    }
}
