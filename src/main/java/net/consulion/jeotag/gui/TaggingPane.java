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

import java.time.Instant;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import net.consulion.jeotag.DataHolder;
import net.consulion.jeotag.model.LocationRecord;

public class TaggingPane extends GridPane {

    private TableView<LocationRecord> tvLocations;

    public TaggingPane() {
        createComponents();
        initLayout();
        createTableColumns();
    }

    private void createComponents() {
        tvLocations = new TableView<>(DataHolder.getInstance().getLocations());
    }

    private void initLayout() {
        add(tvLocations, 0, 1);
    }

    @SuppressWarnings("unchecked")
    private void createTableColumns() {
        //latitude
        final TableColumn<LocationRecord, Float> latitude = new TableColumn<>("Latitude");
        latitude.setCellValueFactory(new PropertyValueFactory<>("latitude"));

        //longitude
        final TableColumn<LocationRecord, Float> longitude = new TableColumn<>("Longitude");
        longitude.setCellValueFactory(new PropertyValueFactory<>("longitude"));

        //altitude
        final TableColumn<LocationRecord, Float> altitude = new TableColumn<>("Altitude");
        altitude.setCellValueFactory(new PropertyValueFactory<>("altitude"));

        //time
        final TableColumn<LocationRecord, Instant> time = new TableColumn<>("Timestamp");
        time.setCellValueFactory(new PropertyValueFactory<>("time"));

        tvLocations.getColumns().addAll(latitude, longitude, altitude, time);
    }

}
