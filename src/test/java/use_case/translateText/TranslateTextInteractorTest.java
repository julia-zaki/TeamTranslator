package use_case.translateText;

import data_access.DBTranslateTextDataAccessObject;
import entity.TextTranslator;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class TranslateTextInteractorTest {

    @Test
    public void ExecuteSuccessTest() {

        TranslateTextInputData inputData = new TranslateTextInputData("English", "Hello", "French");
        TextTranslator textTranslator = new TextTranslator();

        TranslateTextDataAccessInterface translateTextDAO = new DBTranslateTextDataAccessObject();

        use_case.translateText.TranslateTextOutputBoundary translateTextOB = new use_case.translateText.TranslateTextOutputBoundary() {
            @Override
            public void prepareSuccessView(use_case.translateText.TranslateTextOutputData translateTextOutputData) {
                assertEquals("Bonjour", translateTextOutputData.getOutputText());
                assertEquals("English", translateTextOutputData.getInputLanguage());
            }

            @Override
            public void prepareFailView(String errorMessage) {
                fail("Use case failure is unexpected.");
            }
        };

        TranslateTextInputBoundary translateTextInteractor = new use_case.translateText.TranslateTextInteractor
                (translateTextDAO, translateTextOB, textTranslator);

        translateTextInteractor.execute(inputData);

    }

    @Test
    public void failureLanguageDoesNotExistTest() {
        TranslateTextInputData inputData = new TranslateTextInputData("unknown", "Hello", "French");
        TextTranslator textTranslator = new TextTranslator();

        TranslateTextDataAccessInterface translateTextDAO = new DBTranslateTextDataAccessObject();

        use_case.translateText.TranslateTextOutputBoundary translateTextOB = new use_case.translateText.TranslateTextOutputBoundary() {
            @Override
            public void prepareSuccessView(use_case.translateText.TranslateTextOutputData translateTextOutputData) {
                fail("Use case success is unexpected.");
            }

            @Override
            public void prepareFailView(String errorMessage) {
                assertEquals("Selected language does not exist in translator.", errorMessage);
            }
        };

        TranslateTextInputBoundary translateTextInteractor = new use_case.translateText.TranslateTextInteractor
                (translateTextDAO, translateTextOB, textTranslator);

        translateTextInteractor.execute(inputData);
    }

}