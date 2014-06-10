/*
 * Copyright (C) 2014 Consulion
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package net.consulion.jeotag.gui;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import net.consulion.jeotag.KmlReader;
import net.consulion.jeotag.PhotoLoader;

public class TaggingToolBar extends Pane {

    private final VBox parent;
    private final ToolBar toolBar;
    private final Button btLoadKML;
    private final Button btLoadPhotos;
    private final Button btGeotag;
    private final Button btSettings;
    private final FileChooser fileChooser;

    public TaggingToolBar(final VBox parent) {
        this.parent = parent;
        toolBar = new ToolBar();
        btLoadKML = new Button("Load location history");
        btLoadPhotos = new Button("Load photos");
        btGeotag = new Button("Start Geotagging");
        btSettings = new Button("Settings");
        fileChooser = new FileChooser();
        initUI();
        initLayout();

    }

    private void initLayout() {
        toolBar.prefWidthProperty().bind(parent.widthProperty());
        this.getChildren().add(toolBar);
        toolBar.getItems().addAll(
                btLoadKML, btLoadPhotos, btGeotag, btSettings);
        layout();
    }

    private void initUI() {
        btLoadKML.setOnAction((final ActionEvent t) -> {
            onLoadKml();
        });
        btLoadPhotos.setOnAction((final ActionEvent t) -> {
            onLoadPhotos();
        });
        btGeotag.setDisable(true);

    }

    private void onLoadKml() {
        fileChooser.setTitle("Load location history...");
        final List<String> extensions = new ArrayList<>(2);
        extensions.add("*.kml");
        extensions.add("*.KML");
        final FileChooser.ExtensionFilter extensionFilter
                = new FileChooser.ExtensionFilter("KML-Files", extensions);
        fileChooser.getExtensionFilters().setAll(extensionFilter);
        fileChooser.setSelectedExtensionFilter(extensionFilter);
        final File file = fileChooser.showOpenDialog(getScene().getWindow());
        if (file != null) {
            KmlReader.read(file);
        }
    }

    private void onLoadPhotos() {
        fileChooser.setTitle("Load Photos...");
        final List<String> extensions = new ArrayList<>(4);
        extensions.add("*.jpg");
        extensions.add("*.jpeg");
        extensions.add("*.JPG");
        extensions.add("*.JPEG");
        final FileChooser.ExtensionFilter extensionFilter
                = new FileChooser.ExtensionFilter("JPEG-Files", extensions);
        fileChooser.getExtensionFilters().setAll(extensionFilter);
        fileChooser.setSelectedExtensionFilter(extensionFilter);
        final List<File> list
                = fileChooser.showOpenMultipleDialog(getScene().getWindow());
        if (list != null) {
            PhotoLoader.load(list);
        }
    }

    private void onStartGeotagging() {

    }

    private void onOpenSettings() {

    }

}
