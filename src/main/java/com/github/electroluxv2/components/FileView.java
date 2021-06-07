package com.github.electroluxv2.components;

import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

import java.util.StringJoiner;

public class FileView extends Tab {
    public FileView(final String filename) {
        super(filename);


        final var linesNumbers = new TextArea();
        final var textContent = new TextArea();
        final var hBox = new HBox(linesNumbers, textContent);
        hBox.setMaxHeight(Double.MAX_VALUE);
        hBox.setFillHeight(true);
        HBox.setHgrow(linesNumbers, Priority.ALWAYS);
        HBox.setHgrow(textContent, Priority.ALWAYS);
        linesNumbers.setMaxHeight(Double.MAX_VALUE);
        linesNumbers.setMaxWidth(40);
        this.setContent(hBox);

        linesNumbers.setDisable(true);
        textContent.textProperty().addListener(observable -> {
            final var rowCount = textContent.getText().split(System.lineSeparator()).length;
            final var sj = new StringJoiner(System.lineSeparator());
            for (int i = 1; i <= rowCount; i++) {
                sj.add(String.valueOf(i));
            }
            linesNumbers.setText(sj.toString());

            ScrollBar scrollBar = (ScrollBar) linesNumbers.lookup(".scroll-bar");
            if (scrollBar != null) scrollBar.setOpacity(0);
        });

        textContent.scrollTopProperty().bindBidirectional(linesNumbers.scrollTopProperty());
    }
}
