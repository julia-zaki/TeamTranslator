package data_access;

public class LanguageClassFactory {

    public LanguageClass createLanguageClass(String inputLanguage, String[] outputLanguage) {
        return new LanguageClass(inputLanguage, outputLanguage);
    }

}
