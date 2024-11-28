package app;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import entity.FileTranslator;
import entity.TextTranslator;
import interface_adapter.imageUpload.ImageUploadController;
import interface_adapter.imageUpload.ImageUploadPresenter;
import interface_adapter.switchTranslation.SwitchTranslationController;
import interface_adapter.switchTranslation.SwitchTranslationPresenter;
import interface_adapter.translateFile.TranslateFileController;
import interface_adapter.translateFile.TranslateFilePresenter;
import interface_adapter.translateFile.TranslateFileViewModel;
import interface_adapter.translateText.TranslateTextController;
import interface_adapter.translateText.TranslateTextPresenter;
import interface_adapter.translateText.TranslateTextViewModel;
import use_case.imageUpload.ImageUploadDataAccessInterface;
import use_case.imageUpload.ImageUploadInteractor;
import use_case.imageUpload.ImageUploadOutputBoundary;
import use_case.switchTranslation.SwitchTranslationDataAccessInterface;
import use_case.switchTranslation.SwitchTranslationInteractor;
import use_case.switchTranslation.SwitchTranslationOutputBoundary;
import use_case.translateFile.TranslateFileDataAccessInterface;
import use_case.translateFile.TranslateFileInteractor;
import use_case.translateFile.TranslateFileOutputBoundary;
import use_case.translateText.TranslateTextDataAccessInterface;
import use_case.translateText.TranslateTextInteractor;
import use_case.translateText.TranslateTextOutputBoundary;
import view.TranslateFileView;
import view.TranslateTextView;

/**
 * Builder for the Translator Application.
 */
public class TranslateTextAppBuilder {
    public static final int HEIGHT = 500;
    public static final int WIDTH = 800;
    private TranslateFileDataAccessInterface translateFileDAO;
    private TranslateTextDataAccessInterface translateTextDAO;
    private ImageUploadDataAccessInterface imageUploadDAO;
    private SwitchTranslationDataAccessInterface switchTranslationDAO;
    private TranslateFileViewModel translateFileViewModel = new TranslateFileViewModel();
    private TranslateTextViewModel translateTextViewModel = new TranslateTextViewModel();
    private TranslateFileView translateFileView;
    private TranslateTextView translateTextView;
    private TranslateFileInteractor translateFileInteractor;
    private TranslateTextInteractor translateTextInteractor;
    private ImageUploadInteractor imageUploadInteractor;
    private SwitchTranslationInteractor switchTranslationInteractor;

    /**
     * Sets the translateFileDAO to be used in this application.
     * @param translateFileDataAccess the DAO to use
     * @return this builder
     */
    public TranslateTextAppBuilder addTranslateFileDAO(TranslateFileDataAccessInterface translateFileDataAccess) {
        translateFileDAO = translateFileDataAccess;
        return this;
    }

    /**
     * Creates the objects for the TranslateFile Use Case and connects the TranslateFileView to its
     * controller.
     * <p>This method must be called after addTranslateFileView!</p>
     * @return this builder
     * @throws RuntimeException if this method is called before addTranslateFileView
     */
    public TranslateTextAppBuilder addTranslateFileUseCase() {
        final TranslateFileOutputBoundary translateFileOutputBoundary =
                new TranslateFilePresenter(translateFileViewModel);
        final FileTranslator fileTranslator = new FileTranslator(translateFileDAO);

        translateFileInteractor = new TranslateFileInteractor(translateFileDAO, translateFileOutputBoundary,
                fileTranslator);

        final TranslateFileController controller = new TranslateFileController(translateFileInteractor);
        if (translateFileView == null) {
            throw new RuntimeException("addTranslateFileView must be called before addTranslateFileUseCase");
        }
        translateFileView.setTranslateFileController(controller);
        return this;
    }

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
        final TextTranslator textTranslator = new TextTranslator();

        translateTextInteractor = new TranslateTextInteractor(translateTextDAO, translateTextOutputBoundary,
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

        imageUploadInteractor = new ImageUploadInteractor(imageUploadDAO, imageUploadOutputBoundary);

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

        switchTranslationInteractor = new SwitchTranslationInteractor(switchTranslationDAO,
                switchTranslationOutputBoundary);

        final SwitchTranslationController controller = new SwitchTranslationController(switchTranslationInteractor);
        if (translateTextView == null) {
            throw new RuntimeException("addTranslateTextView must be called before addSwitchTranslationUseCase");
        }
        translateTextView.setSwitchTranslationController(controller);
        return this;
    }

    /**
     * Creates the TranslateTextView and underlying TranslateTextViewModel.
     * @return this builder
     */
    public TranslateTextAppBuilder addTranslateTextView() {
        translateTextViewModel = new TranslateTextViewModel();
        translateTextView = new TranslateTextView(translateTextViewModel, translateTextDAO);
        return this;
    }

    /**
     * Creates the TranslateFileView and underlying TranslateFileViewModel.
     * @return this builder
     */
    public TranslateTextAppBuilder addTranslateFileView() {
        translateFileViewModel = new TranslateFileViewModel();
        translateFileView = new TranslateFileView(translateFileViewModel);
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
