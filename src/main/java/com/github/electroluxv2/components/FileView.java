package com.github.electroluxv2.components;

import com.github.electroluxv2.utils.Crypt;
import com.github.electroluxv2.utils.StringUtils;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SecretKey;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.security.InvalidKeyException;
import java.util.StringJoiner;
import java.util.stream.Stream;

public class FileView extends Tab {
    private File file;
    private final ModdedTextArea textArea;
    private final VBox labelContainer;
    private final ScrollPane scrollPane;
    private boolean bind = false;
    private boolean modified = false;

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

        try {
            loadContentFromFile();
        } catch (IOException e) {
            textArea.setText("Failed to load file: %s".formatted(e.getMessage()));
            textArea.setDisable(true);
        } finally {
            handleLineNumbers();
        }

        // Add new labels when user input
        textArea.textProperty().addListener(observable -> {
            setText(this.file.getName() + " *");
            modified = true;
            handleLineNumbers();
        });

        // Sync scroll
        textArea.setOnScroll(scrollEvent -> {
            // Sync scrollbars, only when there is scrollbar
            if (textArea.getScrollTop() > 0 && !bind) {
                bind = true;
                scrollPane.vvalueProperty().bindBidirectional(textArea.vvalueProperty());
            }
        });


        // Prevent work lost
        setOnCloseRequest(event -> {
            // Only when there are any changes
            if (!modified) {
                return;
            }

            final var alert = new Alert(Alert.AlertType.CONFIRMATION, "Close without saving ?", ButtonType.YES, ButtonType.FINISH, ButtonType.CANCEL);
            alert.showAndWait();

            if (alert.getResult().equals(ButtonType.CANCEL)) {
                event.consume();
            } else if (alert.getResult().equals(ButtonType.APPLY)) {
                try {
                    save();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void save() throws IOException {
        save(file);
    }

    public void save(final File saveAsFile) throws IOException {
        final var contest = textArea.getParagraphs();
        final var fw = new FileWriter(saveAsFile, false);
        contest.forEach(sequence -> {
            try {
                fw.append(sequence);
                fw.append(System.lineSeparator());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        fw.close();
        setText(saveAsFile.getName());
        file = saveAsFile;
        modified = false;
    }

    public void encrypt(final SecretKey secretKey) throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        final var text = getTextArea().getText();
        final var encrypted = Crypt.encrypt(text, secretKey);
        getTextArea().setText(encrypted);
    }

    public void decrypt(final SecretKey secretKey) throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        final var encrypted = getTextArea().getText();
        final var text = Crypt.decrypt(encrypted, secretKey);
        getTextArea().setText(text);
    }

    public ModdedTextArea getTextArea() {
        return textArea;
    }

    private void handleLineNumbers() {
        // Re add line labels
        labelContainer.getChildren().clear();
        final var rowCount = StringUtils.count(textArea.getText(), System.lineSeparator());

        for (int i = 0; i <= rowCount; i++) {
            labelContainer.getChildren().addAll(new Label(String.valueOf(i + 1)));
        }
    }

    private void loadContentFromFile() throws IOException {
        final Stream<String> lines = Files.lines(file.toPath(), Charset.defaultCharset());
        final var sj = new StringJoiner(System.lineSeparator());
        lines.forEach(sj::add);
        textArea.setText(sj.toString());
    }
}
