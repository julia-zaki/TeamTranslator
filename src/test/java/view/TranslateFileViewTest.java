package view;

import data_access.DBTranslateFileDataAccessObject;
import entity.FileTranslator;
import interface_adapter.translateFile.TranslateFileController;
import interface_adapter.translateFile.TranslateFilePresenter;
import interface_adapter.translateFile.TranslateFileViewModel;
import use_case.translateFile.TranslateFileDataAccessInterface;
import use_case.translateFile.TranslateFileInteractor;


import javax.swing.*;
import java.awt.*;


public class TranslateFileViewTest {
    public static void main(String[] args) {
        TranslateFileViewModel model = new TranslateFileViewModel();
        TranslateFileView view = new TranslateFileView(model);
        TranslateFileController controller = new TranslateFileController(new TranslateFileInteractor(
                new DBTranslateFileDataAccessObject(), new TranslateFilePresenter(model),
                new FileTranslator(new DBTranslateFileDataAccessObject())));
        view.setTranslateFileController(controller);
        final JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setTitle("File Translator");
        frame.setSize(800, 600);

        frame.add(view);
        frame.setVisible(true);
    }

}
