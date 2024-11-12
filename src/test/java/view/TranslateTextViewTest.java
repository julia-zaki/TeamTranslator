package view;

import data_access.DBTranslateTextDataAccessObject;
import entity.TextTranslator;
import interface_adapter.note.NoteViewModel;
import interface_adapter.translateText.TranslateTextController;
import interface_adapter.translateText.TranslateTextPresenter;
import interface_adapter.translateText.TranslateTextViewModel;
import use_case.translateText.TranslateTextInteractor;

import javax.swing.*;

public class TranslateTextViewTest {
    public static void main(String[] args) {
        TranslateTextViewModel model = new TranslateTextViewModel();
        TranslateTextView view = new TranslateTextView(model);
        TranslateTextController controller = new TranslateTextController(new TranslateTextInteractor(
                new DBTranslateTextDataAccessObject(), new TranslateTextPresenter(model),
                new TextTranslator()));
        view.setTranslateTextController(controller);
        final JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setTitle("Translator Application");
        frame.setSize(400, 400);

        frame.add(view);
        frame.setVisible(true);
    }
}
