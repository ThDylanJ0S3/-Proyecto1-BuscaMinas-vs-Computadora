package controller;

import Model.Casillas;
import Model.Lista;
import Model.TableroMinesweeper;
import com.sun.javafx.logging.PlatformLogger.Level;
import java.io.IOException;
import java.lang.System.Logger;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Dilan
 */
public class gameViewController implements Initializable {
    
    int numFilas = 8;
    int numColumnas = 8;
    int numMinas = 14;
    
    Button[][] botonesTablero;
    
    TableroMinesweeper tableroMinesweeper = new TableroMinesweeper(numFilas, numColumnas, numMinas);

    @FXML
    private Pane visualizarJuego;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        crearTableroMinesweeper();
        cargarControles();
    }    
    
    public void crearTableroMinesweeper(){
        tableroMinesweeper.setEventoPartidaPerdida(new Consumer<Lista>(){
        @Override
            public void accept(Lista lista) {
                for (Casillas casillaConMina : lista.getCasillas()) {
                    botonesTablero[casillaConMina.getPosFila()][casillaConMina.getPosColumna()].setText("*");
                }
            }
        });
        tableroMinesweeper.setEventoPartidaGanada(new Consumer<Lista>(){
        @Override
            public void accept(Lista lista) {
                for (Casillas casillaConMina : lista.getCasillas()) {
                    botonesTablero[casillaConMina.getPosFila()][casillaConMina.getPosColumna()].setText("..");
                }
            }
        });
        tableroMinesweeper.setCasillaAbriendose(new Consumer<Casillas>(){
            @Override
            public void accept(Casillas t) {
                botonesTablero[t.getPosFila()][t.getPosColumna()].setDisable(true);
                botonesTablero[t.getPosFila()][t.getPosColumna()].setText(t.getNumMinasAlrededor()==0?"":t.getNumMinasAlrededor()+"");
            }
            
        });
        tableroMinesweeper.imprimir();
        System.out.println("--------");
        tableroMinesweeper.imprimirpistas();
    }
    
    private void cargarControles(){
        int posX = 25;
        int posY = 25;
        int ancho = 30;
        int alto = 30;
        botonesTablero = new Button[numFilas][numColumnas];
        for (int i = 0; i < botonesTablero.length; i++) {
            for (int j = 0; j < botonesTablero[i].length; j++) {
                botonesTablero[i][j] = new Button();
                botonesTablero[i][j].setId(i+","+j);
                botonesTablero[i][j].setBorder(null);
                if(i==0 && j==0){
                    botonesTablero[i][j].setLayoutX(posX);
                    botonesTablero[i][j].setLayoutY(posY);
                    botonesTablero[i][j].setPrefWidth(ancho);
                    botonesTablero[i][j].setPrefHeight(alto);
                }else if(i==0 && j!=0){
                    botonesTablero[i][j].setLayoutX(botonesTablero[i][j-1].getLayoutX()+botonesTablero[i][j-1].getPrefWidth());
                    botonesTablero[i][j].setLayoutY(posY);
                    botonesTablero[i][j].setPrefWidth(ancho);
                    botonesTablero[i][j].setPrefHeight(alto);
                }else{
                    botonesTablero[i][j].setLayoutX(botonesTablero[i-1][j].getLayoutX());
                    botonesTablero[i][j].setLayoutY(botonesTablero[i-1][j].getLayoutY()+botonesTablero[i-1][j].getPrefHeight());
                    botonesTablero[i][j].setPrefWidth(ancho);
                    botonesTablero[i][j].setPrefHeight(alto);
                }
                botonesTablero[i][j].setOnMouseClicked(e -> {
                    btnClick(e);
                });

                 
                visualizarJuego.getChildren().add(botonesTablero[i][j]);
            }
        }
    }
    
    private void btnClick(javafx.scene.input.MouseEvent event) {
    Button btn = (Button) event.getSource();
    String[] cordenada = btn.getId().split(",");
    int posFila = Integer.parseInt(cordenada[0]);
    int posColumna = Integer.parseInt(cordenada[1]);
    tableroMinesweeper.seleccionarCasillas(posFila, posColumna);
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
            java.util.logging.Logger.getLogger(gameViewController.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        
    }
}