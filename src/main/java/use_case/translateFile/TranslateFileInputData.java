package use_case.translateFile;

import java.io.File;

/**
 * The Input Data for the TranslateFile Use Case.
 */
public class TranslateFileInputData {

    private final String inputLanguage;
    private final File inputFile;
    private final String outputLanguage;

    public TranslateFileInputData(String inputLanguage, File inputFile, String outputLanguage) {
        this.inputLanguage = inputLanguage;
        this.inputFile = inputFile;
        this.outputLanguage = outputLanguage;
    }

    String getInputLanguage() {
        return inputLanguage;
    }

    File getInputFile() {
        return inputFile;
    }

    String getOutputLanguage() {
        return outputLanguage;
    }

}
