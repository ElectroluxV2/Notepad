package com.github.electroluxv2.components;

import com.github.electroluxv2.utils.StringUtils;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class FileView extends Tab {
    public FileView(final String filename) {
        super(filename);

        final var textArea = new ModdedTextArea();
        final var labelContainer = new VBox();
        final var scrollPane = new ScrollPane(labelContainer);

        labelContainer.setPadding(new Insets(5,5,5,5));
        scrollPane.setMaxWidth(40);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        final var hBox = new HBox(scrollPane, textArea);
        HBox.setHgrow(textArea, Priority.ALWAYS);
        HBox.setHgrow(scrollPane, Priority.ALWAYS);

        this.setContent(hBox);

        // Stupid java
        final boolean[] bind = {false};
        textArea.textProperty().addListener(observable -> {
            // Re add line labels
            labelContainer.getChildren().clear();
            final var rowCount = StringUtils.count(textArea.getText(), System.lineSeparator());

            for (int i = 0; i <= rowCount; i++) {
                labelContainer.getChildren().addAll(new Label(String.valueOf(i + 1)));
            }

            // Sync scrollbars, only when there is scrollbar
            if (textArea.getScrollTop() > 0 && !bind[0]) {
                bind[0] = true;
                scrollPane.vvalueProperty().bindBidirectional(textArea.vvalueProperty());
            }
        });
    }
}
