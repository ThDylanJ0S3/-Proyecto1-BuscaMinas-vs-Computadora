package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import Model.TableroMinesweeper;

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
        TableroMinesweeper game = new TableroMinesweeper(8,8,10);
        game.imprimir();
        System.out.println("-------------");
        game.imprimirpistas();
        System.out.println("-------------");    
    }

    @FXML
    private void playAdvanced(ActionEvent event) {
    }
    
}
