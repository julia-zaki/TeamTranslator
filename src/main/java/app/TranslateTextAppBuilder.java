package app;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import entity.TextTranslator;
import interface_adapter.imageUpload.ImageUploadController;
import interface_adapter.imageUpload.ImageUploadPresenter;
import interface_adapter.translateText.TranslateTextController;
import interface_adapter.translateText.TranslateTextPresenter;
import interface_adapter.translateText.TranslateTextViewModel;
import use_case.imageUpload.ImageUploadDataAccessInterface;
import use_case.imageUpload.ImageUploadInteractor;
import use_case.imageUpload.ImageUploadOutputBoundary;
import use_case.translateText.TranslateTextDataAccessInterface;
import use_case.translateText.TranslateTextInteractor;
import use_case.translateText.TranslateTextOutputBoundary;
import view.TranslateTextView;

/**
 * Builder for the Translator Application.
 */
public class TranslateTextAppBuilder {
    public static final int HEIGHT = 500;
    public static final int WIDTH = 800;
    private TranslateTextDataAccessInterface translateTextDAO;
    private ImageUploadDataAccessInterface imageUploadDAO;
    private TranslateTextViewModel translateTextViewModel = new TranslateTextViewModel();
    private TranslateTextView translateTextView;
    private TranslateTextInteractor translateTextInteractor;
    private ImageUploadInteractor imageUploadInteractor;

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
     * Creates the TranslateTextView and underlying TranslateTextViewModel.
     * @return this builder
     */
    public TranslateTextAppBuilder addTranslateTextView() {
        translateTextViewModel = new TranslateTextViewModel();
        translateTextView = new TranslateTextView(translateTextViewModel, translateTextDAO);
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
