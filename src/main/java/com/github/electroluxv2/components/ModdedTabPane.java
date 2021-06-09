package com.github.electroluxv2.components;

import javafx.scene.control.TabPane;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ModdedTabPane extends TabPane {
    private final List<FileView> fileViewList = new ArrayList<>();

    public ModdedTabPane() { }

    public ModdedTabPane(final FileView... tabs) {
        super(tabs);
        fileViewList.addAll(Arrays.asList(tabs));
    }

    public void addView(final FileView view) {
        getTabs().add(view);
        fileViewList.add(view);
    }

    public List<FileView> getViews() {
        return fileViewList;
    }
}
