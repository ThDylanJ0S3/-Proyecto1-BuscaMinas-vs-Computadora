package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
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
    private void play(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/selectDifficulty.fxml"));
        Parent root = loader.load();
        selectDifficultyController control = loader.getController();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
        
        stage.setOnCloseRequest((t) -> control.cerrar());
        Stage myStage = (Stage) this.play.getScene().getWindow();
        myStage.close();
    }

    @FXML
    private void score(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/scoreMenu.fxml"));
        Parent root = loader.load();
        scoreMenuController controlador = loader.getController();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
        
        stage.setOnCloseRequest((t) -> controlador.cerrar());
        Stage myStage = (Stage) this.score.getScene().getWindow();
        myStage.close();
    }

    @FXML
    private void exit(ActionEvent event) {
        Stage stage = (Stage) this.exit.getScene().getWindow();
        stage.close();
    }
    
}
