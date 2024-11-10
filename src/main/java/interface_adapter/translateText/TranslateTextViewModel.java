package interface_adapter.translateText;

import interface_adapter.ViewModel;

/**
 * The ViewModel for the TranslateTextView.
 */
public class TranslateTextViewModel extends ViewModel<TranslateState> {
    public TranslateTextViewModel() {
        super("TranslateTextViewModel");
        setState(new TranslateState());
    }
}
