package view.translateText;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import interface_adapter.imageUpload.ImageUploadController;
import interface_adapter.switchTranslation.SwitchTranslationController;
import interface_adapter.translateText.TranslateState;
import interface_adapter.translateText.TranslateTextController;
import interface_adapter.translateText.TranslateTextViewModel;

/**
 * The View for when the user is translating.
 */
public class TranslateTextView extends JPanel implements ActionListener, PropertyChangeListener {

    private JTextArea translateInputField;
    private JTextArea translateOutputField;
    private JComboBox<String> inputLanguageComboBox;
    private JComboBox<String> outputLanguageComboBox;

    private TranslateTextViewButtonPanel buttonPanel;
    private TranslateTextViewTextPanel inputPanel;
    private TranslateTextViewTextPanel outputPanel;

    public TranslateTextView(TranslateTextViewModel translateTextViewModel) {

        translateTextViewModel.addPropertyChangeListener(this);

    }

    /**
     * React to a button click that results in evt.
     * @param evt the ActionEvent to react to
     */
    public void actionPerformed(ActionEvent evt) {
        System.out.println("Click " + evt.getActionCommand());
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final TranslateState state = (TranslateState) evt.getNewValue();
        setFields(state);
        if (state.getError() != null) {
            JOptionPane.showMessageDialog(this, state.getError(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void setFields(TranslateState state) {

        translateOutputField.setText(state.getOutputText());
        inputLanguageComboBox.setSelectedItem(state.getInputLanguage());
        outputLanguageComboBox.setSelectedItem(state.getOutputLanguage());
        translateInputField.setText(state.getInputText());

    }

    /**
     * Sets the components of TranslateTextView that are modified during the program.
     * @param outputTextArea text area where translated is displayed
     * @param inputComboBox combobox to select input language
     * @param outputComboBox combobox to select input language
     * @param inputTextArea text where text is inputted
     */
    public void setObservableComponents(JTextArea outputTextArea,
                                         JComboBox<String> inputComboBox,
                                         JComboBox<String> outputComboBox,
                                         JTextArea inputTextArea) {
        this.translateOutputField = outputTextArea;
        this.inputLanguageComboBox = inputComboBox;
        this.outputLanguageComboBox = outputComboBox;
        this.translateInputField = inputTextArea;
    }

    public void setButtonPanel(TranslateTextViewButtonPanel buttonPanel) {
        this.buttonPanel = buttonPanel;
    }

    public void setInputPanel(TranslateTextViewTextPanel inputPanel) {
        this.inputPanel = inputPanel;
    }

    public void setOutputPanel(TranslateTextViewTextPanel outputPanel) {
        this.outputPanel = outputPanel;
    }

    /**
     * Injects TranslateText controller into textButton.
     * @param translateTextcontroller the controller for TranslateText Use Case
     */
    public void setTranslateTextController(TranslateTextController translateTextcontroller) {
        buttonPanel.setTranslateTextController(translateTextcontroller);

    }

    /**
     * Injects ImageUpload controller into imageUploadButton.
     * @param imageUploadController the controller for ImageUpload Use Case
     */
    public void setImageUploadController(ImageUploadController imageUploadController) {
        buttonPanel.setImageUploadController(imageUploadController);
    }

    /**
     * Injects SwitchTranslation controller into switchButton.
     * @param switchTranslationController the controller for SwitchTranslation Use Case
     */
    public void setSwitchTranslationController(SwitchTranslationController switchTranslationController) {
        buttonPanel.setSwitchTranslationController(switchTranslationController);
    }

    /**
     * Injects TextToSpeechController into speaker button.
     * @param textToSpeechController the controller for TextToSpeech Use Case
     */
    public void setTextToSpeechController(MockTextToSpeechController textToSpeechController) {
        inputPanel.setTextToSpeechController(textToSpeechController);
        outputPanel.setTextToSpeechController(textToSpeechController);
    }

}
