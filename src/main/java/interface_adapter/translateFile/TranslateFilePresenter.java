package interface_adapter.translateFile;

import use_case.translateFile.TranslateFileOutputBoundary;
import use_case.translateFile.TranslateFileOutputData;

/**
 * The presenter for the file translation program.
 */
public class TranslateFilePresenter implements TranslateFileOutputBoundary {

    private final TranslateFileViewModel translateFileViewModel;

    public TranslateFilePresenter(TranslateFileViewModel translateFileViewModel) {
        this.translateFileViewModel = translateFileViewModel;
    }

    /**
     * Prepares the success  view for the Translate related Use Cases.
     *
     * @param translateFileOutputData the output file.
     */
    @Override
    public void prepareSuccessView(TranslateFileOutputData translateFileOutputData) {
        translateFileViewModel.getState().setOutputFile(translateFileOutputData.getOutputFile());
        translateFileViewModel.getState().setInputLanguage(translateFileOutputData.getInputLanguage());
        translateFileViewModel.getState().setError(null);
        translateFileViewModel.firePropertyChanged();

    }

    /**
     * Prepares the failure view for the Translate related Use Cases.
     *
     * @param errorMessage the explanation of the failure
     */
    @Override
    public void prepareFailView(String errorMessage) {
        translateFileViewModel.getState().setError(errorMessage);
        translateFileViewModel.firePropertyChanged();
    }
}

