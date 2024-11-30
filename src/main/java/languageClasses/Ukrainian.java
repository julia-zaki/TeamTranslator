package languageClasses;

import data_access.LanguageMapperInterface;

/**
 * Ukrainian.
 */

public class Ukrainian implements LanguageMapperInterface {

    /**
     * Given an input language, return what it will be when that language an output language.
     * @param inputLanguage  the language name of text (ie. English). Set to null to detect language.
     * @return what that language will be as an output language
     */
    @Override
    public String giveOutput(String inputLanguage) {
        return "Ukrainian";
    }

    /**
     * Given an input language, return what it will be when that language an output language.
     *
     * @param outputLanguage the language name (ie. English (American)) to which the text is translated
     */
    @Override
    public String giveInput(String outputLanguage) {
        return "Ukrainian";
    }

}
