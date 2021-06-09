package com.github.electroluxv2.components;

import javafx.scene.Node;
import javafx.scene.control.MenuItem;

import java.util.concurrent.Callable;

public class ModdedMenuItem extends MenuItem {
    private Callable<Void> handler;

    public ModdedMenuItem() {
        super();
        registerHandler();
    }

    public ModdedMenuItem(String s) {
        super(s);
        registerHandler();
    }

    public ModdedMenuItem(String s, Node node) {
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
                exception.printStackTrace();
                System.err.println(exception.getMessage());
            }
        });
    }
}
