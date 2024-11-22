package data_access;

import org.junit.Test;
import use_case.translateText.DataAccessException;

import java.io.File;

import static org.junit.Assert.assertEquals;

public class DBImageUploadDataAccessObjectTest {
    DBImageUploadDataAccessObject imageUploadDAO = new DBImageUploadDataAccessObject();
    @Test
    public void testTranslateText() throws DataAccessException {
        String text = imageUploadDAO.getText(new File("Images/imageInFrench.png"));
        assertEquals("je mange un sandwich\r\n" +
                "il adore son chien\r\n" +
                "nous apprenons le franqais\r\n" +
                "le chat mange le poisson\r\n" +
                "tu bois un coca-cola\r\n", text);
    }

}
