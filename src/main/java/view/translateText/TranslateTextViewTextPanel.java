package view.translateText;

import java.awt.Component;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * Text Panel in TranslateTextView that has common elements from the input and output panel.
 */
public class TranslateTextViewTextPanel extends JPanel {
    private final JComboBox<String> languageComboBox;
    private final JTextArea textArea;
    private final JButton speakerButton;

    public TranslateTextViewTextPanel(ArrayList<String> languages) {

        // Format panel
        final BoxLayout boxLayout = new BoxLayout(this, BoxLayout.Y_AXIS);
        this.setLayout(boxLayout);
        this.setPreferredSize(new Dimension(300, 325));

        // Create combobox to select language
        languageComboBox = new JComboBox(languages.toArray());
        languageComboBox.setPreferredSize(new Dimension(300, 25));

        // Create text area in scroll pane
        textArea = new JTextArea();
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        final JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(300, 275));

        // Create speaker button
        final ImageIcon speaker = new ImageIcon("Images/speaker_resized.png");
        speakerButton = new JButton(speaker);
        speakerButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Add components to input panel
        this.add(languageComboBox);
        this.add(scrollPane);
        this.add(speakerButton);
    }

    public JComboBox<String> getLanguageComboBox() {
        return languageComboBox;
    }

    public JTextArea getTextArea() {
        return textArea;
    }

    public JButton getSpeakerButton() {
        return speakerButton;
    }
}
