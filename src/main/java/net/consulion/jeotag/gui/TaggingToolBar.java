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

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;

public class TaggingToolBar extends ToolBar {

    private final Button btExit;
    private final Button btSettings;
    private final Button btHelp;
    private final Button btAbout;

    public TaggingToolBar() {
        btExit = new Button("Exit");
        btSettings = new Button("Settings");
        btHelp = new Button("Help");
        btAbout = new Button("About");
        initUI();
        initLayout();
    }

    private void initLayout() {
        getItems().addAll(btExit, btSettings, btHelp, btAbout);
        layout();
    }

    private void initUI() {
        btExit.setOnAction((final ActionEvent t) -> {
            onExit();
        });
        btSettings.setOnAction((final ActionEvent t) -> {
            onOpenSettings();
        });
        btHelp.setOnAction((final ActionEvent t) -> {
            onOpenHelp();
        });
        btAbout.setOnAction((final ActionEvent t) -> {
            onOpenAbout();
        });
    }

    private void onOpenSettings() {

    }

    private void onOpenHelp() {

    }

    private void onOpenAbout() {

    }

    private void onExit() {
        Platform.exit();
    }
}
