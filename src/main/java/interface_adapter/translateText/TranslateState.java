package interface_adapter.translateText;

/**
 * The State for a note.
 * <p>For this example, a note is simplay a string.</p>
 */
public class TranslateState {
    private String text = "";
    private String error;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setError(String errorMessage) {
        this.error = errorMessage;
    }

    public String getError() {
        return error;
    }
}
