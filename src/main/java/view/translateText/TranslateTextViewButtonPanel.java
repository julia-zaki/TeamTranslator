package view.translateText;

import interface_adapter.imageUpload.ImageUploadController;
import interface_adapter.translateText.TranslateTextController;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * Button Panel of TranslateTextView where user can select which features of the app to use.
 * These include translating text, translating videos, translating files, uploading text from images,
 * and switching input and output fields.
 */
public class TranslateTextViewButtonPanel extends JPanel{

    private final JButton textButton = new JButton("Translate Text");
    private final JButton videoButton = new JButton("Translate Video");
    private final JButton fileButton = new JButton("Translate File");
    private final JButton switchButton = new JButton("Switch");
    private final JButton imageButton = new JButton("Image Upload");

    public TranslateTextViewButtonPanel() {
        this.add(textButton);
        this.add(videoButton);
        this.add(fileButton);
        this.add(switchButton);
        this.add(imageButton);
    }

    private void addTextButtonListener(TranslateTextController controller, TranslateTextViewTextPanel inputPanel,
                                       TranslateTextViewTextPanel outputPanel) {
        textButton.addActionListener(
                evt -> {
                    if (evt.getSource().equals(textButton)) {
                        controller.execute(inputPanel.getLanguageComboBox().getSelectedItem().toString(),
                                inputPanel.getTextArea().getText(), outputPanel.getLanguageComboBox().getSelectedItem()
                                        .toString());
                    }
                }
        );
    }

    private void addImageButtonListener(ImageUploadController controller, TranslateTextViewTextPanel inputPanel,
                                        TranslateTextView view) {
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
                        controller.execute(imageFile, inputPanel.getTextArea().getText());
                        JOptionPane.showMessageDialog(view,
                                "", "Uploaded Image:",
                                JOptionPane.PLAIN_MESSAGE, new ImageIcon(imageFile.getAbsolutePath()));
                    }
                }
            }
        };

        imageButton.addActionListener(listener);
    }

    private void addSwitchButtonListener(){

    }

}
