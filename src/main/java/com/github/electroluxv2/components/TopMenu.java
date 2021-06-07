package com.github.electroluxv2.components;

import com.github.electroluxv2.utils.EditorProperties;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

import java.io.IOException;

public class TopMenu extends MenuBar {
    public final CheckMenuItem viewMenuDarkMode;

    public TopMenu() throws IOException {
        final var fileMenu = new Menu("File");
        final var fileMenuOpen = new MenuItem("Open");
        final var fileMenuSave = new MenuItem("Save");
        final var fileMenuAutoSave = new CheckMenuItem("Auto save");
        fileMenu.getItems().addAll(fileMenuOpen, fileMenuSave, fileMenuAutoSave);
        this.getMenus().add(fileMenu);

        final var viewMenu = new Menu("View");
        final var viewMenuDisableEdit = new CheckMenuItem("Disable edit");
        final var viewMenuDisableWrapLines = new CheckMenuItem("Wrap lines");
        viewMenuDarkMode = new CheckMenuItem("Dark mode");
        viewMenu.getItems().addAll(viewMenuDisableEdit, viewMenuDisableWrapLines, viewMenuDarkMode);
        this.getMenus().add(viewMenu);

        final var cryptMenu = new Menu("Crypt");
        final var cryptMenuEncrypt = new MenuItem("Encrypt");
        final var cryptMenuDecrypt = new MenuItem("Decrypt");
        cryptMenu.getItems().addAll(cryptMenuEncrypt, cryptMenuDecrypt);
        this.getMenus().add(cryptMenu);

        loadFromProperties();
    }

    private void loadFromProperties() throws IOException {
    }
}
