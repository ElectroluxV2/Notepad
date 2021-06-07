package com.github.electroluxv2.components;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;

import java.io.IOException;

public class TopMenu extends MenuBar {
    public final ModdedMenuItem fileOpen;
    public final ModdedMenuItem fileSave;
    public final ModdedCheckMenuItem fileAutoSave;
    public final ModdedCheckMenuItem viewDisableEdit;
    public final ModdedCheckMenuItem viewDisableWrapLines;
    public final ModdedCheckMenuItem viewDarkEnabled;
    public final ModdedMenuItem cryptEncrypt;
    public final ModdedMenuItem cryptDecrypt;

    public TopMenu() throws IOException {
        final var fileMenu = new Menu("File");
        fileOpen = new ModdedMenuItem("Open");
        fileSave = new ModdedMenuItem("Save");
        fileAutoSave = new ModdedCheckMenuItem("Auto save");
        fileMenu.getItems().addAll(fileOpen, fileSave, fileAutoSave);
        this.getMenus().add(fileMenu);

        final var viewMenu = new Menu("View");
        viewDisableEdit = new ModdedCheckMenuItem("Disable edit");
        viewDisableWrapLines = new ModdedCheckMenuItem("Wrap lines");
        viewDarkEnabled = new ModdedCheckMenuItem("Dark mode");
        viewMenu.getItems().addAll(viewDisableEdit, viewDisableWrapLines, viewDarkEnabled);
        this.getMenus().add(viewMenu);

        final var cryptMenu = new Menu("Crypt");
        cryptEncrypt = new ModdedMenuItem("Encrypt");
        cryptDecrypt = new ModdedMenuItem("Decrypt");
        cryptMenu.getItems().addAll(cryptEncrypt, cryptDecrypt);
        this.getMenus().add(cryptMenu);
    }
}
