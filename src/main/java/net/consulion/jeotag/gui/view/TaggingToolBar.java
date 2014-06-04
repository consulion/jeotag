/*
 * Copyright (C) 2014 Consulion
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package net.consulion.jeotag.gui.view;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import net.consulion.jeotag.gui.controller.TaggingController;

public class TaggingToolBar extends Pane {

    private final TaggingController controller;
    private final ToolBar toolBar;
    private final HBox hbButtons;
    private final Button btLoadKML;
    private final Button btLoadPhotos;
    private final Button btGeotag;
    private final Button btSettings;

    public TaggingToolBar() {
        controller = TaggingController.getInstance();
        toolBar = new ToolBar();
        hbButtons = new HBox();
        btLoadKML = new Button("Load location history");
        btLoadPhotos = new Button("Load photos");
        btGeotag = new Button("Start Geotagging");
        btSettings = new Button("Settings");
        initUI();
        initLayout();

    }

    private void initLayout() {
        toolBar.setPrefWidth(Double.MAX_VALUE);
        toolBar.getItems().add(hbButtons);
        this.getChildren().add(toolBar);
        toolBar.getItems().addAll(
                btLoadKML, btLoadPhotos, btGeotag, btSettings);
        layout();
    }

    private void initUI() {
        btLoadKML.setOnAction((final ActionEvent t) -> {
            onLoadKml();
        });

    }

    private void onLoadKml() {
        controller.onLoadKml();
    }

    private void onLoadPhotos() {
        controller.onLoadPhotos();
    }

    private void onStartGeotagging() {
        controller.onStartGeotagging();
    }

    private void onOpenSettings() {
        controller.onOpenSettings();
    }

}
