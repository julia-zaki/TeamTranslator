package use_case.translateFile;

import java.io.File;

/**
 * Output Data for  the TranslateFile Use Case.
 */
public class TranslateFileOutputData {

    private final File outputFile;
    private String inputLanguage;

    public TranslateFileOutputData(File outputFile, String inputLanguage) {
        this.outputFile = outputFile;
        this.inputLanguage = inputLanguage;
    }

    public File getOutputFile() {
        return outputFile;
    }
}
