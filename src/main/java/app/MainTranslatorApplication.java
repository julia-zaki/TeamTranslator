package app;

import data_access.DBImageUploadDataAccessObject;
import data_access.DBTranslateTextDataAccessObject;
import use_case.imageUpload.ImageUploadDataAccessInterface;
import use_case.translateText.TranslateTextDataAccessInterface;

/**
 * An application where we can text.
 */
public class MainTranslatorApplication {

    /**
     * The main entry point of the application.
     * The program will show you the input and output language drop-down box and will allow you
     * to type text in a textbox. You can then click Translate Text and your text will be translated.
     * This uses the DeepL API.
     * @param args commandline arguments are ignored
     */
    public static void main(String[] args) {

        // create the data access and inject it into our builder!
        final TranslateTextDataAccessInterface translateTextDataAccess = new DBTranslateTextDataAccessObject();
        final ImageUploadDataAccessInterface imageUploadDataAccess = new DBImageUploadDataAccessObject();

        final app.TranslateTextAppBuilder builder = new app.TranslateTextAppBuilder();
        builder.addTranslateTextDAO(translateTextDataAccess)
                .addImageUploadDAO(imageUploadDataAccess)
                .addTranslateTextView()
                .addTranslateTextUseCase()
                .addImageUploadUseCase().build().setVisible(true);
    }
}
