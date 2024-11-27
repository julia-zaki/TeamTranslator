package use_case.translateFile;

import data_access.Constants;
import entity.FileTranslator;
import use_case.translateText.DataAccessException;

/**
 * The TranslateFile Interactor.
 */
public class TranslateFileInteractor implements TranslateFileInputBoundary {

    private final TranslateFileDataAccessInterface dataAccessObject;
    private final TranslateFileOutputBoundary translateFileOutputBoundary;
    private final FileTranslator fileTranslator;

    public TranslateFileInteractor(TranslateFileDataAccessInterface translateFileDataAccessInterface,
                                   TranslateFileOutputBoundary translateFilePresenter,
                                   FileTranslator fileTranslator) {
        this.dataAccessObject = translateFileDataAccessInterface;
        this.translateFileOutputBoundary = translateFilePresenter;
        this.fileTranslator = fileTranslator;

    }

    @Override
    public void executeUpload(TranslateFileInputData translateFileInputData) {

        try {
            fileTranslator.setInputLanguage(translateFileInputData.getInputLanguage());
            fileTranslator.setOutputLanguage(translateFileInputData.getOutputLanguage());
            fileTranslator.setInputFile(translateFileInputData.getInputFile());

            if (!dataAccessObject.getInputLanguages().contains(fileTranslator.getInputLanguage())) {
                translateFileOutputBoundary.prepareFailView("Selected language does not exist in translator.");
            }
            else if (!dataAccessObject.getOutputLanguages(fileTranslator.getInputLanguage()).contains(
                    fileTranslator.getOutputLanguage())) {
                translateFileOutputBoundary.prepareFailView("Translated language does not exist in translator.");
            }
            else if (fileTranslator.getInputFile() == null) {
                translateFileOutputBoundary.prepareFailView("No file is uploaded.");
            }

            else if (!Constants.FILE_SIZE_LIMITS.containsKey(fileTranslator.getFileType())) {
                translateFileOutputBoundary.prepareFailView("Unsupported file type.");
            }

            else if (fileTranslator.getFileSize() > fileTranslator.getMaxFileSize()) {
                translateFileOutputBoundary.prepareFailView("File size exceeds the maximum allowed.");
            }

            else {
                fileTranslator.setDocumentID();
                fileTranslator.setDocumentKey();
                fileTranslator.setStatus(fileTranslator.getDocumentID(), fileTranslator.getDocumentKey());
                final TranslateFileOutputData translateFileOutputData = new TranslateFileOutputData(
                        fileTranslator.getInputLanguage(), fileTranslator.getOutputLanguage(),
                        fileTranslator.getOutputFile(), fileTranslator.getDocumentID(),
                        fileTranslator.getDocumentKey());
                translateFileOutputBoundary.prepareSuccessView(translateFileOutputData);
            }
        }
        catch (DataAccessException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void executeDownload(TranslateFileInputData translateFileInputData) {
        try {
            if (translateFileInputData.getDocumentID().isEmpty()) {
                translateFileOutputBoundary.prepareFailView("Unable to determine the document ID.");
            }
            else if (translateFileInputData.getDocumentKey().isEmpty()) {
                translateFileOutputBoundary.prepareFailView("Unable to determine the document key.");
            }
            else {
                fileTranslator.setOutputFile(dataAccessObject.downloadDocument(fileTranslator.getDocumentID(),
                        fileTranslator.getDocumentKey()));
                final TranslateFileOutputData translateFileOutputData = new TranslateFileOutputData(
                        fileTranslator.getInputLanguage(), fileTranslator.getOutputLanguage(),
                        fileTranslator.getOutputFile(), fileTranslator.getDocumentID(),
                        fileTranslator.getDocumentKey());
                translateFileOutputBoundary.prepareSuccessView(translateFileOutputData);
            }
        }
        catch (DataAccessException ex) {
            throw new RuntimeException(ex);
        }
    }
}
