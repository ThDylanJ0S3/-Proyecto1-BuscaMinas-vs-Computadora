package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Dylan Meza
 */
public class mainMenuController implements Initializable {

    @FXML
    private Button play;
    @FXML
    private Button score;
    @FXML
    private Button exit;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void play(ActionEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/selectDifficulty.fxml"));
            Parent root = loader.load();
            selectDifficultyController controlador = loader.getController();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(mainMenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void score(ActionEvent event) {
    }

    @FXML
    private void exit(ActionEvent event) {
    }
    
}
