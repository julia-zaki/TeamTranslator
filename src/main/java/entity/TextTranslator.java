package entity;

import java.util.Map;

import data_access.Constants;
import use_case.translateText.DataAccessException;
import use_case.translateText.TranslateTextDataAccessInterface;

/**
 * The representation of a Text Translator.
 */
public class TextTranslator {

    private final TranslateTextDataAccessInterface dataAccessObject;
    private String inputText;
    private String inputLanguage;
    private String outputLanguage;
    private String outputText;

    public TextTranslator(TranslateTextDataAccessInterface dataAccessObject) {
        this.dataAccessObject = dataAccessObject;
    }

    public void setInputLanguage(String inputLanguage) {
        this.inputLanguage = inputLanguage;
    }

    public void setOutputLanguage(String outputLanguage) {
        this.outputLanguage = outputLanguage;
    }

    public void setInputText(String inputText) {
        this.inputText = inputText;
    }

    public void setOutputText(String outputText) {
        this.outputText = outputText;
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

    /**
     * Translates the input text in the specified output language (input language could be "Detect Language")
     * and updates the output text and input text (if detecting language).
     * @throws DataAccessException if the text could not be translated for any reason
     */
    public void translate() throws DataAccessException {
        final Map<String, String> translationResult = dataAccessObject.translateText(
                inputText, inputLanguage, outputLanguage);
        outputText = translationResult.get(Constants.TEXT_KEY);
        inputLanguage = translationResult.get(Constants.LANGUAGE_KEY);
    }

}
