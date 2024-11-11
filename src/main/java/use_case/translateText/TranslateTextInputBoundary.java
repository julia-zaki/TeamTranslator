package use_case.translateText;

/**
 * Input Boundary for actions which are related to translating text.
 */
public interface TranslateTextInputBoundary {

    /**
     * Executes the translatetext use case.
     * @param inputText the text to be translated.
     * @param inputLanguage  the language name of text (ie. English). Set to null to detect language.
     * @param outputLanguage the language name (ie. English) to which the text is translated
     */
    void execute(String inputLanguage, String inputText, String outputLanguage);

}
