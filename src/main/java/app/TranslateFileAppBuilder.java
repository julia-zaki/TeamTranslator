package app;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import entity.FileTranslator;
import interface_adapter.translateFile.TranslateFileController;
import interface_adapter.translateFile.TranslateFilePresenter;
import interface_adapter.translateFile.TranslateFileViewModel;
import use_case.translateFile.TranslateFileDataAccessInterface;
import use_case.translateFile.TranslateFileInteractor;
import use_case.translateFile.TranslateFileOutputBoundary;
import view.TranslateFileView;

/**
 * Builder for the File Translator Application.
 */

public class TranslateFileAppBuilder {
    public static final int HEIGHT = 500;
    public static final int WIDTH = 800;
    private TranslateFileDataAccessInterface translateFileDAO;
    private TranslateFileViewModel translateFileViewModel = new TranslateFileViewModel();
    private TranslateFileView translateFileView;
    private TranslateFileInteractor translateFileInteractor;

    /**
     * Sets the translateFileDAO to be used in this application.
     * @param translateFileDataAccess the DAO to use
     * @return this builder
     */
    public TranslateFileAppBuilder addTranslateFileDAO(TranslateFileDataAccessInterface translateFileDataAccess) {
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
    public TranslateFileAppBuilder addTranslateFileUseCase() {
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
     * Creates the TranslateFileView and underlying TranslateFileViewModel.
     * @return this builder
     */
    public TranslateFileAppBuilder addTranslateFileView() {
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
        frame.setTitle("Translator Application - File Translation");
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setSize(WIDTH, HEIGHT);
        frame.add(translateFileView);

        return frame;
    }
}
