package interface_adapter.imageUpload;

import java.io.File;

import use_case.imageUpload.ImageUploadInputBoundary;
import use_case.imageUpload.ImageUploadInputData;

/**
 * The controller for the ImageUpload Use Case.
 */
public class ImageUploadController {

    private final ImageUploadInputBoundary imageUploadUseCaseInteractor;

    public ImageUploadController(ImageUploadInputBoundary imageUploadUseCaseInteractor) {
        this.imageUploadUseCaseInteractor = imageUploadUseCaseInteractor;
    }

    /**
     * Executes the ImageUpload Use Case.
     * @param imageFile the file of the image
     */
    public void execute(File imageFile) {
        final ImageUploadInputData imageUploadInputData = new ImageUploadInputData(imageFile);

        imageUploadUseCaseInteractor.execute(imageUploadInputData);
    }
}
