package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
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

    /** 
    * Inicia el juego en modo "Dummy" cuando se hace clic en el botón correspondiente.
    * Carga la vista del juego, configura los controles de la partida y muestra la escena.
    * Cierra la ventana actual.
    */
    @FXML
    private void playDummy(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/gameView.fxml"));
            Parent root = loader.load();
            gameViewController controller = loader.getController();
            controller.cargarControlesDummy();
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

    /**
    * Inicia el juego en modo "Avanzado" cuando se hace clic en el botón correspondiente.
    * Carga la vista del juego, configura los controles de la partida y muestra la escena.
    * Cierra la ventana actual.
    */
    @FXML
    private void playAdvanced(ActionEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/gameView.fxml"));
            Parent root = loader.load();
            gameViewController controller = loader.getController();
            controller.cargarControlesAdvance();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
            
            stage.setOnCloseRequest((t) -> controller.cerrar());
            Stage myStage = (Stage) this.advancedMode.getScene().getWindow();
            myStage.close();
        } catch (IOException ex) {
            Logger.getLogger(selectDifficultyController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
    * Cierra la ventana actual y abre la vista del menú principal.
    */
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