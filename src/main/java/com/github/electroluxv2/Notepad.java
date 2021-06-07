package com.github.electroluxv2;

import com.github.electroluxv2.components.FileView;
import com.github.electroluxv2.components.TopMenu;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Notepad extends Application {
    @Override
    public void start(final Stage stage) {
        stage.setTitle("Notepad");

        final var topMenu = new TopMenu();
        final var fileViewContainer = new TabPane(new FileView("test.txt"));

        var scene = new Scene(new VBox(topMenu, fileViewContainer), 1280, 800);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
