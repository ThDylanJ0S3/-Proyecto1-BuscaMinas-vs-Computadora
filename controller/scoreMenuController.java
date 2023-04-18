package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
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
public class scoreMenuController implements Initializable {

    @FXML
    private Button volver;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    /**
    * Cierra la vista actual y carga la vista del menú principal.
    * Este método se ejecuta al hacer clic en el botón "volver".
    */
    @FXML
    public void cerrar(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/mainMenu.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
            
            Stage myStage = (Stage) this.volver.getScene().getWindow();
            myStage.close();
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(scoreMenuController.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        
    }
}
