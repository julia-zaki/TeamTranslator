package view.translateText;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Builder for the TranslateTextView.
 */
public class TranslateTextViewBuilder {

    private TranslateTextViewTextPanel inputPanel;
    private TranslateTextViewTextPanel outputPanel;

    /**
     * Create the input panel where user can select input language, enter text, and turn input text to speech.
     * @param languages the input languages to be listed in the combobox
     * @return this builder
     */
    public TranslateTextViewBuilder addInputPanel(ArrayList<String> languages) {
        inputPanel = new TranslateTextViewTextPanel(languages);
        inputPanel.setBorder(BorderFactory.createTitledBorder("INPUT"));
        inputPanel.getLanguageComboBox().setAlignmentX(Component.LEFT_ALIGNMENT);
        return this;
    }

    /**
     * Create the out panel where user can select input language, view translated text, and turn input text to speech.
     * @param languages the output languages to be listed in the combobox
     * @return this builder
     */
    public TranslateTextViewBuilder addOutputPanel(ArrayList<String> languages) {
        outputPanel = new TranslateTextViewTextPanel(languages);
        outputPanel.setBorder(BorderFactory.createTitledBorder("OUTPUT"));
        outputPanel.getLanguageComboBox().setAlignmentX(Component.LEFT_ALIGNMENT);
        outputPanel.getTextArea().setEnabled(false);
        return this;
    }



}
