package use_case.imageUpload;

import data_access.InMemoryImageUploadDataAccessObject;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class ImageUploadInteractorTest {

    // Mock DAO by creating in-memory DAO for Image Upload and overriding getText
    ImageUploadDataAccessInterface imageUploadDAO = new InMemoryImageUploadDataAccessObject() {

        @Override
        public String getText(File imageFile) {
            return "test";
        }
    };

    @Test
    public void successTest() {

        // Mock input data to be a valid file from Images folder
        ImageUploadInputData imageUploadInputData = new ImageUploadInputData(new File("Images/imageInFrench.png"));

        // Create a presenter to test whether the output is expected
        ImageUploadOutputBoundary imageUploadOB = new ImageUploadOutputBoundary() {

            /**
             * Prepares the success view for the ImageUpload related Use Cases.
             *
             * @param imageUploadOutputData the output data
             */
            @Override
            public void prepareSuccessView(ImageUploadOutputData imageUploadOutputData) {
                assertEquals("test", imageUploadOutputData.getInputText());
            }

            /**
             * Prepares the failure view for the ImageUpload related Use Cases.
             *
             * @param errorMessage the explanation of the failure
             */
            @Override
            public void prepareFailView(String errorMessage) {
                fail("Use case failure is unexpected");
            }
        };

        ImageUploadInteractor imageUploadInteractor = new ImageUploadInteractor(imageUploadDAO, imageUploadOB);

        imageUploadInteractor.execute(imageUploadInputData);
    }

    @Test
    public void failureFileDoesNoteExistTest() {

        // Mock input data to be a file that does not exist
        ImageUploadInputData imageUploadInputData = new ImageUploadInputData(new File("notAValidFile"));

        // Create a presenter to test whether the output is expected
        ImageUploadOutputBoundary imageUploadOB = new ImageUploadOutputBoundary() {

            /**
             * Prepares the success view for the ImageUpload related Use Cases.
             *
             * @param imageUploadOutputData the output data
             */
            @Override
            public void prepareSuccessView(ImageUploadOutputData imageUploadOutputData) {
                fail("Use case success is unexpected");
            }

            /**
             * Prepares the failure view for the ImageUpload related Use Cases.
             *
             * @param errorMessage the explanation of the failure
             */
            @Override
            public void prepareFailView(String errorMessage) {
                assertEquals("The file does not exist.", errorMessage);
            }
        };

        ImageUploadInteractor imageUploadInteractor = new ImageUploadInteractor(imageUploadDAO, imageUploadOB);

        imageUploadInteractor.execute(imageUploadInputData);

    }

    @Test
    public void failureNoTextInImageTest() {

        // Mock DAO's getText method to return the empty string
        imageUploadDAO = new InMemoryImageUploadDataAccessObject() {
            @Override
            public String getText(File imageFile) {
                return "";
            }
        };

        ImageUploadOutputBoundary imageUploadOB = new ImageUploadOutputBoundary() {

            /**
             * Prepares the success view for the ImageUpload related Use Cases.
             *
             * @param imageUploadOutputData the output data
             */
            @Override
            public void prepareSuccessView(ImageUploadOutputData imageUploadOutputData) {
                fail("Use case success is unexpected");
            }

            /**
             * Prepares the failure view for the ImageUpload related Use Cases.
             *
             * @param errorMessage the explanation of the failure
             */
            @Override
            public void prepareFailView(String errorMessage) {
                assertEquals("No text in file found.", errorMessage);
            }
        };
    }
}
