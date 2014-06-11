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
package net.consulion.jeotag;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import net.consulion.jeotag.gui.TaggingPane;
import net.consulion.jeotag.gui.TaggingToolBar;

public class MainApp extends Application {

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(final String[] args) {
        launch(args);
    }
    
    private final BorderPane bpRoot = new BorderPane();
    private Scene scene;
    private GridPane gpMain;
    private TaggingPane taggingPane;
    private TaggingToolBar toolBar;
    
    @Override
    public void start(final Stage stage) throws Exception {
        initUI(stage);
        initLayout();
    }
    
    private void initUI(final Stage stage) {
        scene = new Scene(bpRoot, 1280, 768);
        stage.setTitle("Jeotag - Photo Geotagging");
        stage.setScene(scene);
        stage.show();
        gpMain = new GridPane();
        toolBar = new TaggingToolBar();
        taggingPane = new TaggingPane();
    }
    
    private void initLayout() {
        scene.setFill(Paint.valueOf("lightgrey"));
        bpRoot.setCenter(gpMain);
        toolBar.prefWidthProperty().bind(bpRoot.widthProperty());
        taggingPane.prefHeightProperty().bind(bpRoot.heightProperty());
        gpMain.add(toolBar, 0, 0);
        gpMain.add(taggingPane, 0, 1);
    }
    
}
