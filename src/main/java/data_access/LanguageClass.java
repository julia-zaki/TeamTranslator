package data_access;

import java.util.Arrays;

public class LanguageClass implements LanguageMapperInterface {

    private final String inputLanguage;
    private final String[] outputLanguages;

    public LanguageClass(String inputLanguage, String[] outputLanguages) {
        this.inputLanguage = inputLanguage;
        this.outputLanguages = outputLanguages;
    }

    /**
     * Given an input language, return what it will be when that language an output language.
     * @param inputLanguage  the language name of text (ie. English). Set to null to detect language.
     * @return what that language will be as an output language
     */
    @Override
    public String giveOutput(String inputLanguage) {
        return outputLanguages[0];
    }

    /**
     * Given an input language, return what it will be when that language an output language.
     *
     * @param outputLanguage the language name (ie. English (American)) to which the text is translated
     */
    @Override
    public String giveInput(String outputLanguage) {
        return inputLanguage;
    }

    /**
     * Checks if the language matches any of this language mapper's input or output languages
     *
     * @param language the language to be matched
     * @return true or false whether the language is an input or output language of this mapper
     */
    public boolean matches(String language) {
        return (language.equals(inputLanguage) || Arrays.asList(outputLanguages).contains(language));
    }
}
