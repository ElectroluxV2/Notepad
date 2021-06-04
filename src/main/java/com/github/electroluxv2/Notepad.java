package com.github.electroluxv2;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Notepad extends Application {
    @Override
    public void start(final Stage stage) {
        stage.setTitle("Notepad");
        final var menu = new MenuBar();
        final var fileMenu = new Menu("File");
        menu.getMenus().add(fileMenu);
        var scene = new Scene(new VBox(menu), 1280, 800);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
