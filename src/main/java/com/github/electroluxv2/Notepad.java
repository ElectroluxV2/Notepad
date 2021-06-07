package com.github.electroluxv2;

import com.github.electroluxv2.components.FileView;
import com.github.electroluxv2.components.TopMenu;
import com.github.electroluxv2.utils.DarkMode;
import com.github.electroluxv2.utils.EditorProperties;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class Notepad extends Application {
    final TopMenu topMenu;
    final TabPane fileViewContainer;

    public Notepad() throws IOException {
        topMenu = new TopMenu();
        fileViewContainer = new TabPane(new FileView("test.txt"), new FileView("amonhus.exe"));
    }


    @Override
    public void start(final Stage stage) throws IOException {
        stage.setTitle("Notepad");



        final var infoLabel = new Label("Open file using top menu");
        VBox.setVgrow(fileViewContainer, Priority.ALWAYS);

        var scene = new Scene(new VBox(topMenu, fileViewContainer, infoLabel), 1280, 800);

        // User choice
        topMenu.viewMenuDarkMode.setSelected(EditorProperties.getBoolean("darkModeEnabled"));
        DarkMode.change(scene, topMenu.viewMenuDarkMode.isSelected());

        // Respond to theme change
        topMenu.viewMenuDarkMode.setOnAction(actionEvent -> {
            try {
                DarkMode.change(scene, topMenu.viewMenuDarkMode.isSelected());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
