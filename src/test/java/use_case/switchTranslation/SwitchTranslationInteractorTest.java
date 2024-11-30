package use_case.switchTranslation;

import data_access.DBTranslateTextDataAccessObject;
import org.junit.Test;
import use_case.translateText.DataAccessException;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

// the interactor tests.
public class SwitchTranslationInteractorTest {
    @Test
    public void ExecuteSuccessEnglishAsInputTest() {

        SwitchTranslationInputData inputData = new SwitchTranslationInputData("Hello", "English", "French");

        SwitchTranslationDataAccessInterface switchTranslationDAO = new DBTranslateTextDataAccessObject();

        SwitchTranslationOutputBoundary switchTranslationOB =
                new SwitchTranslationOutputBoundary() {
            @Override
            public void prepareSuccessView(SwitchTranslationOutputData switchTranslationOutputData) {
                assertEquals("Bonjour", switchTranslationOutputData.getInputText());
                assertEquals("French", switchTranslationOutputData.getInputLanguage());
                assertEquals("Hello", switchTranslationOutputData.getTranslatedText());
                assertEquals("English (American)", switchTranslationOutputData.getOutputLanguage());
            }

            @Override
            public void prepareFailView(String errorMessage) {
                fail("Use case failure is unexpected.");
            }
        };

        SwitchTranslationInputBoundary switchTranslationInteractor = new
                SwitchTranslationInteractor(switchTranslationDAO, switchTranslationOB);

        switchTranslationInteractor.execute(inputData);

    }

    @Test
    public void ExecuteSuccessEnglishAsOutputTest() {

        SwitchTranslationInputData inputData = new SwitchTranslationInputData("Bonjour", "French", "English (British)");

        SwitchTranslationDataAccessInterface switchTranslationDAO = new DBTranslateTextDataAccessObject();

        SwitchTranslationOutputBoundary switchTranslationOB =
                new SwitchTranslationOutputBoundary() {
                    @Override
                    public void prepareSuccessView(SwitchTranslationOutputData switchTranslationOutputData) {
                        assertEquals("Hello", switchTranslationOutputData.getInputText());
                        assertEquals("English", switchTranslationOutputData.getInputLanguage());
                        assertEquals("Bonjour", switchTranslationOutputData.getTranslatedText());
                        assertEquals("French", switchTranslationOutputData.getOutputLanguage());
                    }

                    @Override
                    public void prepareFailView(String errorMessage) {
                        fail("Use case failure is unexpected.");
                    }
                };

        SwitchTranslationInputBoundary switchTranslationInteractor = new
                SwitchTranslationInteractor(switchTranslationDAO, switchTranslationOB);

        switchTranslationInteractor.execute(inputData);

    }

    @Test
    public void failureInputLanguageDoesNotExistTest() {
        SwitchTranslationInputData inputData = new SwitchTranslationInputData("Apple", "Detect Language", "French");

        SwitchTranslationDataAccessInterface switchTranslationDAO = new DBTranslateTextDataAccessObject();

        use_case.switchTranslation.SwitchTranslationOutputBoundary switchTranslationOB = new use_case.switchTranslation.SwitchTranslationOutputBoundary() {
            @Override
            public void prepareSuccessView(use_case.switchTranslation.SwitchTranslationOutputData switchTranslationOutputData) {
                fail("Use case success is unexpected.");
            }

            @Override
            public void prepareFailView(String errorMessage) {
                assertEquals("Please select an input language.", errorMessage);
            }
        };

        SwitchTranslationInputBoundary switchTranslationInteractor = new use_case.switchTranslation.SwitchTranslationInteractor
                (switchTranslationDAO, switchTranslationOB);

        switchTranslationInteractor.execute(inputData);
    }

    @Test
    public void failureInputTextDoesNotExistTest() {
        SwitchTranslationInputData inputData = new SwitchTranslationInputData("", "English", "French");

        SwitchTranslationDataAccessInterface switchTranslationDAO = new DBTranslateTextDataAccessObject();

        use_case.switchTranslation.SwitchTranslationOutputBoundary switchTranslationOB = new use_case.switchTranslation.SwitchTranslationOutputBoundary() {
            @Override
            public void prepareSuccessView(use_case.switchTranslation.SwitchTranslationOutputData switchTranslationOutputData) {
                fail("Use case success is unexpected.");
            }

            @Override
            public void prepareFailView(String errorMessage) {
                assertEquals("Input text cannot be empty. Please provide "
                        + "text to translate.", errorMessage);
            }
        };

        SwitchTranslationInputBoundary switchTranslationInteractor = new use_case.switchTranslation.SwitchTranslationInteractor
                (switchTranslationDAO, switchTranslationOB);

        switchTranslationInteractor.execute(inputData);
    }

    @Test
    public void failureDataAccessExceptionTest() {
        // Create input data
        SwitchTranslationInputData inputData = new SwitchTranslationInputData("Test text", "English", "French");

        // Mocked Data Access Object that throws DataAccessException
        SwitchTranslationDataAccessInterface switchTranslationDAO = new SwitchTranslationDataAccessInterface() {
            @Override
            public Map<String, String> switchLanguagesAndTexts(String inputText, String inputLanguage, String outputLanguage) throws DataAccessException {
                throw new DataAccessException("Simulated data access error");
            }
        };

        // Output Boundary to capture failure messages
        SwitchTranslationOutputBoundary switchTranslationOB = new SwitchTranslationOutputBoundary() {
            @Override
            public void prepareSuccessView(SwitchTranslationOutputData switchTranslationOutputData) {
                fail("Use case success is unexpected.");
            }

            @Override
            public void prepareFailView(String errorMessage) {
                assertEquals("There seems to be an error with the switching process. Please try again.", errorMessage);
            }
        };

        // Create and execute the interactor
        SwitchTranslationInputBoundary switchTranslationInteractor = new SwitchTranslationInteractor(switchTranslationDAO, switchTranslationOB);

        switchTranslationInteractor.execute(inputData);
    }

}