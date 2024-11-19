package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import interface_adapter.imageUpload.ImageUploadController;
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
    private final List<String> inputLanguages;
    private final JComboBox inputLanguageComboBox;
    private final List<String> outputLanguages;
    private final JComboBox outputLanguageComboBox;
    private final ImageIcon image = new ImageIcon();

    private final JButton textButton = new JButton("Translate Text");
    private final JButton videoButton = new JButton("Translate Video");
    private final JButton fileButton = new JButton("Translate File");
    private final JButton vocabButton = new JButton("Vocabulary");
    private final JButton imageButton = new JButton("Image Upload");
    private TranslateTextController translateTextController;
    private ImageUploadController imageUploadController;

    public TranslateTextView(TranslateTextViewModel translateTextViewModel,
                             TranslateTextDataAccessInterface translateTextDataAccess) {

        translateInputField.setLineWrap(true);
        translateOutputField.setLineWrap(true);
        translateInputField.setWrapStyleWord(true);
        translateOutputField.setWrapStyleWord(true);

        translateOutputField.setEditable(false);
        translation.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.translateTextViewModel = translateTextViewModel;
        this.translateTextViewModel.addPropertyChangeListener(this);

        try {
            inputLanguages = translateTextDataAccess.getInputLanguages();
            outputLanguages = translateTextDataAccess.getOutputLanguages(null);
        }
        catch (DataAccessException ex) {
            throw new RuntimeException(ex);
        }

        final JPanel inputPanel = new JPanel();
        final JPanel outputPanel = new JPanel();

        inputPanel.setBorder(BorderFactory.createTitledBorder("Input"));
        outputPanel.setBorder(BorderFactory.createTitledBorder("Output"));

        final BoxLayout boxLayout = new BoxLayout(inputPanel, BoxLayout.Y_AXIS);
        final BoxLayout boxLayout2 = new BoxLayout(outputPanel, BoxLayout.Y_AXIS);
        inputPanel.setLayout(boxLayout);
        outputPanel.setLayout(boxLayout2);
        inputPanel.setPreferredSize(new Dimension(300, 300));
        outputPanel.setPreferredSize(new Dimension(300, 300));

        inputLanguageComboBox = new JComboBox(inputLanguages.toArray());
        inputLanguageComboBox.setPreferredSize(new Dimension(300, 25));
        outputLanguageComboBox = new JComboBox(outputLanguages.toArray());
        outputLanguageComboBox.setPreferredSize(new Dimension(300, 25));

        final JPanel buttons = new JPanel();
        buttons.add(textButton);
        buttons.add(videoButton);
        buttons.add(fileButton);
        buttons.add(imageButton);
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

        imageButton.addActionListener(
                evt -> {
                    if (evt.getSource().equals(imageButton)) {
                        final JFileChooser fileChooser = new JFileChooser();
                        final FileNameExtensionFilter fileFilter = new FileNameExtensionFilter("Image Files",
                                "png", "jpg");
                        fileChooser.setFileFilter(fileFilter);
                        final int result = fileChooser.showOpenDialog(this);
                        if (result == JFileChooser.APPROVE_OPTION) {
                            imageUploadController.execute(fileChooser.getSelectedFile());
                        }
                    }
                }
        );

        inputLanguageComboBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        inputPanel.add(inputLanguageComboBox);
        inputPanel.add(translateInputField);

        outputLanguageComboBox.setAlignmentX(Component.RIGHT_ALIGNMENT);
        outputPanel.add(outputLanguageComboBox);
        outputPanel.add(translateOutputField);

        this.setLayout(new FlowLayout());

        this.add(inputPanel);
        this.add(outputPanel);
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
        translateInputField.setText(state.getInputText());

    }

    public void setTranslateTextController(TranslateTextController translateTextcontroller) {
        this.translateTextController = translateTextcontroller;
    }

    public void setImageUploadController(ImageUploadController imageUploadController) {
        this.imageUploadController = imageUploadController;
    }
}