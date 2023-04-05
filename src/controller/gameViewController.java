package controller;

import Model.Casillas;
import Model.Lista;
import Model.Cronometro;
import Model.TableroMinesweeper;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Dylan Meza
 */
public class gameViewController implements Initializable {
    
    int numFilas = 8;
    int numColumnas = 8;
    int numMinas = 10;
    int pingEnMinas = 0;
    int cantBanderasMinas = numMinas*2;
    Cronometro crono = new Cronometro(this);
    
    Button[][] botonesTablero;
    
    TableroMinesweeper tableroMinesweeper = new TableroMinesweeper(numFilas, numColumnas, numMinas);

    @FXML
    private Pane visualizarJuego;
    @FXML
    public Label cronometro;
    @FXML
    private Label cantidadPings;
    @FXML
    private Label pingsMinas;
    @FXML
    private Button pilaDePistas;
    @FXML
    private Label movimientoMaquina;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        crearTableroMinesweeper();
        crono.gameTimerInit();
        pingsMinas.setText("Minas Encontradas: "+ pingEnMinas);
        cantidadPings.setText("Banderas: "+ cantBanderasMinas);
        }
    
    public void crearTableroMinesweeper(){
        tableroMinesweeper.setEventoPartidaPerdida(new Consumer<Lista>(){
        @Override
            public void accept(Lista lista) {
                for (Casillas casillaConMina : lista.getCasillas()) {
                    botonesTablero[casillaConMina.getPosFila()][casillaConMina.getPosColumna()].setText("*");
                }
                visualizarJuego.setDisable(true);
                crono.detenerCronometro();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Perdiste");
                alert.setContentText("Presionaste una mina :(");
                alert.show();
            }
        });
        tableroMinesweeper.setEventoPartidaGanada(new Consumer<Lista>(){
        @Override
            public void accept(Lista lista) {
                for (Casillas casillaConMina : lista.getCasillas()) {
                    botonesTablero[casillaConMina.getPosFila()][casillaConMina.getPosColumna()].setText(":)");
                }
                visualizarJuego.setDisable(true);
                crono.detenerCronometro();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Ganaste");
                alert.setContentText("Evitaste todas las minas :)");
                alert.show();
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
    
    public void cargarControlesDummy(){
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
                    if (e.getButton() == MouseButton.PRIMARY){
                        btnClick(e);
                    }
                    if (e.getButton() == MouseButton.SECONDARY) {
                        Button btn = (Button) e.getSource();
                        if(cantBanderasMinas!=0){
                            if(!btn.isDisable()){
                            btn.setText("M");
                            cantBanderasMinas--;
                            pingEnMinas++;
                            pingsMinas.setText("Minas Encontradas: "+pingEnMinas);   
                        }}else{
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Alerta");
                            alert.setContentText("No te quedan mas pings");
                            alert.show();
                            }
                        }
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