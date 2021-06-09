package com.github.electroluxv2.components;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;

public class GetTextAlert extends Alert {

    private final TextArea textArea;

    public GetTextAlert(final AlertType alertType, final String title) {
        super(alertType, title, ButtonType.OK);

        textArea = new TextArea();
        textArea.setWrapText(true);

        final var gridPane = new GridPane();
        gridPane.setMaxWidth(Double.MAX_VALUE);
        gridPane.add(textArea, 0, 0);

        setTitle(title);
        getDialogPane().setContent(gridPane);
    }

    public String getText() {
        return textArea.getText();
    }
}
