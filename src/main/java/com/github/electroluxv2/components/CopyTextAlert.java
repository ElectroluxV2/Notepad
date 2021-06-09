package com.github.electroluxv2.components;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;

public class CopyTextAlert extends Alert {
    public CopyTextAlert(final AlertType alertType, final String title, final String text) {
        super(alertType, title, ButtonType.OK);

        final var textArea = new TextArea(text);
        textArea.setEditable(false);
        textArea.setWrapText(true);

        final var gridPane = new GridPane();
        gridPane.setMaxWidth(Double.MAX_VALUE);
        gridPane.add(textArea, 0, 0);

        setTitle(title);
        getDialogPane().setContent(gridPane);
    }
}
