package data_access;

import org.junit.Test;
import use_case.translateText.DataAccessException;

import java.util.Map;

public class DBTranslateTextDataAccessObjectTest {
    DBTranslateTextDataAccessObject translateTextDAO = new DBTranslateTextDataAccessObject();
    @Test
    public void testTranslateText() throws DataAccessException {
        Map<String, String> translationResult = translateTextDAO
                .translateText("Hello World", "Detect Language", "French");
        assert translationResult.get(Constants.LANGUAGE_KEY).equals("English");
        assert translationResult.get(Constants.TEXT_KEY).equals("Bonjour le monde");
    }
}
