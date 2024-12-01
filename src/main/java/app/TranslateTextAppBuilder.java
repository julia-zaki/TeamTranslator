package app;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import entity.TextTranslator;
import interface_adapter.imageUpload.ImageUploadController;
import interface_adapter.imageUpload.ImageUploadPresenter;
import interface_adapter.switchTranslation.SwitchTranslationController;
import interface_adapter.switchTranslation.SwitchTranslationPresenter;
import interface_adapter.textToSpeech.TextToSpeechPresenter;
import interface_adapter.translateText.TranslateTextController;
import interface_adapter.translateText.TranslateTextPresenter;
import interface_adapter.translateText.TranslateTextViewModel;
import use_case.imageUpload.ImageUploadDataAccessInterface;
import use_case.imageUpload.ImageUploadInteractor;
import use_case.imageUpload.ImageUploadOutputBoundary;
import use_case.switchTranslation.SwitchTranslationDataAccessInterface;
import use_case.switchTranslation.SwitchTranslationInteractor;
import use_case.switchTranslation.SwitchTranslationOutputBoundary;
import use_case.textToSpeech.TextToSpeechDataAccessInterface;
import use_case.textToSpeech.TextToSpeechInteractor;
import use_case.textToSpeech.TextToSpeechOutputBoundary;
import use_case.translateText.TranslateTextDataAccessInterface;
import use_case.translateText.TranslateTextInteractor;
import use_case.translateText.TranslateTextOutputBoundary;
import interface_adapter.textToSpeech.TextToSpeechController;
import view.translateText.TranslateTextView;
import view.translateText.TranslateTextViewBuilder;

/**
 * Builder for the Translator Application.
 */
public class TranslateTextAppBuilder {
    public static final int HEIGHT = 500;
    public static final int WIDTH = 800;
    private TranslateTextDataAccessInterface translateTextDAO;
    private ImageUploadDataAccessInterface imageUploadDAO;
    private SwitchTranslationDataAccessInterface switchTranslationDAO;
    private TextToSpeechDataAccessInterface textToSpeechDAO;
    private TranslateTextViewModel translateTextViewModel = new TranslateTextViewModel();
    private TranslateTextView translateTextView;

    /**
     * Sets the translatetextDAO to be used in this application.
     * @param translateTextDataAccess the DAO to use
     * @return this builder
     */
    public TranslateTextAppBuilder addTranslateTextDAO(TranslateTextDataAccessInterface translateTextDataAccess) {
        translateTextDAO = translateTextDataAccess;
        return this;
    }

    /**
     * Creates the objects  for the TranslateText Use Case and connects the TranslateTextView to its
     * controller.
     * <p>This method must be called after addTranslateTextView!</p>
     * @return this builder
     * @throws RuntimeException if this method is called before addTranslateTextView
     */
    public TranslateTextAppBuilder addTranslateTextUseCase() {
        final TranslateTextOutputBoundary translateTextOutputBoundary =
                new TranslateTextPresenter(translateTextViewModel);
        final TextTranslator textTranslator = new TextTranslator(translateTextDAO);

        final TranslateTextInteractor translateTextInteractor = new TranslateTextInteractor(translateTextDAO,
                translateTextOutputBoundary,
                textTranslator);

        final TranslateTextController controller = new TranslateTextController(translateTextInteractor);
        if (translateTextView == null) {
            throw new RuntimeException("addTranslateTextView must be called before addTranslateTextUseCase");
        }
        translateTextView.setTranslateTextController(controller);
        return this;
    }

    /**
     * Sets the imageUploadDAO to be used in this application.
     * @param imageUploadDataAccess the DAO to use
     * @return this builder
     */
    public TranslateTextAppBuilder addImageUploadDAO(ImageUploadDataAccessInterface imageUploadDataAccess) {
        this.imageUploadDAO = imageUploadDataAccess;
        return this;
    }

    /**
     * Creates the objects  for the Image Upload Use Case and connects the TranslateTextView to its
     * controller. Image Upload uses the same view as TranslateTextView.
     * <p>This method must be called after addTranslateTextView!</p>
     * @return this builder
     * @throws RuntimeException if this method is called before addTranslateTextView
     */
    public TranslateTextAppBuilder addImageUploadUseCase() {
        final ImageUploadOutputBoundary imageUploadOutputBoundary =
                new ImageUploadPresenter(translateTextViewModel);

        final ImageUploadInteractor imageUploadInteractor = new ImageUploadInteractor(imageUploadDAO,
                imageUploadOutputBoundary);

        final ImageUploadController controller = new ImageUploadController(imageUploadInteractor);
        if (translateTextView == null) {
            throw new RuntimeException("addTranslateTextView must be called before addImageUploadUseCase");
        }
        translateTextView.setImageUploadController(controller);
        return this;
    }

    /**
     * Sets the switchTranslationDAO to be used in this application.
     * @param switchTranslationDataAccess the DAO to use
     * @return this builder
     */
    public TranslateTextAppBuilder addSwitchTranslationDAO(
            SwitchTranslationDataAccessInterface switchTranslationDataAccess) {
        switchTranslationDAO = switchTranslationDataAccess;
        return this;
    }

    /**
     * Creates the objects for the Switch Translation Use Case and connects the TranslateTextView to its
     * controller. Uses the same view as TranslateTextView.
     * <p>This method must be called after addTranslateTextView!</p>
     * @return this builder
     * @throws RuntimeException if this method is called before addTranslateTextView
     */
    public TranslateTextAppBuilder addSwitchTranslationUseCase() {
        final SwitchTranslationOutputBoundary switchTranslationOutputBoundary =
                new SwitchTranslationPresenter(translateTextViewModel);

        final SwitchTranslationInteractor switchTranslationInteractor = new SwitchTranslationInteractor(
                switchTranslationDAO, switchTranslationOutputBoundary);

        final SwitchTranslationController controller = new SwitchTranslationController(switchTranslationInteractor);
        if (translateTextView == null) {
            throw new RuntimeException("addTranslateTextView must be called before addSwitchTranslationUseCase");
        }
        translateTextView.setSwitchTranslationController(controller);
        return this;
    }

    /**
     * Sets the TextToSpeechDAO to be used in this application.
     * @param textToSpeechDataAccess the DAO to use
     * @return this builder
     */
    public TranslateTextAppBuilder addTextToSpeechDAO(TextToSpeechDataAccessInterface textToSpeechDataAccess) {
        textToSpeechDAO = textToSpeechDataAccess;
        return this;
    }

    /**
     * Creates the objects for Text to Speech Use Case and connects the TranslateTextView to its
     * controller. Uses the same view as TranslateTextView.
     * <p>This method must be called after addTranslateTextView!</p>
     * @return this builder
     * @throws RuntimeException if this method is called before addTranslateTextView
     */
    public TranslateTextAppBuilder addTextToSpeechUseCase() {
        final TextToSpeechOutputBoundary textToSpeechOutputBoundary = new TextToSpeechPresenter(translateTextViewModel);

        final TextToSpeechInteractor textToSpeechInteractor = new TextToSpeechInteractor(
                (TextToSpeechDataAccessInterface) translateTextDAO,
                textToSpeechOutputBoundary);

        final TextToSpeechController controller = new TextToSpeechController(textToSpeechInteractor);
        if (translateTextView == null) {
            throw new RuntimeException("addTranslateTextView must be called before addTextToSpeechUseCase");
        }
        translateTextView.setTextToSpeechController(controller);
        return this;
    }

    /**
     * Creates the TranslateTextView and underlying TranslateTextViewModel.
     * @return this builder
     */
    public TranslateTextAppBuilder addTranslateTextView() {
        translateTextViewModel = new TranslateTextViewModel();

        final TranslateTextViewBuilder builder = new TranslateTextViewBuilder();

        translateTextView = builder
                .addViewModel(translateTextViewModel)
                .addDAO(translateTextDAO)
                .addLanguages()
                .addInputPanel()
                .addOutputPanel()
                .addButtonPanel()
                .build();

        return this;
    }

    /**
     * Builds the translator application.
     * @return the JFrame for the application
     */
    public JFrame build() {
        final JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setTitle("Translator Application");
        frame.setSize(WIDTH, HEIGHT);

        frame.add(translateTextView);

        return frame;

    }
}
