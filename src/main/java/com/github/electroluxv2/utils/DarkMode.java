package com.github.electroluxv2.utils;

import javafx.scene.Scene;

import java.io.IOException;

public class DarkMode {
    public static Void change(final Scene scene, final boolean enabled) {
        if (enabled) {
            scene.getStylesheets().add("dark.css");
        } else {
            scene.getStylesheets().remove("dark.css");
        }

        EditorProperties.save("darkModeEnabled", String.valueOf(enabled));
        return null;
    }
}
