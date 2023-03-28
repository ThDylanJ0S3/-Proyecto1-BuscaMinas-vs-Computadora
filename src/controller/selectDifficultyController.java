package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import Model.TableroMinesweeper;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Dylan Meza
 */
public class selectDifficultyController implements Initializable {

    @FXML
    private Button dummyMode;
    @FXML
    private Button advancedMode;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void playDummy(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/gameView.fxml"));
            Parent root = loader.load();
            gameViewController controller = loader.getController();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
            
            stage.setOnCloseRequest((t) -> controller.cerrar());
            Stage myStage = (Stage) this.dummyMode.getScene().getWindow();
            myStage.close();
        } catch (IOException ex) {
            Logger.getLogger(selectDifficultyController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void playAdvanced(ActionEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/gameView.fxml"));
            Parent root = loader.load();
            gameViewController controller = loader.getController();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
            
            stage.setOnCloseRequest((t) -> controller.cerrar());
            Stage myStage = (Stage) this.dummyMode.getScene().getWindow();
            myStage.close();
        } catch (IOException ex) {
            Logger.getLogger(selectDifficultyController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void cerrar(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/mainMenu.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(selectDifficultyController.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        
    }
}