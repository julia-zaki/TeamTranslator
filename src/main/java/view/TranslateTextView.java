package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileNameExtensionFilter;

import interface_adapter.imageUpload.ImageUploadController;
import interface_adapter.switchTranslation.SwitchTranslationController;
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
    private final JScrollPane inputScrollPane = new JScrollPane(translateInputField);
    private final JScrollPane outputScrollPane = new JScrollPane(translateOutputField);

    private final JButton textButton = new JButton("Translate Text");
    private final JButton videoButton = new JButton("Translate Video");
    private final JButton fileButton = new JButton("Translate File");
    private final JButton switchButton = new JButton("Switch");
    private final JButton imageButton = new JButton("Image Upload");
    private final ImageIcon speaker = new ImageIcon("Images/speaker_resized.png");
    private final JButton inputSpeakerButton = new JButton(speaker);
    private final JButton outputSpeakerButton = new JButton(speaker);
    private TranslateTextController translateTextController;
    private ImageUploadController imageUploadController;
    private SwitchTranslationController switchTranslationController;

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
        inputPanel.setPreferredSize(new Dimension(MagicNumber.ALPHA, MagicNumber.GAMMA));
        outputPanel.setPreferredSize(new Dimension(MagicNumber.ALPHA, MagicNumber.GAMMA));

        inputLanguageComboBox = new JComboBox(inputLanguages.toArray());
        inputLanguageComboBox.setPreferredSize(new Dimension(MagicNumber.ALPHA, MagicNumber.OMEGA));
        inputScrollPane.setPreferredSize(new Dimension(MagicNumber.ALPHA, MagicNumber.BETA));
        outputScrollPane.setPreferredSize(new Dimension(MagicNumber.ALPHA, MagicNumber.BETA));
        outputLanguageComboBox = new JComboBox(outputLanguages.toArray());
        outputLanguageComboBox.setPreferredSize(new Dimension(MagicNumber.ALPHA, MagicNumber.OMEGA));

        final JPanel buttons = new JPanel();
        buttons.add(textButton);
        buttons.add(videoButton);
        buttons.add(fileButton);
        buttons.add(imageButton);
        buttons.add(switchButton);

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
                                "png", "jpg", "gif", "pdf", "tif", "bmp");
                        fileChooser.setFileFilter(fileFilter);
                        final int result = fileChooser.showOpenDialog(this);
                        if (result == JFileChooser.APPROVE_OPTION) {
                            final File imageFile = fileChooser.getSelectedFile();
                            imageUploadController.execute(imageFile, translateInputField.getText());
                            JOptionPane.showMessageDialog(this,
                                    "", "Uploaded Image:",
                                    JOptionPane.PLAIN_MESSAGE, new ImageIcon(imageFile.getAbsolutePath()));
                        }
                    }
                }
        );

        switchButton.addActionListener(evt -> {
            final String inputText = translateInputField.getText().trim();
            final Object inputLang = inputLanguageComboBox.getSelectedItem();
            final Object outputLang = outputLanguageComboBox.getSelectedItem();

            switchTranslationController.execute(inputText, inputLang.toString(), outputLang.toString());
        });

        inputSpeakerButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        outputSpeakerButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        inputLanguageComboBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        inputPanel.add(inputLanguageComboBox);
        inputPanel.add(inputScrollPane);
        inputPanel.add(inputSpeakerButton);

        outputLanguageComboBox.setAlignmentX(Component.RIGHT_ALIGNMENT);
        outputPanel.add(outputLanguageComboBox);
        outputPanel.add(outputScrollPane);
        outputPanel.add(outputSpeakerButton);

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

        final String outputLanguage = state.getOutputLanguage();
        if (outputLanguages.contains(outputLanguage)) {
            outputLanguageComboBox.setSelectedItem(outputLanguage);
        }
        else {
            System.err.println("Invalid Output Language: " + outputLanguage);
            // Optionally, reset to a default value or show an error message
        }
    }

    public void setTranslateTextController(TranslateTextController translateTextcontroller) {
        this.translateTextController = translateTextcontroller;
    }

    public void setImageUploadController(ImageUploadController imageUploadController) {
        this.imageUploadController = imageUploadController;
    }

    public void setSwitchTranslationController(SwitchTranslationController switchTranslationController) {
        this.switchTranslationController = switchTranslationController;
    }

}
