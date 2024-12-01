package data_access;

import languageClasses.*;

/**
 * LanguageMapper
 */

public class GetLanguageClass {
    /**
     * @param inputLanguage the language name of text (ie. English).
     */

    public static LanguageMapperInterface giveOutputLanguageClass(String inputLanguage) {
        LanguageMapperInterface result = null;

        if ("Bulgarian".equals(inputLanguage)) {
            result = new Bulgarian();
        }
        else if ("Chinese".equals(inputLanguage)) {
            result = new Chinese();
        }
        else if ("Czech".equals(inputLanguage)) {
            result = new Czech();
        }
        else if ("Danish".equals(inputLanguage)) {
            result = new Danish();
        }
        else if ("Dutch".equals(inputLanguage)) {
            result = new Dutch();
        }
        else if ("English".equals(inputLanguage)) {
            result = new English();
        }
        else if ("Estonian".equals(inputLanguage)) {
            result = new Estonian();
        }
        else if ("Finnish".equals(inputLanguage)) {
            result = new Finnish();
        }
        else if ("French".equals(inputLanguage)) {
            result = new French();
        }
        else if ("German".equals(inputLanguage)) {
            result = new German();
        }
        else if ("Greek".equals(inputLanguage)) {
            result = new Greek();
        }
        else if ("Hungarian".equals(inputLanguage)) {
            result = new Hungarian();
        }
        else if ("Indonesian".equals(inputLanguage)) {
            result = new Indonesian();
        }
        else if ("Italian".equals(inputLanguage)) {
            result = new Italian();
        }
        else if ("Japanese".equals(inputLanguage)) {
            result = new Japanese();
        }
        else if ("Korean".equals(inputLanguage)) {
            result = new Korean();
        }
        else if ("Latvian".equals(inputLanguage)) {
            result = new Latvian();
        }
        else if ("Lithuanian".equals(inputLanguage)) {
            result = new Lithuanian();
        }
        else if ("Norwegian".equals(inputLanguage)) {
            result = new Norwegian();
        }
        else if ("Polish".equals(inputLanguage)) {
            result = new Polish();
        }
        else if ("Portuguese".equals(inputLanguage)) {
            result = new Portuguese();
        }
        else if ("Romanian".equals(inputLanguage)) {
            result = new Romanian();
        }
        else if ("Russian".equals(inputLanguage)) {
            result = new Russian();
        }
        else if ("Slovak".equals(inputLanguage)) {
            result = new Slovak();
        }
        else if ("Slovenian".equals(inputLanguage)) {
            result = new Slovenian();
        }
        else if ("Spanish".equals(inputLanguage)) {
            result = new Spanish();
        }
        else if ("Swedish".equals(inputLanguage)) {
            result = new Swedish();
        }
        else if ("Turkish".equals(inputLanguage)) {
            result = new Turkish();
        }
        else if ("Ukrainian".equals(inputLanguage)) {
            result = new Ukrainian();
        }
        return result;
    }

    /**
     * @param outputLanguage the language name (ie. English (American)) to which the text is translated.
     */
    public static LanguageMapperInterface giveInputLanguageClass(String outputLanguage) {

        LanguageMapperInterface result2 = null;

        if ("Bulgarian".equals(outputLanguage)) {
            result2 = new Bulgarian();
        }
        else if ("Chinese (simplified)".equals(outputLanguage)) {
            result2 = new Chinese();
        }
        else if ("Czech".equals(outputLanguage)) {
            result2 = new Czech();
        }
        else if ("Danish".equals(outputLanguage)) {
            result2 = new Danish();
        }
        else if ("Dutch".equals(outputLanguage)) {
            result2 = new Dutch();
        }
        else if ("English (American)".equals(outputLanguage)) {
            result2 = new English();
        }
        else if ("English (British)".equals(outputLanguage)) {
            result2 = new English();
        }
        else if ("Estonian".equals(outputLanguage)) {
            result2 = new Estonian();
        }
        else if ("Finnish".equals(outputLanguage)) {
            result2 = new Finnish();
        }
        else if ("French".equals(outputLanguage)) {
            result2 = new French();
        }
        else if ("German".equals(outputLanguage)) {
            result2 = new German();
        }
        else if ("Greek".equals(outputLanguage)) {
            result2 = new Greek();
        }
        else if ("Hungarian".equals(outputLanguage)) {
            result2 = new Hungarian();
        }
        else if ("Indonesian".equals(outputLanguage)) {
            result2 = new Indonesian();
        }
        else if ("Italian".equals(outputLanguage)) {
            result2 = new Italian();
        }
        else if ("Japanese".equals(outputLanguage)) {
            result2 = new Japanese();
        }
        else if ("Korean".equals(outputLanguage)) {
            result2 = new Korean();
        }
        else if ("Latvian".equals(outputLanguage)) {
            result2 = new Latvian();
        }
        else if ("Lithuanian".equals(outputLanguage)) {
            result2 = new Lithuanian();
        }
        else if ("Norwegian".equals(outputLanguage)) {
            result2 = new Norwegian();
        }
        else if ("Polish".equals(outputLanguage)) {
            result2 = new Polish();
        }
        else if ("Portuguese (Brazilian)".equals(outputLanguage)) {
            result2 = new Portuguese();
        }
        else if ("Portuguese (European)".equals(outputLanguage)) {
            result2 = new Portuguese();
        }
        else if ("Romanian".equals(outputLanguage)) {
            result2 = new Romanian();
        }
        else if ("Russian".equals(outputLanguage)) {
            result2 = new Russian();
        }
        else if ("Slovak".equals(outputLanguage)) {
            result2 = new Slovak();
        }
        else if ("Slovenian".equals(outputLanguage)) {
            result2 = new Slovenian();
        }
        else if ("Spanish".equals(outputLanguage)) {
            result2 = new Spanish();
        }
        else if ("Swedish".equals(outputLanguage)) {
            result2 = new Swedish();
        }
        else if ("Turkish".equals(outputLanguage)) {
            result2 = new Turkish();
        }
        else if ("Ukrainian".equals(outputLanguage)) {
            result2 = new Ukrainian();
        }
        return result2;
    }
}
