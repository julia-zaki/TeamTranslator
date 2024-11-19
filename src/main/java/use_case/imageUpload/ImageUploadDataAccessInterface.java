package use_case.imageUpload;

import java.io.File;

import use_case.translateText.DataAccessException;

/**
 * Interface for the Image Upload DAO. It consists of methods for
 * extracting text from an image file.
 */
public interface ImageUploadDataAccessInterface {

    /**
     * Extract the text from an image file.
     * @param imageFile the file of the image
     * @return the text from the image
     * @throws DataAccessException if the text could not be extracted for any reason
     */
    String getText(File imageFile) throws DataAccessException;

}
