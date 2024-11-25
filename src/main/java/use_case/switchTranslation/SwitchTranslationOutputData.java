package use_case.switchTranslation;

/**
 * Output Data for  the TranslateText Use Case.
 */
public class SwitchTranslationOutputData {
    private final String inputText;
    private final String inputLanguage;
    private final String translatedText;
    private final String outputLanguage;

    public SwitchTranslationOutputData(String inputText, String inputLanguage, String translatedText, String outputLanguage) {
        this.inputText = inputText;
        this.inputLanguage = inputLanguage;
        this.translatedText = translatedText;
        this.outputLanguage = outputLanguage;
    }

    public String getInputText() {
        return inputText;
    }

    public String getInputLanguage() {
        return inputLanguage;
    }

    public String getTranslatedText() {
        return translatedText;
    }

    public String getOutputLanguage() {
        return outputLanguage;
    }
}
