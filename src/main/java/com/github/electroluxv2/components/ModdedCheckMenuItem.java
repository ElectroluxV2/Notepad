package com.github.electroluxv2.components;

import javafx.scene.Node;
import javafx.scene.control.CheckMenuItem;

import java.util.concurrent.Callable;

public class ModdedCheckMenuItem extends CheckMenuItem {
    private Callable<Void> handler;

    public ModdedCheckMenuItem() {
        super();
        registerHandler();
    }

    public ModdedCheckMenuItem(String s) {
        super(s);
        registerHandler();
    }

    public ModdedCheckMenuItem(String s, Node node) {
        super(s, node);
        registerHandler();
    }

    public void onAction(final Callable<Void> handler) {
        this.handler = handler;
        registerHandler();
    }

    private void registerHandler() {
        setOnAction(actionEvent -> {
            try {
                if (handler != null) handler.call();
            } catch (Exception exception) {
                System.err.println(exception.getMessage());
            }
        });
    }
}
