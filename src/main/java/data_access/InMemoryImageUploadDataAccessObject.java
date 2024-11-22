package data_access;

import java.io.File;

import use_case.imageUpload.ImageUploadDataAccessInterface;
import use_case.translateText.DataAccessException;

/**
 * In memory data access object for Image Upload Use Case.
 */
public class InMemoryImageUploadDataAccessObject implements ImageUploadDataAccessInterface {
    /**
     * Extract the text from an image file.
     *
     * @param imageFile the file of the image
     * @return the text from the image
     * @throws DataAccessException if the text could not be extracted for any reason
     */
    @Override
    public String getText(File imageFile) throws DataAccessException {
        return "";
    }
}
