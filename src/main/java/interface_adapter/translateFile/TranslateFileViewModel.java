package interface_adapter.translateFile;

import interface_adapter.ViewModel;

/**
 * The ViewModel for the TranslateFileView.
 */
public class TranslateFileViewModel extends ViewModel<TranslateFileState> {
    public TranslateFileViewModel() {
        super("TranslateFileViewModel");
        setState(new TranslateFileState());
    }
}
