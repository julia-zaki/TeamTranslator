package data_access;

import org.junit.Test;
import use_case.note.DataAccessException;

public class DBTranslateTextDataAccessObjectTest {
    DBTranslateTextDataAccessObject translateTextDAO = new DBTranslateTextDataAccessObject();
    @Test
    public void testTranslateText() throws DataAccessException {
        String actual = translateTextDAO.translateText("Hello World", null, "fr");
        String expected = "Bonjour le monde";
        assert actual.equals(expected);
    }
}
