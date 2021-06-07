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
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.IOException;

public class Notepad extends Application {
    final TopMenu topMenu;
    final TabPane fileViewContainer;
    final Label infoLabel;
    final Scene scene;

    public Notepad() throws IOException {
        // Create components
        topMenu = new TopMenu();
        fileViewContainer = new TabPane();
        VBox.setVgrow(fileViewContainer, Priority.ALWAYS);
        infoLabel = new Label("Open file using top menu");
        scene = new Scene(new VBox(topMenu, fileViewContainer, infoLabel), 1280, 800);

    }


    @Override
    public void start(final Stage stage) throws IOException {
        stage.setTitle("Notepad");
        stage.setScene(scene);
        stage.show();

        loadUserChoices();
        respondToMenu();
    }

    private void loadUserChoices() throws IOException {
        // User choices
        topMenu.viewDarkModeEnabled.setSelected(EditorProperties.getBoolean("darkModeEnabled"));
        DarkMode.change(scene, topMenu.viewDarkModeEnabled.isSelected());
    }

    private void respondToMenu() {
        // Respond to file open
        topMenu.fileOpen.onAction(this::onOpenFile);

        // Respond to theme change
        topMenu.viewDarkModeEnabled.setOnAction(actionEvent -> {
            try {
                DarkMode.change(scene, topMenu.viewDarkModeEnabled.isSelected());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private Void onOpenFile() {
        final var fileChooser = new FileChooser();
        fileChooser.setTitle("Open text file");
        final var selected = fileChooser.showOpenMultipleDialog(scene.getWindow());

        for (final var file : selected) {
            fileViewContainer.getTabs().add(new FileView(file));
        }

        return null;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
