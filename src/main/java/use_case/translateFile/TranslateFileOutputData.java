package use_case.translateFile;

import java.io.File;

/**
 * Output Data for  the TranslateFile Use Case.
 */
public class TranslateFileOutputData {

    private final File outputFile;
    private final String inputLanguage;
    private final String outputLanguage;
    private final String documentID;
    private final String documentKey;

    public TranslateFileOutputData(String inputLanguage, String outputLanguage,
                                   File outputFile, String documentID, String documentKey) {

        this.inputLanguage = inputLanguage;
        this.outputLanguage = outputLanguage;
        this.outputFile = outputFile;
        this.documentID = documentID;
        this.documentKey = documentKey;
    }

    public File getOutputFile() {
        return outputFile;
    }

    public String getInputLanguage() {
        return inputLanguage;
    }

    public String getOutputLanguage() {
        return outputLanguage;
    }
}
