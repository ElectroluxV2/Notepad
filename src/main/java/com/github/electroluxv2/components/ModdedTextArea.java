package com.github.electroluxv2.components;

import javafx.beans.property.DoubleProperty;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;

public class ModdedTextArea extends TextArea {

    public ModdedTextArea() {
        setPrefRowCount(100);
    }

    public DoubleProperty vvalueProperty() {
        return ((ScrollPane) getChildrenUnmodifiable().get(0)).vvalueProperty();
    }
}
