package view;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import data_access.DBTranslateTextDataAccessObject;
import interface_adapter.translateText.TranslateState;
import interface_adapter.translateText.TranslateTextController;
import interface_adapter.translateText.TranslateTextViewModel;
import use_case.translateText.DataAccessException;
import use_case.translateText.TranslateTextDataAccessInterface;

/**
 * The View for when the user is translating.
 */
public class TranslateTextView extends JPanel implements ActionListener, PropertyChangeListener {

    private final TranslateTextViewModel translateTextViewModel;

    private final JLabel translation = new JLabel("Translation");
    private final JTextArea translateInputField = new JTextArea();
    private final JTextArea translateOutputField = new JTextArea();
    private final TranslateTextDataAccessInterface translateTextDai = new DBTranslateTextDataAccessObject();
    private final List<String> inputLanguages;

    {
        try {
            inputLanguages = translateTextDai.getInputLanguages();
            outputLanguages = translateTextDai.getOutputLanguages(null);
        }
        catch (DataAccessException ex) {
            throw new RuntimeException(ex);
        }
    }

    private final JComboBox inputLanguageComboBox = new JComboBox(inputLanguages.toArray());
    private final List<String> outputLanguages;

    private final JComboBox outputLanguageComboBox = new JComboBox(outputLanguages.toArray());

    private final JButton textButton = new JButton("Translate Text");
    private final JButton videoButton = new JButton("Translate Video");
    private final JButton fileButton = new JButton("Translate File");
    private final JButton vocabButton = new JButton("Vocabulary");
    private TranslateTextController translateTextController;

    public TranslateTextView(TranslateTextViewModel translateTextViewModel) {

        translateOutputField.setEditable(false);
        translation.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.translateTextViewModel = translateTextViewModel;
        this.translateTextViewModel.addPropertyChangeListener(this);

        final JPanel buttons = new JPanel();
        buttons.add(textButton);
        buttons.add(videoButton);
        buttons.add(fileButton);
        buttons.add(vocabButton);

        textButton.addActionListener(
                evt -> {
                    if (evt.getSource().equals(textButton)) {
                        translateTextController.execute(inputLanguageComboBox.getSelectedItem().toString(),
                                translateInputField.getText(), (String) outputLanguageComboBox.getSelectedItem()
                                        .toString());
                    }
                }
        );

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.add(translation);
        this.add(inputLanguageComboBox);
        this.add(outputLanguageComboBox);
        this.add(translateInputField);
        this.add(translateOutputField);
        this.add(buttons);
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
    }

    public void setTranslateTextController(TranslateTextController controller) {
        this.translateTextController = controller;
    }
}

