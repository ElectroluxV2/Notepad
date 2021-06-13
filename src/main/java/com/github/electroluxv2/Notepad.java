package com.github.electroluxv2;

import com.github.electroluxv2.components.*;
import com.github.electroluxv2.utils.Crypt;
import com.github.electroluxv2.utils.DarkMode;
import com.github.electroluxv2.utils.EditorProperties;
import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.crypto.SecretKey;
import java.io.IOException;

public class Notepad extends Application {
    final TopMenu topMenu = new TopMenu();
    final ModdedTabPane fileViewContainer = new ModdedTabPane();
    final Label infoLabel = new Label("Open file using top menu");
    final Scene scene = new Scene(new VBox(topMenu, fileViewContainer, infoLabel), 1280, 800);

    public Notepad() {
        VBox.setVgrow(fileViewContainer, Priority.ALWAYS);
    }

    @Override
    public void start(final Stage stage) {
        stage.setTitle("Notepad");
        stage.setScene(scene);
        stage.show();

        loadUserChoices();
        respondToMenu();

        stage.setOnCloseRequest((windowEvent) -> {
            System.out.println("close");

            // Terminate all Platform.runLater()
            System.exit(0);
        });
    }

    private void loadUserChoices() {
        // User choices
        topMenu.viewDarkEnabled.setSelected(EditorProperties.getBoolean("darkModeEnabled"));
        DarkMode.change(scene, topMenu.viewDarkEnabled.isSelected());

        topMenu.fileAutoSave.setSelected(EditorProperties.getBoolean("autoSaveEnabled"));
    }

    private void respondToMenu() {
        // Respond to file Section
        topMenu.fileOpen.onAction(this::onOpenFile);
        topMenu.fileNew.onAction(this::onNewFile);
        topMenu.fileSave.onAction(this::onSaveFile);
        topMenu.fileSaveAll.onAction(this::onSaveFileAll);
        topMenu.fileSaveAs.onAction(this::onSaveFileAs);
        topMenu.fileAutoSave.onAction(this::onAutoSaveChange);

        // Respond to view Section
        topMenu.viewDisableEdit.onAction(this::setDisableOnEachTextArea);
        topMenu.viewDisableWrapLines.onAction(this::setTextWrapOnEachTextArea);
        topMenu.viewDarkEnabled.onAction(() -> DarkMode.change(scene, topMenu.viewDarkEnabled.isSelected()));

        // Respond to crypt section
        topMenu.cryptEncrypt.onAction(this::onEncrypt);
        topMenu.cryptDecrypt.onAction(this::onDecrypt);

        // Block some actions when no file is opened
        fileViewContainer.getSelectionModel().selectedItemProperty().addListener(this::checkIfActionsShouldBeEnabled);
        this.checkIfActionsShouldBeEnabled(null, null, null);
    }

    private Void onAutoSaveChange() {

        final var enabled = topMenu.fileAutoSave.isSelected();

        EditorProperties.save("autoSaveEnabled", String.valueOf(enabled));
        if (enabled) {
           onSaveFileAll();
        }
        return null;
    }

    private void checkIfActionsShouldBeEnabled(final ObservableValue<? extends Tab> ov, final Tab form, final Tab to) {
        final var totalViewsCount = fileViewContainer.getTabs().size();
        final var disable = totalViewsCount == 0;
        topMenu.fileSave.setDisable(disable);
        topMenu.fileSaveAll.setDisable(disable);
        topMenu.fileSaveAs.setDisable(disable);
        topMenu.viewDisableWrapLines.setDisable(disable);
        topMenu.viewDisableEdit.setDisable(disable);
        topMenu.cryptMenu.setDisable(disable);

    }

    private Void onEncrypt() {
        final SecretKey secretKey = Crypt.generateKey();
        final String encoded = Crypt.encodeKey(secretKey);

        new CopyTextAlert(Alert.AlertType.CONFIRMATION, "Save your key", encoded).show();

        final var view = fileViewContainer.getCurrent();

        view.encrypt(secretKey);
        return null;
    }

    private Void onDecrypt() {
        final var alert = new GetTextAlert(Alert.AlertType.INFORMATION, "Provide your key");
        alert.showAndWait();
        final var encodedKey = alert.getText();
        final var decodedKey = Crypt.decodeKey(encodedKey);
        final var view = fileViewContainer.getCurrent();

        view.decrypt(decodedKey);
        return null;
    }

    private Void setTextWrapOnEachTextArea() {
        for (final var view : fileViewContainer.getViews()) {
            view.getTextArea().setWrapText(topMenu.viewDisableWrapLines.isSelected());
        }
        return null;
    }

    private Void setDisableOnEachTextArea() {
        for (final var view : fileViewContainer.getViews()) {
            view.getTextArea().setEditable(!topMenu.viewDisableEdit.isSelected());
        }
        return null;
    }

    private Void onOpenFile() {
        final var fileChooser = new FileChooser();
        fileChooser.setTitle("Open text files");
        final var selected = fileChooser.showOpenMultipleDialog(scene.getWindow());

        if (selected == null) {
            return null;
        }

        for (final var file : selected) {
            final var view = new FileView(file);
            view.getTextArea().setEditable(!topMenu.viewDisableEdit.isSelected());
            view.getTextArea().setWrapText(topMenu.viewDisableWrapLines.isSelected());
            fileViewContainer.addView(view);
        }

        return null;
    }

    public Void onNewFile() {
        final var fileChooser = new FileChooser();
        fileChooser.setTitle("Create new");

        final var file = fileChooser.showSaveDialog(scene.getWindow());

        try {
            if (!file.createNewFile()) {
                System.err.printf("%s already exists, opening instead", file);
            }
        } catch (final IOException e) { ErrorAlert.Show(e); }

        final var view = new FileView(file);
        view.getTextArea().setEditable(!topMenu.viewDisableEdit.isSelected());
        view.getTextArea().setWrapText(topMenu.viewDisableWrapLines.isSelected());
        fileViewContainer.addView(view);

        return null;
    }

    private Void onSaveFile() {
        final var view = fileViewContainer.getCurrent();
        view.save();
        return null;
    }

    private Void onSaveFileAs() {
        final var view = fileViewContainer.getCurrent();

        final var fileChooser = new FileChooser();
        fileChooser.setTitle("Save as");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("All Files", "*.*"));
        final var file = fileChooser.showSaveDialog(scene.getWindow());

        if (file == null) {
            return null;
        }

        view.save(file);
        return null;
    }

    private Void onSaveFileAll() {
        for (final var view : fileViewContainer.getViews()) {
            view.save();
        }
        return null;
    }

    public static void main(final String[] args) {
        launch(args);
    }
}
