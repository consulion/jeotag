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
import java.time.Instant;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import net.consulion.jeotag.DataHolder;
import net.consulion.jeotag.model.LocationRecord;
import net.consulion.jeotag.model.PhotoDataset;

public class TaggingPane extends GridPane {

    private TableView<LocationRecord> tvLocations;
    private TableView<PhotoDataset> tvPhotos;
    private Label laLocations;
    private Label laPhotos;

    public TaggingPane() {
        createComponents();
        initLayout();
        createTableColumns();
    }

    private void createComponents() {
        tvLocations = new TableView<>(DataHolder.getInstance().getLocations());
        tvPhotos = new TableView<>(DataHolder.getInstance().getPhotos());
        laLocations = new Label("Locations");
        laPhotos = new Label("Photos");
    }

    private void initLayout() {
        add(laLocations, 0, 0);
        add(laPhotos, 1, 0);
        add(tvLocations, 0, 1);
        add(tvPhotos, 1, 1);
        //tvPhotos.setPrefSize(635, 640);
        //tvLocations.setPrefSize(635, 640);
        tvLocations.prefWidthProperty().bind(this.widthProperty().multiply(0.5));
        tvPhotos.prefWidthProperty().bind(this.widthProperty().multiply(0.5));
        //TaggingPane.setHgrow(tvLocations, Priority.ALWAYS);
        //TaggingPane.setHgrow(tvPhotos, Priority.ALWAYS);
    }

    @SuppressWarnings("unchecked")
    private void createTableColumns() {
        //location: latitude
        final TableColumn<LocationRecord, Float> latitude
                = new TableColumn<>("Latitude");
        latitude.setCellValueFactory(new PropertyValueFactory<>("latitude"));
        latitude.prefWidthProperty().
                bind(tvLocations.widthProperty().multiply(0.25));

        //location: longitude
        final TableColumn<LocationRecord, Float> longitude
                = new TableColumn<>("Longitude");
        longitude.setCellValueFactory(new PropertyValueFactory<>("longitude"));
        longitude.prefWidthProperty().
                bind(tvLocations.widthProperty().multiply(0.25));

        //location: altitude
        final TableColumn<LocationRecord, Float> altitude
                = new TableColumn<>("Altitude");
        altitude.setCellValueFactory(new PropertyValueFactory<>("altitude"));
        altitude.prefWidthProperty().
                bind(tvLocations.widthProperty().multiply(0.125));

        //location: time
        final TableColumn<LocationRecord, Instant> time
                = new TableColumn<>("Timestamp");
        time.setCellValueFactory(new PropertyValueFactory<>("time"));
        time.prefWidthProperty().
                bind(tvLocations.widthProperty().multiply(0.375));

        tvLocations.getColumns().addAll(latitude, longitude, altitude, time);

        //photo: path
        final TableColumn<PhotoDataset, File> filePhoto
                = new TableColumn<>("Path");
        filePhoto.setCellValueFactory(new PropertyValueFactory<>("filePhoto"));
        filePhoto.prefWidthProperty().
                bind(tvPhotos.widthProperty().multiply(0.525));

        //photo: instant taken
        final TableColumn<PhotoDataset, Instant> instantTaken
                = new TableColumn<>("Timestamp");
        instantTaken.setCellValueFactory(
                new PropertyValueFactory<>("instantTaken"));
        instantTaken.prefWidthProperty().
                bind(tvPhotos.widthProperty().multiply(0.35));

        //photo: has geotag
        final TableColumn<PhotoDataset, Boolean> hasGeotag
                = new TableColumn<>("Has Geotag");
        hasGeotag.setCellValueFactory(new PropertyValueFactory<>("hasGeotag"));
//        instantTaken.prefWidthProperty().
//                bind(tvPhotos.widthProperty().multiply(0.14));

        tvPhotos.getColumns().addAll(filePhoto, instantTaken, hasGeotag);
    }

}
