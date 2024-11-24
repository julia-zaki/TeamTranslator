package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import data_access.DBTranslateFileDataAccessObject;
import interface_adapter.translateFile.TranslateFileController;
import interface_adapter.translateFile.TranslateFileState;
import interface_adapter.translateFile.TranslateFileViewModel;
import use_case.translateFile.DataAccessException;
import use_case.translateFile.TranslateFileDataAccessInterface;

/**
 * The View for when the user is translating a file.
 */
public class TranslateFileView extends JPanel implements ActionListener, PropertyChangeListener {

    private static final String ERROR_TITLE = "Error";
    private static final int BYTE_ARRAY_CONSTANT = 1024;
    private static final Dimension FILE_BUTTONS_DIM = new Dimension(500, 70);
    private static final Font FILEBUTTONS_FONT = new Font("Arial", Font.PLAIN, 25);
    private static final Color UPLOADBUTTON_COLOR = new Color(47, 237, 149);
    private static final Color DOWNLOADBUTTON_COLOR = new Color(170, 98, 209);

    private final TranslateFileViewModel translateFileViewModel;

    private final JLabel translation = new JLabel("File Translation");
    private final JFileChooser translateFileInputField = new JFileChooser();
    private final JFileChooser translateFileOutputField = new JFileChooser();
    private final TranslateFileDataAccessInterface translateFileDai = new DBTranslateFileDataAccessObject();
    private final List<String> inputLanguages;

    {
        try {
            inputLanguages = translateFileDai.getInputLanguages();
            outputLanguages = translateFileDai.getOutputLanguages(null);
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
    private final JButton vocabButton = new JButton("Vocabulary");
    private final JButton uploadButton = new JButton("Upload File");
    private final JButton downloadButton = new JButton("Download Translated File");
    private TranslateFileController translateFileController;
    private final TranslateFileState translateFileState = new TranslateFileState();

    public TranslateFileView(TranslateFileViewModel translateFileViewModel) {

        translation.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.translateFileViewModel = translateFileViewModel;
        this.translateFileViewModel.addPropertyChangeListener(this);

        final JPanel buttons = new JPanel();
        buttons.add(textButton);
        buttons.add(videoButton);
        buttons.add(vocabButton);

        final JPanel filebuttons = new JPanel();
        uploadButton.setPreferredSize(FILE_BUTTONS_DIM);
        uploadButton.setBackground(UPLOADBUTTON_COLOR);
        uploadButton.setFont(FILEBUTTONS_FONT);
        downloadButton.setPreferredSize(FILE_BUTTONS_DIM);
        downloadButton.setBackground(DOWNLOADBUTTON_COLOR);
        downloadButton.setFont(FILEBUTTONS_FONT);
        filebuttons.add(uploadButton);
        filebuttons.add(downloadButton);

        uploadButton.addActionListener(
                this::fileUploadRequest
        );

        downloadButton.addActionListener(
                evt -> {
                    try {
                        downloadFileRequest(evt);
                    }
                    catch (DataAccessException ex) {
                        throw new RuntimeException(ex);
                    }
                }
        );

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.add(translation);
        this.add(inputLanguageComboBox);
        this.add(outputLanguageComboBox);
        this.add(filebuttons);
        this.add(buttons);
    }

    private void fileUploadRequest(ActionEvent evt) {
        if (evt.getSource().equals(uploadButton)) {
            final int result = translateFileInputField.showOpenDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                translateFileState.setInputFile(translateFileInputField.getSelectedFile());
                JOptionPane.showMessageDialog(null,
                        "Selected File: " + translateFileState.getInputFile().getAbsolutePath());

                translateFileController.executeUpload(inputLanguageComboBox.getSelectedItem().toString(),
                        translateFileState.getInputFile(),
                        outputLanguageComboBox.getSelectedItem().toString());
            }
        }
    }

    private void downloadFileRequest(ActionEvent evt) throws DataAccessException {
        if (evt.getSource().equals(downloadButton)) {

            final Map<String, String> docInfo = translateFileDai.translateDocumentUpload(
                    translateFileState.getInputFile(),
                    inputLanguageComboBox.getSelectedItem().toString(),
                    outputLanguageComboBox.getSelectedItem().toString());

            final String docID = docInfo.get("document_id");
            final String docKey = docInfo.get("document_key");

            final String status = translateFileDai.getDocumentStatus(docID, docKey);

            if ("done".equals(status)) {
                final File translatedFile = translateFileDai.downloadDocument(
                        docID, docKey);
                translateFileState.setOutputFile(translatedFile);

                translateFileOutputField.setDialogType(JFileChooser.SAVE_DIALOG);
                translateFileOutputField.setApproveButtonText("Save");
                translateFileOutputField.setDialogTitle("Choose the location to save the translated file");
                final int returnValue = translateFileOutputField.showSaveDialog(null);

                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    final File selectedFile = translateFileOutputField.getSelectedFile();
                    try (InputStream translatedFileInputStream = new FileInputStream(translateFileState.getOutputFile());

                         OutputStream fileOutputStream = new FileOutputStream(selectedFile)) {

                        final byte[] buffer = new byte[BYTE_ARRAY_CONSTANT];
                        int bytesRead;

                        while ((bytesRead = translatedFileInputStream.read(buffer)) != -1) {
                            fileOutputStream.write(buffer, 0, bytesRead);
                        }
                        JOptionPane.showMessageDialog(null, "File saved successfully.",
                                "Success", JOptionPane.INFORMATION_MESSAGE);
                    }
                    catch (IOException ex) {
                        JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage(),
                                ERROR_TITLE, JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
            else {
                JOptionPane.showMessageDialog(null, "Error: " + status,
                        ERROR_TITLE, JOptionPane.ERROR_MESSAGE);
            }
        }
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
        final TranslateFileState state = (TranslateFileState) evt.getNewValue();
        setFields(state);
        if (state.getError() != null) {
            JOptionPane.showMessageDialog(this, state.getError(),
                    ERROR_TITLE, JOptionPane.ERROR_MESSAGE);
        }
    }

    private void setFields(TranslateFileState state) {
        translateFileOutputField.setSelectedFile(state.getOutputFile());
        inputLanguageComboBox.setSelectedItem(state.getInputLanguage());
    }

    public void setTranslateFileController(TranslateFileController controller) {
        this.translateFileController = controller;
    }

}
