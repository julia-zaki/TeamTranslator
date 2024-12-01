package data_access;

import java.util.ArrayList;
import java.util.List;

/**
 * LanguageMapper
 */

public class GetLanguageClass {

    LanguageClassFactory factory = new LanguageClassFactory();
    private static final List<LanguageClass> languageClasses = new ArrayList<LanguageClass>();

    public GetLanguageClass() {
        createLanguageClasses();
    }

    private void createLanguageClasses() {
        languageClasses.add(factory.createLanguageClass("Chinese",
                new String[] {"Chinese (simplified)", "Chinese (traditional)"}));
        languageClasses.add(factory.createLanguageClass("English",
                new String[] {"English (American)", "English (British)"}));
        languageClasses.add(factory.createLanguageClass("Portuguese",
                new String[] {"Portuguese (Brazilian)", "Portuguese (European)"}));

    }

    /**
     * @param language the language name of text (ie. English).
     */
    public LanguageMapperInterface giveLanguageClass(String language) {
        // If the language has multiple target languages
       for (LanguageClass languageClass: languageClasses) {
           if (languageClass.matches(language)) {
               return languageClass;
           }
       }

       // Else, input language and output languages are the same when switched
       return factory.createLanguageClass(language, new String[]{language});
    }
}
