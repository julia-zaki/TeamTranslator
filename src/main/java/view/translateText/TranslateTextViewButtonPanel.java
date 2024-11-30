package view.translateText;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

import interface_adapter.imageUpload.ImageUploadController;
import interface_adapter.switchTranslation.SwitchTranslationController;
import interface_adapter.translateText.TranslateTextController;

/**
 * Button Panel of TranslateTextView where user can select which features of the app to use.
 * These include translating text, translating videos, translating files, uploading text from images,
 * and switching input and output fields.
 */
public class TranslateTextViewButtonPanel extends JPanel {

    private final JButton textButton = new JButton("Translate Text");
    private final JButton videoButton = new JButton("Translate Video");
    private final JButton fileButton = new JButton("Translate File");
    private final JButton switchButton = new JButton("Switch");
    private final JButton imageButton = new JButton("Image Upload");
    private final TranslateTextView view;
    private final TranslateTextViewTextPanel inputPanel;
    private final TranslateTextViewTextPanel outputPanel;
    private TranslateTextController translateTextController;
    private SwitchTranslationController switchTranslationController;
    private ImageUploadController imageUploadController;

    public TranslateTextViewButtonPanel(TranslateTextView view, TranslateTextViewTextPanel inputPanel,
                                        TranslateTextViewTextPanel outputPanel) {
        this.add(textButton);
        this.add(videoButton);
        this.add(fileButton);
        this.add(switchButton);
        this.add(imageButton);

        addTextButtonListener();
        addImageButtonListener();
        addSwitchButtonListener();

        this.view = view;
        this.inputPanel = inputPanel;
        this.outputPanel = outputPanel;
    }

    /**
     * Creates and injects an action listener into textButton.
     */
    public void addTextButtonListener() {
        textButton.addActionListener(
                evt -> {
                    if (evt.getSource().equals(textButton)) {
                        translateTextController.execute(inputPanel.getLanguageComboBox().getSelectedItem().toString(),
                                inputPanel.getTextArea().getText(), outputPanel.getLanguageComboBox().getSelectedItem()
                                        .toString());
                    }
                }
        );
    }

    /**
     * Creates and injects an action listener into imageButton.
     */
    public void addImageButtonListener() {
        final ActionListener listener = new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param evt the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent evt) {
                if (evt.getSource().equals(imageButton)) {
                    final JFileChooser fileChooser = new JFileChooser();
                    final FileNameExtensionFilter fileFilter = new FileNameExtensionFilter("Image Files",
                            "png", "jpg", "gif", "pdf", "tif", "bmp");
                    fileChooser.setFileFilter(fileFilter);
                    final int result = fileChooser.showOpenDialog(view);
                    if (result == JFileChooser.APPROVE_OPTION) {
                        final File imageFile = fileChooser.getSelectedFile();
                        imageUploadController.execute(imageFile, inputPanel.getTextArea().getText());
                        JOptionPane.showMessageDialog(view,
                                "", "Uploaded Image:",
                                JOptionPane.PLAIN_MESSAGE, new ImageIcon(imageFile.getAbsolutePath()));
                    }
                }
            }
        };

        imageButton.addActionListener(listener);
    }

    /**
     * Creates and injects an action listener into switchButton.
     */
    public void addSwitchButtonListener() {
        switchButton.addActionListener(evt -> {
            final String inputText = inputPanel.getTextArea().getText().trim();
            final Object inputLang = inputPanel.getLanguageComboBox().getSelectedItem();
            final Object outputLang = outputPanel.getLanguageComboBox().getSelectedItem();

            switchTranslationController.execute(inputText, inputLang.toString(), outputLang.toString());
        });
    }

    public void setTranslateTextController(TranslateTextController translateTextController) {
        this.translateTextController = translateTextController;
    }

    public void setSwitchTranslationController(SwitchTranslationController switchTranslationController) {
        this.switchTranslationController = switchTranslationController;
    }

    public void setImageUploadController(ImageUploadController imageUploadController) {
        this.imageUploadController = imageUploadController;
    }

}
