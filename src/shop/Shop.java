/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package shop;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 *
 * @author kareem.moustafa
 */
public class Shop extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("MainShop.fxml"));
        Scene scene = new Scene(root);
        Screen screen = Screen.getPrimary();
        scene.getStylesheets().add(Shop.class.getResource("win7glass.css").toExternalForm());
        stage.setMaximized(true);
        //stage.setResizable(false);
        stage.setMinHeight(screen.getVisualBounds().getHeight());
        stage.setMinWidth(screen.getVisualBounds().getWidth());
        System.out.println(screen.getVisualBounds().getHeight());
        System.out.println(screen.getVisualBounds().getWidth());
        stage.setMaxHeight(screen.getVisualBounds().getHeight());
        stage.setMaxWidth(screen.getVisualBounds().getWidth());
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
