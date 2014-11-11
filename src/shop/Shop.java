/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package shop;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import utils.DBHandler;

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
       // try {
//            Connection conn = DBHandler.connect();
//            CallableStatement proc = conn.prepareCall("{ call DB.INSERT_COMPANYINFO(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) }");
//            proc.setString(1, "2015");
//            proc.setInt(2, 12);
//            proc.setString(3, "85test");
//            proc.setString(4, "85test");
//            proc.setString(5, "85test");
//            proc.setString(6, "85test");     
//            proc.setString(7, "85test");
//            proc.setString(8, "85test");                        
//            proc.setString(9, "2015");
//            proc.setString(10, "20");
//            proc.setString(11, "85test");
//            proc.setString(12, "85test");
//            proc.setString(13, "85test");
//            proc.setString(14, "85test");     
//            proc.setString(15, "85test");
//            proc.setString(16, "85test");                        
//            proc.setString(17, "2015");
//            proc.setString(18, "111");
//            proc.setString(19, "85test");
//            proc.setString(20, "85test");
//            proc.setString(21, "85test");
//            proc.setString(22, "85test");     
//                       
//                        proc.execute();
//                           proc.close();
//            conn.close();
//            Statement s = conn.createStatement();
//            ResultSet r = s.executeQuery("SELECT * FROM COMPANYINFO");
//            System.out.println(r.next());
            //System.out.println(conn.toString());
            
            launch(args);
//        } catch (SQLException ex) {
//            Logger.getLogger(Shop.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }
    
}
