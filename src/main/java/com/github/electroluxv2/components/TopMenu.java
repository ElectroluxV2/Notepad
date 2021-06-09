package com.github.electroluxv2.components;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;

public class TopMenu extends MenuBar {
    public final ModdedMenuItem fileOpen;
    public final ModdedMenuItem fileNew;
    public final ModdedMenuItem fileSave;
    public final ModdedMenuItem fileSaveAs;
    public final ModdedMenuItem fileSaveAll;
    public final ModdedCheckMenuItem fileAutoSave;
    public final ModdedCheckMenuItem viewDisableEdit;
    public final ModdedCheckMenuItem viewDisableWrapLines;
    public final ModdedCheckMenuItem viewDarkEnabled;
    public final Menu cryptMenu;
    public final ModdedMenuItem cryptEncrypt;
    public final ModdedMenuItem cryptDecrypt;

    public TopMenu() {
        final var fileMenu = new Menu("File");
        fileOpen = new ModdedMenuItem("Open");
        fileOpen.setAccelerator(new KeyCodeCombination(KeyCode.O, KeyCodeCombination.CONTROL_ANY));
        fileNew = new ModdedMenuItem("New");
        fileNew.setAccelerator(new KeyCodeCombination(KeyCode.N, KeyCodeCombination.CONTROL_ANY));
        fileSave = new ModdedMenuItem("Save");
        fileSave.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCodeCombination.CONTROL_ANY));
        fileSaveAs = new ModdedMenuItem("Save as");
        fileSaveAll = new ModdedMenuItem("Save all");
        fileAutoSave = new ModdedCheckMenuItem("Auto save");
        fileMenu.getItems().addAll(fileOpen, fileNew, fileSave, fileSaveAs, fileSaveAll, fileAutoSave);
        this.getMenus().add(fileMenu);

        final var viewMenu = new Menu("View");
        viewDisableEdit = new ModdedCheckMenuItem("Disable edit");
        viewDisableWrapLines = new ModdedCheckMenuItem("Wrap lines");
        viewDarkEnabled = new ModdedCheckMenuItem("Dark mode");
        viewMenu.getItems().addAll(viewDisableEdit, viewDisableWrapLines, viewDarkEnabled);
        this.getMenus().add(viewMenu);

        cryptMenu = new Menu("Crypt");
        cryptEncrypt = new ModdedMenuItem("Encrypt");
        cryptEncrypt.setAccelerator(new KeyCodeCombination(KeyCode.E, KeyCodeCombination.CONTROL_ANY));
        cryptDecrypt = new ModdedMenuItem("Decrypt");
        cryptDecrypt.setAccelerator(new KeyCodeCombination(KeyCode.D, KeyCodeCombination.CONTROL_ANY));
        cryptMenu.getItems().addAll(cryptEncrypt, cryptDecrypt);
        this.getMenus().add(cryptMenu);
    }
}
