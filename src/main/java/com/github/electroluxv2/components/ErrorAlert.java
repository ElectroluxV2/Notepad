package com.github.electroluxv2.components;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class ErrorAlert extends Alert {
    public ErrorAlert(final Exception e) {
        super(AlertType.ERROR, "An error occurred", ButtonType.OK);
        e.printStackTrace(System.out);
        setContentText("See stderr for more details");
        show();
    }

    public static void Show(final Exception e) {
        new ErrorAlert(e);
    }
}
