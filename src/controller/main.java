package controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Dylan Meza
 */
public class main extends Application {
    
    @Override
    public void start(Stage Stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/View/mainMenu.fxml"));
        Scene scene = new Scene(root);
        Stage.setTitle("Mines Weeper");
        Stage.setScene(scene);
        Stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}