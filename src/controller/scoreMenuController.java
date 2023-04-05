/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
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
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Dylan Meza
 */
public class scoreMenuController implements Initializable {

    @FXML
    private Label puntaje2;
    @FXML
    private Label puntaje1;
    @FXML
    private Label puntaje3;
    @FXML
    private Label puntaje4;
    @FXML
    private Label puntaje5;
    @FXML
    private Button volver;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
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
