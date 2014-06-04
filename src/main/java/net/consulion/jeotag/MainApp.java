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

package net.consulion.jeotag;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import net.consulion.jeotag.gui.view.TaggingPane;
import net.consulion.jeotag.gui.view.TaggingToolBar;

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

    private final Group gRoot = new Group();
    private Scene scene;
    private VBox vbMain;
    private TaggingPane taggingPane;
    
    private TaggingToolBar toolBar;

    @Override
    public void start(final Stage stage) throws Exception {
        initUI(stage);
        initLayout();
    }

    private void initUI(final Stage stage) {
        scene = new Scene(gRoot, 1024, 768);
        scene.getStylesheets().add("/styles/Styles.css");
        stage.setTitle("Jeotag - Photo Geotagging");
        stage.setScene(scene);
        stage.show();

        vbMain = new VBox();
        toolBar = new TaggingToolBar();
        taggingPane = new TaggingPane();
    }

    private void initLayout() {
        toolBar.setPrefWidth(Double.MAX_VALUE);
        vbMain.setPrefWidth(Double.MAX_VALUE);
        gRoot.getChildren().add(vbMain);
        vbMain.getChildren().add(toolBar);
        vbMain.getChildren().add(taggingPane);
    }

}
