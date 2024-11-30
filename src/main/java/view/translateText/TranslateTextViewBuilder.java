package view.translateText;

import java.awt.Component;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JTextArea;

import interface_adapter.translateText.TranslateTextViewModel;
import use_case.translateText.DataAccessException;
import use_case.translateText.TranslateTextDataAccessInterface;

/**
 * Builder for the TranslateTextView.
 */
public class TranslateTextViewBuilder {

    private TranslateTextViewTextPanel inputPanel;
    private TranslateTextViewTextPanel outputPanel;
    private TranslateTextViewButtonPanel buttonPanel;
    private TranslateTextViewModel translateTextViewModel;
    private TranslateTextDataAccessInterface translateTextDAO;
    private JTextArea translateInputField;
    private JTextArea translateOutputField;
    private JComboBox<String> inputLanguageComboBox;
    private List<String> inputLanguages = new ArrayList<>();
    private List<String> outputLanguages = new ArrayList<>();
    private TranslateTextView view;

    /**
     * Add the view model.
     * @param viewModel the information to be displayed by view.
     * @return this builder
     */
    public TranslateTextViewBuilder addViewModel(TranslateTextViewModel viewModel) {
        translateTextViewModel = viewModel;
        return this;
    }

    /**
     * Add the TranslateTextDAO. This is used to get
     * @param translateTextDai the data access interface
     * @return this builder
     */
    public TranslateTextViewBuilder addDAO(TranslateTextDataAccessInterface translateTextDai) {
        this.translateTextDAO = translateTextDai;
        return this;
    }

    /**
     * Retrieves the list of input and output languages.
     * @return this builder
     * @throws RuntimeException if called before addDAO
     */
    public TranslateTextViewBuilder addLanguages() {
        if (translateTextDAO == null) {
            throw new RuntimeException("addLanguages must be called after addDAO");
        }
        try {
            inputLanguages = translateTextDAO.getInputLanguages();
            outputLanguages = translateTextDAO.getOutputLanguages(null);
        }
        catch (DataAccessException ex) {
            System.out.println(ex.getMessage());
        }
        return this;
    }

    /**
     * Create the input panel where user can select input language, enter text, and turn input text to speech.
     * @return this builder
     * @throws RuntimeException if this method is called before addLanguages
     */
    public TranslateTextViewBuilder addInputPanel() {
        if (inputLanguages == null) {
            throw new RuntimeException("addInputPanel must be called after addLanguages");
        }

        inputPanel = new TranslateTextViewTextPanel(inputLanguages);
        inputPanel.setBorder(BorderFactory.createTitledBorder("INPUT"));
        inputPanel.getLanguageComboBox().setAlignmentX(Component.LEFT_ALIGNMENT);
        translateInputField = inputPanel.getTextArea();
        inputLanguageComboBox = inputPanel.getLanguageComboBox();

        return this;
    }

    /**
     * Create the outpanel where user can select input language, view translated text, and turn input text to speech.
     * @return this builder
     * @throws RuntimeException if this method is called before addLanguages
     */
    public TranslateTextViewBuilder addOutputPanel() {
        if (outputLanguages == null) {
            throw new RuntimeException("addOutputPanel must be called after addLanguages");
        }
        outputPanel = new TranslateTextViewTextPanel(outputLanguages);
        outputPanel.setBorder(BorderFactory.createTitledBorder("OUTPUT"));
        outputPanel.getLanguageComboBox().setAlignmentX(Component.LEFT_ALIGNMENT);
        outputPanel.getTextArea().setEditable(false);
        translateOutputField = outputPanel.getTextArea();
        return this;
    }

    /**
     * Create the Button Panel where user can select different features/use cases.
     * @return this builder
     */
    public TranslateTextViewBuilder addButtonPanel() {
        buttonPanel = new TranslateTextViewButtonPanel(view, inputPanel, outputPanel);

        return this;
    }

    /**
     * Creates the TranslateTextView and assigns values to its instance attributes.
     * @return an instance of TranslateTextView
     */
    public TranslateTextView build() {
        translateTextViewModel.addPropertyChangeListener(view);

        view = new TranslateTextView(translateTextViewModel);
        view.setObservableComponents(translateOutputField, inputLanguageComboBox, translateInputField);
        view.setButtonPanel(buttonPanel);
        view.setLayout(new FlowLayout());

        view.add(inputPanel);
        view.add(outputPanel);
        view.add(buttonPanel);

        return view;
    }

}
