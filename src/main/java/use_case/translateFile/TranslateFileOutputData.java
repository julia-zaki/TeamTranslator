package use_case.translateFile;

import java.io.File;

/**
 * Output Data for  the TranslateFile Use Case.
 */
public class TranslateFileOutputData {

    private final File outputFile;

    public TranslateFileOutputData( File outputFile ) {
        this.outputFile = outputFile;
    }

    public File getOutputFile() {
        return outputFile;
    }
}
