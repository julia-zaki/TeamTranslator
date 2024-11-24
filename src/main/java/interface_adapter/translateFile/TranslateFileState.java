package interface_adapter.translateFile;

import java.io.File;

/**
 * The State for a Translating File.
 */
public class TranslateFileState {
    private File inputFile;
    private File outputFile;
    private String error;
    private String inputLanguage;
    private String outputLanguage;

    public File getInputFile() {
        return inputFile;
    }

    public void setInputFile(File inputFile) {
        this.inputFile = inputFile;
    }

    public File getOutputFile() {
        return outputFile;
    }

    public String getInputLanguage() {
        return inputLanguage;
    }

    public void setInputLanguage(String inputLanguage) {
        this.inputLanguage = inputLanguage;
    }

    public void setOutputLanguage(String outputLanguage) {
        this.outputLanguage = outputLanguage;
    }

    public String getOutputLanguage() {
        return outputLanguage;
    }

    public void setError(String errorMessage) {
        this.error = errorMessage;
    }

    public String getError() {
        return error;
    }

    public void setOutputFile(File outputFile) {
        this.outputFile = outputFile;
    }
}
