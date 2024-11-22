package use_case.imageUpload;

/**
 * The output boundary  for the Image Upload Use Case.
 */
public interface ImageUploadOutputBoundary {
    /**
     * Prepares the success view for the ImageUpload related Use Cases.
     * @param imageUploadOutputData the output data
     */
    void prepareSuccessView(ImageUploadOutputData imageUploadOutputData);

    /**
     * Prepares the failure view for the ImageUpload related Use Cases.
     * @param errorMessage the explanation of the failure
     */
    void prepareFailView(String errorMessage);
}
