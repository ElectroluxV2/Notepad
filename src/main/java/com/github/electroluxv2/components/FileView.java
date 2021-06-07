package com.github.electroluxv2.components;

import com.github.electroluxv2.utils.StringUtils;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.StringJoiner;
import java.util.stream.Stream;

public class FileView extends Tab {
    private final File file;
    private final ModdedTextArea textArea;
    private final VBox labelContainer;
    private final ScrollPane scrollPane;
    private boolean bind = false;

    public FileView(final File file) {
        super(file.getName());
        this.file = file;

        textArea = new ModdedTextArea();
        labelContainer = new VBox();
        scrollPane = new ScrollPane(labelContainer);

        labelContainer.setPadding(new Insets(5,5,5,5));
        scrollPane.setMaxWidth(40);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        final var hBox = new HBox(scrollPane, textArea);
        HBox.setHgrow(textArea, Priority.ALWAYS);
        HBox.setHgrow(scrollPane, Priority.ALWAYS);

        this.setContent(hBox);

        // Add new labels when user input
        textArea.textProperty().addListener(observable -> handleLineNumbers());

        try {
            loadContentFromFile();
        } catch (IOException e) {
            textArea.setText("Failed to load file: %s".formatted(e.getMessage()));
            textArea.setDisable(true);
        }
    }

    private void handleLineNumbers() {
        // Re add line labels
        labelContainer.getChildren().clear();
        final var rowCount = StringUtils.count(textArea.getText(), System.lineSeparator());

        for (int i = 0; i <= rowCount; i++) {
            labelContainer.getChildren().addAll(new Label(String.valueOf(i + 1)));
        }

        textArea.setOnScroll(scrollEvent -> {
            // Sync scrollbars, only when there is scrollbar
            if (textArea.getScrollTop() > 0 && !bind) {
                bind = true;
                System.out.println("bind");
                scrollPane.vvalueProperty().bindBidirectional(textArea.vvalueProperty());
            }
        });
    }

    private void loadContentFromFile() throws IOException {
        final Stream<String> lines = Files.lines(file.toPath(), Charset.defaultCharset());
        final var sj = new StringJoiner(System.lineSeparator());
        lines.forEach(sj::add);
        textArea.setText(sj.toString());
    }
}
