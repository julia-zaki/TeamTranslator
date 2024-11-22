package use_case.imageUpload;

/**
 * Input Boundary for actions which are related to uploading images.
 */
public interface ImageUploadInputBoundary {

    /**
     * Executes the Image Upload Use Case.
     * @param imageUploadInputData the input data.
     */
    void execute(ImageUploadInputData imageUploadInputData);

}
