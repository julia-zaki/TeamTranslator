package use_case.translateFile;

import java.io.File;
import java.util.Map;

/**
 * The Input Data for the TranslateFile Use Case.
 */
public class TranslateFileInputData {

    private final String inputLanguage;
    private final File inputFile;
    private final String outputLanguage;
    private final String documentID;
    private final String documentKey;

    public TranslateFileInputData(String inputLanguage, File inputFile, String outputLanguage,
                                  String documentID, String documentKey) {
        this.inputLanguage = inputLanguage;
        this.inputFile = inputFile;
        this.outputLanguage = outputLanguage;
        this.documentID = documentID;
        this.documentKey = documentKey;
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

    public String getDocumentID() {
        return documentID;
    }

    public String getDocumentKey() {
        return documentKey;
    }
}
