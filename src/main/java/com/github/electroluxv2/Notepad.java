package com.github.electroluxv2;

import com.github.electroluxv2.components.FileView;
import com.github.electroluxv2.components.TopMenu;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Notepad extends Application {
    @Override
    public void start(final Stage stage) {
        stage.setTitle("Notepad");

        final var topMenu = new TopMenu();
        final var fileViewContainer = new TabPane(new FileView("test.txt"), new FileView("amonhus.exe"));
        final var infoLabel = new Label("Open file using top menu");
        VBox.setVgrow(fileViewContainer, Priority.ALWAYS);

        var scene = new Scene(new VBox(topMenu, fileViewContainer, infoLabel), 1280, 800);
        scene.getStylesheets().add("dark.css");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
