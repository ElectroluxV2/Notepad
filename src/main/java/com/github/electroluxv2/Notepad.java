package com.github.electroluxv2;

import com.github.electroluxv2.components.FileView;
import com.github.electroluxv2.components.ModdedTabPane;
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
    final ModdedTabPane fileViewContainer;
    final Label infoLabel;
    final Scene scene;

    public Notepad() throws IOException {
        // Create components
        topMenu = new TopMenu();
        fileViewContainer = new ModdedTabPane();
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
        topMenu.viewDarkEnabled.setSelected(EditorProperties.getBoolean("darkModeEnabled"));
        DarkMode.change(scene, topMenu.viewDarkEnabled.isSelected());
    }

    private void respondToMenu() {
        // Respond to file Section
        topMenu.fileOpen.onAction(this::onOpenFile);

        // Respond to view Section
        topMenu.viewDisableEdit.onAction(this::setDisableOnEachTextArea);
        topMenu.viewDisableWrapLines.onAction(this::setTextWrapOnEachTextArea);
        topMenu.viewDarkEnabled.onAction(() -> DarkMode.change(scene, topMenu.viewDarkEnabled.isSelected()));
    }

    private Void setTextWrapOnEachTextArea() {
        for (final var view : fileViewContainer.getViews()) {
            view.getTextArea().setWrapText(topMenu.viewDisableWrapLines.isSelected());
        }
        return null;
    }

    private Void setDisableOnEachTextArea() {
        for (final var view : fileViewContainer.getViews()) {
            view.getTextArea().setDisable(topMenu.viewDisableEdit.isSelected());
        }
        return null;
    }

    private Void onOpenFile() {
        final var fileChooser = new FileChooser();
        fileChooser.setTitle("Open text files");
        final var selected = fileChooser.showOpenMultipleDialog(scene.getWindow());

        for (final var file : selected) {
            final var view = new FileView(file);
            view.getTextArea().setDisable(topMenu.viewDisableEdit.isSelected());
            view.getTextArea().setWrapText(topMenu.viewDisableWrapLines.isSelected());
            fileViewContainer.addView(view);
        }

        return null;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
