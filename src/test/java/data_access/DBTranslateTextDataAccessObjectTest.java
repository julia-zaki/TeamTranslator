package data_access;

import org.junit.Test;
import use_case.translateText.DataAccessException;

import java.util.Map;

public class DBTranslateTextDataAccessObjectTest {
    DBTranslateTextDataAccessObject translateTextDAO = new DBTranslateTextDataAccessObject();
    @Test
    public void testTranslateText() throws DataAccessException {
        Map<String, String> translationResult = translateTextDAO
                .translateText("Hello World", null, "French");
        assert translationResult.get(DBTranslateTextDataAccessObject.LANGUAGE_KEY).equals("English");
        assert translationResult.get(DBTranslateTextDataAccessObject.TEXT_KEY).equals("Bonjour le monde");
    }
}
