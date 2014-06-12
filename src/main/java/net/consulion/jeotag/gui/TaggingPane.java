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
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToolBar;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import net.consulion.jeotag.DataHolder;
import net.consulion.jeotag.KmlReader;
import net.consulion.jeotag.PhotoLoader;
import net.consulion.jeotag.model.LocationRecord;
import net.consulion.jeotag.model.PhotoDataset;

public class TaggingPane extends GridPane {

    private TableView<LocationRecord> tvLocations;
    private TableView<PhotoDataset> tvPhotos;
    private Label laLocations;
    private Label laPhotos;
    private Button btLoadKML;
    private Button btLoadPhotos;
    private Button btLoadPhotoDirectory;
    private Button btGeotag;
    private FileChooser fileChooser;
    private DirectoryChooser directoryChooser;
    private ToolBar progressToolBar;
    private ProgressBar progressBar;

    public TaggingPane() {
        createComponents();
        initLayout();
        createTableColumns();
        initUi();
    }

    private void createComponents() {
        tvLocations = new TableView<>(DataHolder.getInstance().getLocations());
        tvPhotos = new TableView<>(DataHolder.getInstance().getPhotos());
        laLocations = new Label("Locations");
        laPhotos = new Label("Photos");
        laLocations.setFont(Font.font(20));
        laPhotos.setFont(Font.font(20));
        btLoadKML = new Button("Load location history file(s)");
        btLoadPhotos = new Button("Load image file(s)");
        btLoadPhotoDirectory = new Button("Load directory");
        btGeotag = new Button("Start Geotagging");
        fileChooser = new FileChooser();
        directoryChooser = new DirectoryChooser();
        progressToolBar = new ToolBar();
        progressBar = new ProgressBar();
    }

    private void initLayout() {
        setHgap(7);
        setVgap(2);
        add(laLocations, 0, 0);
        add(btLoadKML, 1, 0);
        add(laPhotos, 2, 0);
        add(btLoadPhotos, 3, 0);
        add(btLoadPhotoDirectory, 4, 0);
        add(tvLocations, 0, 1, 2, 1);
        add(tvPhotos, 2, 1, 3, 1);
        tvLocations.prefWidthProperty().bind(this.widthProperty().multiply(0.5));
        tvPhotos.prefWidthProperty().bind(this.widthProperty().multiply(0.5));
        tvLocations.prefHeightProperty().bind(this.heightProperty());
        tvPhotos.prefHeightProperty().bind(this.heightProperty());
        progressToolBar.getItems().add(progressBar);
        progressToolBar.prefWidthProperty().bind(this.widthProperty());
        add(progressToolBar, 0, 2, 5, 1);
    }

    private void initUi() {
        btLoadKML.setOnAction((final ActionEvent t) -> {
            onLoadKml();
        });
        btLoadPhotos.setOnAction((final ActionEvent t) -> {
            onLoadPhotos();
        });
        btLoadPhotoDirectory.setOnAction((final ActionEvent t) -> {
            onLoadPhotoDirectory();
        });
        btGeotag.setDisable(true);
        progressBar.setVisible(false);
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

        tvPhotos.getColumns().addAll(filePhoto, instantTaken, hasGeotag);
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
            final Task<Void> task = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    KmlReader.read(file);
                    //TODO: actually show progress.
                    updateProgress(1, 1);
                    progressBar.setVisible(false);
                    return null;
                }

            };
            progressBar.progressProperty().unbind();
            progressBar.setVisible(true);
            progressBar.progressProperty().bind(task.progressProperty());
            new Thread(task).start();
        }
    }

    private void onLoadPhotoDirectory() {
        directoryChooser.setTitle("Select photo directoy...");
        final File dir
                = directoryChooser.showDialog(getScene().getWindow());
        if (dir != null && dir.canRead()) {
            final Task<Void> task = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    final List<File> fileList = new ArrayList<>();
                    final FileVisitor<Path> visitor = new FileVisitor<Path>() {

                        @Override
                        public FileVisitResult preVisitDirectory(
                                final Path dir, final BasicFileAttributes attrs)
                                throws IOException {
                            return FileVisitResult.CONTINUE;
                        }

                        @Override
                        public FileVisitResult visitFile(
                                final Path file, final BasicFileAttributes attrs)
                                throws IOException {
                            if (file.toFile().isFile()) {
                                final String fString
                                        = file.getFileName().toString();
                                final String extension = fString.substring(
                                        fString.lastIndexOf('.') + 1);
                                if (extension.equalsIgnoreCase("jpg")
                                        || extension.equalsIgnoreCase("jpeg")) {
                                    fileList.add(file.toFile());
                                }
                            }
                            return FileVisitResult.CONTINUE;
                        }

                        @Override
                        public FileVisitResult visitFileFailed(
                                final Path file, final IOException exc)
                                throws IOException {
                            return FileVisitResult.CONTINUE;
                        }

                        @Override
                        public FileVisitResult postVisitDirectory(
                                final Path dir, final IOException exc)
                                throws IOException {
                            return FileVisitResult.CONTINUE;
                        }
                    };
                    Files.walkFileTree(dir.toPath(), visitor);
                    addImageFiles(fileList);
                    return null;
                }
            };
            progressBar.progressProperty().unbind();
            progressBar.setVisible(true);
            progressBar.progressProperty().bind(task.progressProperty());
            new Thread(task).start();
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
        addImageFiles(list);
    }

    private void addImageFiles(final List<File> list) {
        if (list != null) {
            final Task<Void> task = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    PhotoLoader.load(list);
                    //TODO: actually show progress.
                    updateProgress(1, 1);
                    progressBar.setVisible(false);
                    return null;
                }
            };
            progressBar.progressProperty().unbind();
            progressBar.setVisible(true);
            progressBar.progressProperty().bind(task.progressProperty());
            new Thread(task).start();
        }
    }

    private void onStartGeotagging() {

    }
}
