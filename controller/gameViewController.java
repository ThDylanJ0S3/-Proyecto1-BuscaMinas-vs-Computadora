package controller;

import Model.Casillas;
import Model.Lista;
import Model.Cronometro;
import Model.Pila;
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
import java.util.Random;
import javafx.event.ActionEvent;
import javafx.scene.input.KeyCode;

/**
 * FXML Controller class
 *
 * @author Dylan Meza
 */

public class gameViewController implements Initializable {
    // Variables de configuración del juego
    int numFilas = 8;
    int numColumnas = 8;
    int numMinas = 10;
    int pingEnMinas = 0;
    int cantBanderasMinas = numMinas*2;
    int numDePistas = 0;
    int intentos = 0;
    boolean dummyMode = false;
    boolean advanceMode = false;
    private boolean serialConect = false;
    
    // Estructuras de datos y clases auxiliares
    Pila pilaSugerencias = new Pila();
    Cronometro crono = new Cronometro(this);
    arduinoController control = new arduinoController();
    Button[][] botonesTablero;
    TableroMinesweeper tableroMinesweeper = new TableroMinesweeper(numFilas, numColumnas, numMinas);

    // Elementos visuales del juego
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
    @FXML
    private Button estadoConexion;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        crearTableroMinesweeper();
        crono.gameTimerInit();
        pingsMinas.setText("Minas Encontradas: "+ pingEnMinas);
        cantidadPings.setText("Banderas: "+ cantBanderasMinas);
        //Boton para conectar el control Arduino
        estadoConexion.setOnAction((x) -> {
            if(!serialConect){
                control.conectar("COM3");
                serialConect = true;
                estadoConexion.setText("Conectado");
                System.out.println("Control arduino conectado");
            } else {
                control.desconectar();
                serialConect = false;
                estadoConexion.setText("Desconectado");
                System.out.println("Control arduino desconectado");
            }
        });
        }
    
    /**
     * Crea el tablero del juego Minesweeper y establece los eventos asociados a su funcionamiento.
     */
    public void crearTableroMinesweeper(){

        //Establece el evento de partida perdida
        tableroMinesweeper.setEventoPartidaPerdida(new Consumer<Lista>(){
        @Override
        public void accept(Lista lista) {
        //Muestra las casillas con minas
        for (Casillas casillaConMina : lista.getCasillas()) {
        for (int i = 0; i < 1; i++) {
        control.enviar('M');
        break;
        }
        botonesTablero[casillaConMina.getPosFila()][casillaConMina.getPosColumna()].setText("*");
        }
        //Deshabilita el botón de visualización del juego y detiene el cronómetro
        visualizarJuego.setDisable(true);
        crono.detenerCronometro();
        //Muestra un mensaje indicando que se perdió la partida
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Perdiste");
        alert.setContentText("Presionaste una mina :(");
        alert.show();
        }
        });

        //Establece el evento de partida ganada
        tableroMinesweeper.setEventoPartidaGanada(new Consumer<Lista>(){
            @Override
            public void accept(Lista lista) {
                //Muestra las casillas con minas evitadas
                for (Casillas casillaConMina : lista.getCasillas()) {
                    botonesTablero[casillaConMina.getPosFila()][casillaConMina.getPosColumna()].setText(":)");
                }
                //Deshabilita el botón de visualización del juego y detiene el cronómetro
                visualizarJuego.setDisable(true);
                crono.detenerCronometro();
                //Muestra un mensaje indicando que se ganó la partida
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Ganaste");
                alert.setContentText("Evitaste todas las minas :)");
                alert.show();
            }
        });

        //Establece el evento de partida ganada por que el dummy actibo una mina
        tableroMinesweeper.setEventoPartidaGanadaDummy(new Consumer<Lista>(){
            @Override
            public void accept(Lista lista) {
                //Muestra las casillas con minas evitadas
                for (Casillas casillaConMina : lista.getCasillas()) {
                    botonesTablero[casillaConMina.getPosFila()][casillaConMina.getPosColumna()].setText(":)");
                }
                //Deshabilita el botón de visualización del juego y detiene el cronómetro
                visualizarJuego.setDisable(true);
                crono.detenerCronometro();
                //Muestra un mensaje indicando que se ganó la partida por que el dummy actibo una mina
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Ganaste");
                alert.setContentText("El enemigo activo una mina");
                alert.show();
                }
            });

        /**
        * Establece una acción que se ejecuta cuando se abre una casilla del tablero Minesweeper.
        * @param consumer Consumer que toma una Casillas como parámetro y no devuelve ningún valor. Se utiliza para enviar una señal al servidor de que se está abriendo una casilla, deshabilitar el botón correspondiente y mostrar el número de minas alrededor del botón.
        */
        tableroMinesweeper.setCasillaAbriendose(new Consumer<Casillas>(){
            @Override
            public void accept(Casillas t) {
                //Envía una señal al servidor de que se está abriendo una casilla
                for (int i = 0; i < 1; i++) {
                    control.enviar('B');
                    break;
                }
                botonesTablero[t.getPosFila()][t.getPosColumna()].setDisable(true);
                botonesTablero[t.getPosFila()][t.getPosColumna()].setText(t.getNumMinasAlrededor()==0?"":t.getNumMinasAlrededor()+"");
            }
        });
    //Imprime el tablero actual del juego Minesweeper.
    tableroMinesweeper.imprimir();
    System.out.println("--------");
    //Imprime las pistas del tablero actual del juego Minesweeper.
    tableroMinesweeper.imprimirpistas();
    System.out.println("--------");
    }
    
    /**
    * Carga los controles de la interfaz dummy y los agrega a visualizarJuego.
    */
    public void cargarControlesDummy(){
        int posX = 25; // La posición X de la primera casilla
        int posY = 25; // La posición Y de la primera casilla
        int ancho = 30; // El ancho de cada botón
        int alto = 30; // El alto de cada botón
        botonesTablero = new Button[numFilas][numColumnas]; // Inicializa el arreglo de botones
        // Crea cada botón, le asigna un identificador y su posición en la interfaz
        for (int i = 0; i < botonesTablero.length; i++) {
            for (int j = 0; j < botonesTablero[i].length; j++) {
                botonesTablero[i][j] = new Button();
                botonesTablero[i][j].setId(i+","+j);
                botonesTablero[i][j].setBorder(null);
                // Configura la posición y dimensiones de cada botón según su posición en la matriz de botones
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
                // Agrega el evento de presionar tecla Enter al botón
                botonesTablero[i][j].setOnKeyPressed(ent_e -> {
                    if (ent_e.getCode() == KeyCode.ENTER){
                        intentos++;
                        if(intentos==5){
                            numDePistas++;
                            intentos = 0;
                            sugerencias();
                        }
                        prsEnter(ent_e);
                        dummyMode = true;
                        if(dummyMode){
                            Random random = new Random();
                            int rI = random.nextInt(8);
                            int rJ = random.nextInt(8);
                            while(botonesTablero[rI][rJ].isDisable()){
                                rI = random.nextInt(8);
                                rJ = random.nextInt(8);
                            }
                            int moveDI = rI+1;
                            int moveDJ = rJ+1;
                            tableroMinesweeper.seleccionarCasillasDummy(rI, rJ);
                            botonesTablero[rI][rJ].fire();
                            dummyMode = false;
                            movimientoMaquina.setText("El dummy eligio la casilla"+moveDI+","+moveDJ);
                        }
                    }
                    });
                // Agrega el evento de clic al botón
                botonesTablero[i][j].setOnMouseClicked(e -> {
                    if (e.getButton() == MouseButton.PRIMARY){
                        intentos++;
                        if(intentos==5){
                            numDePistas++;
                            intentos = 0;
                            sugerencias();
                        }
                        btnClick(e);
                        dummyMode = true;
                        if(dummyMode){
                            Random random = new Random();
                            int rI = random.nextInt(8);
                            int rJ = random.nextInt(8);
                            while(botonesTablero[rI][rJ].isDisable()){
                                rI = random.nextInt(8);
                                rJ = random.nextInt(8);
                            }
                            int moveDI = rI+1;
                            int moveDJ = rJ+1;
                            tableroMinesweeper.seleccionarCasillasDummy(rI, rJ);
                            dummyMode = false;
                            movimientoMaquina.setText("El dummy eligio la casilla"+moveDI+","+moveDJ);
                        }
                    }
                    // Marca el boton con un simbolo para indicar que hay una mina
                    if (e.getButton() == MouseButton.SECONDARY) {
                        if(serialConect){
                            control.enviar('L'); // Envia una señal al control de arduino para que encienda el led
                        }
                        Button btn = (Button) e.getSource();
                        if(cantBanderasMinas!=0){
                            if(!btn.isDisable()){
                            btn.setText("X");
                            cantBanderasMinas--;
                            pingEnMinas++;
                            cantidadPings.setText("Banderas: "+ cantBanderasMinas);
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
       
    /**
    * Carga los controles de la interfaz avanzada y los agrega a visualizarJuego.
    */
    public void cargarControlesAdvance(){
        int posX = 25; // La posición X de la primera casilla
        int posY = 25; // La posición Y de la primera casilla
        int ancho = 30; // El ancho de cada botón
        int alto = 30; // El alto de cada botón
        botonesTablero = new Button[numFilas][numColumnas]; // Inicializa el arreglo de botones
        // Crea cada botón, le asigna un identificador y su posición en la interfaz
        for (int i = 0; i < botonesTablero.length; i++) {
            for (int j = 0; j < botonesTablero[i].length; j++) {
                botonesTablero[i][j] = new Button();
                botonesTablero[i][j].setId(i+","+j);
                botonesTablero[i][j].setBorder(null);
                // Configura la posición y dimensiones de cada botón según su posición en la matriz de botones
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

                // Agrega el evento de presionar tecla Enter al botón
                botonesTablero[i][j].setOnKeyPressed(ent_e -> {
                    if (ent_e.getCode() == KeyCode.ENTER){
                        intentos++;
                        if(intentos==5){
                            numDePistas++;
                            intentos = 0;
                            sugerencias(); // Genera una sugerencia
                        }
                        prsEnter(ent_e);
                        advanceMode = true;
                        if(advanceMode){
                            advancedMode();
                            advanceMode = false;
                        }
                    }
                });

                // Agrega el evento de clic al botón
                botonesTablero[i][j].setOnMouseClicked(e -> {
                    if (e.getButton() == MouseButton.PRIMARY){
                        intentos++;
                        if(intentos==5){
                            numDePistas++;
                            intentos = 0;
                            sugerencias(); // Genera una sugerencia
                        }
                        btnClick(e);
                        advanceMode = true;
                        if(advanceMode){
                            advancedMode();
                            advanceMode = false;
                        }
                    }
                    // Marca el boton con un simbolo para indicar que hay una mina
                    if (e.getButton() == MouseButton.SECONDARY) {
                        if(serialConect){
                            control.enviar('L'); // Envia una señal al control de arduino para que encienda el led
                        }
                        Button btn = (Button) e.getSource();
                        if(cantBanderasMinas!=0){
                            if(!btn.isDisable()){
                            btn.setText("X");
                            cantBanderasMinas--;
                            pingEnMinas++;
                            cantidadPings.setText("Banderas: "+ cantBanderasMinas);
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
    
    /**
    * Este método genera una sugerencia aleatoria para el jugador en forma de una casilla segura.
    * Si la casilla sugerida es una mina, entonces se intenta generar otra casilla segura de forma aleatoria.
    * La casilla segura se agrega a la pila de sugerencias y se actualiza el contador de pistas en la interfaz de usuario.
    */
    private void sugerencias(){
        Random random = new Random();
        int pI = random.nextInt(8);
        int pJ = random.nextInt(8);
        if(!botonesTablero[pI][pJ].isDisable()){
            Casillas casilla = tableroMinesweeper.getCasilla(pI, pJ);
            if(!casilla.isMina()){
                System.out.println("casilla pista"+ casilla.toString());
                pilaSugerencias.push(casilla);
                pilaDePistas.setText("Pistas: "+numDePistas);
            }else{
                sugerencias();
            }
        }
        pI = random.nextInt(8);
        pJ = random.nextInt(8);
    }
    
    /**
    * Método que se ejecuta al presionar el botón de "Seleccionar Pistas" en la interfaz gráfica.
    * Verifica si hay pistas disponibles y muestra una alerta con la información de la posición sugerida si es así.
    * Si no hay pistas, muestra una alerta indicándolo.
    * Si la posición sugerida ya ha sido seleccionada, la elimina de la pila y busca la siguiente sugerencia.
    * @param event El evento de acción generado por el botón de "Seleccionar Pistas".
    */
    @FXML
    private void seleccionarPistas(ActionEvent event){
        if(numDePistas != 0){
            Casillas casillaSugerencia = (Casillas) pilaSugerencias.peek();
            int posI = casillaSugerencia.getPosFila();
            int posJ = casillaSugerencia.getPosColumna();
            if(!pilaSugerencias.isEmpty()){
                while(botonesTablero[posI][posJ].isDisabled()){
                    pilaSugerencias.pop();
                    seleccionarPistas(event);
                }
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Alerta");
                alert.setContentText("("+posI+","+posJ+"): "+"es una posicion segura");
                pilaSugerencias.pop();
                numDePistas--;
                alert.show();
            }else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Alerta");
            alert.setContentText("No hay pistas");
            alert.show();
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Alerta");
            alert.setContentText("No hay pistas");
            alert.show();
        } 
    }
    
    /**
    * Realiza una jugada avanzada en el modo enemigo de Minesweeper.
    * Se genera una lista general, una lista de incertidumbre, una lista segura y una lista para repartir.
    * Luego se agregan casillas a la lista general y a la lista para repartir. Si la lista para repartir no está vacía,
    * se comprueba si la casilla es segura o de incertidumbre y se agrega a la lista correspondiente, eliminando la casilla de la lista de repartir.
    * Si la lista segura no está vacía, se selecciona una casilla segura al azar, se actualiza la GUI y se muestra en el texto de movimiento de la máquina.
    * De lo contrario, se selecciona una casilla de incertidumbre al azar, se actualiza la GUI y se muestra en el texto de movimiento de la máquina.
    * También se imprime información adicional en la consola para depuración.
    */
    public void advancedMode(){
        Lista listaGeneral = new Lista();
        Lista listaIncertidumbre = new Lista();
        Lista listaSegura = new Lista();
        Lista listaRepartir = new Lista();
        for (int i = 0; i < botonesTablero.length; i++) {
            for (int j = 0; j < botonesTablero[i].length; j++) {
                if(!botonesTablero[i][j].isDisabled()){
                    Casillas listCasillas = tableroMinesweeper.getCasilla(i, j);
                    listaGeneral.agregarFinal(listCasillas);
                    listaRepartir.agregarFinal(listCasillas);
                    if(!listaRepartir.isEmpty()){
                        Casillas casilla = (Casillas) listaRepartir.getCasilla();
                        if(tableroMinesweeper.verificarCasillas(i, j)<1){
                            listaIncertidumbre.agregarFinal(casilla);
                            listaRepartir.delete(casilla);
                        }else{
                            listaSegura.agregarFinal(casilla);
                            listaRepartir.delete(casilla);
                        }
                    }
                }
            }
        }
        if(!listaSegura.isEmpty()){
            Casillas casSegura = (Casillas) listaSegura.getCasilla();
            int posFi = casSegura.getPosFila();
            int posCo = casSegura.getPosColumna();
            tableroMinesweeper.seleccionarCasillasDummy(posFi, posCo);
            int posFEleg = posFi + 1;
            int poscEleg = posCo + 1;
            movimientoMaquina.setText("El enemigo eligio la casilla"+posFEleg+","+poscEleg);
        }else{
            Casillas casIncertidumbre = (Casillas) listaIncertidumbre.getCasilla();
            int posF = casIncertidumbre.getPosFila();
            int posC = casIncertidumbre.getPosColumna();
            tableroMinesweeper.seleccionarCasillasDummy(posF, posC);
            int posFEleg = posF + 1;
            int poscEleg = posC + 1;
            movimientoMaquina.setText("El enemigo eligio la casilla"+posFEleg+","+poscEleg);
        }
        System.out.println("----------");
        System.out.print("General: ");
        listaGeneral.imprimir();
        System.out.println("");
        System.out.println("");
        System.out.print("Segura: ");
        listaSegura.imprimir();
        System.out.println("");
        System.out.println("");
        System.out.print("Inser: ");
        listaIncertidumbre.imprimir();
        System.out.println("");
    }
    
    /**
    * Acción que se ejecuta al hacer click en un botón del tablero de juego.
    * Se obtiene la coordenada del botón clickeado y se llama al método 'seleccionarCasillas' de la clase 'TableroMinesweeper'
    * pasando como parámetros las coordenadas obtenidas.
    * @param event Evento del mouse
    */

    private void btnClick(javafx.scene.input.MouseEvent event) {
    Button btn = (Button) event.getSource();
    String[] cordenada = btn.getId().split(",");
    int posFila = Integer.parseInt(cordenada[0]);
    int posColumna = Integer.parseInt(cordenada[1]);
    tableroMinesweeper.seleccionarCasillas(posFila, posColumna);
    }
    
    /**
    * Acción que se ejecuta al presionar una tecla sobre un botón del tablero de juego.
    * Se obtiene la coordenada del botón y se llama al método 'seleccionarCasillas' de la clase 'TableroMinesweeper'
    * pasando como parámetros las coordenadas obtenidas.
    * @param event Evento del teclado
    */
    private void prsEnter(javafx.scene.input.KeyEvent event) {
    Button btn = (Button) event.getSource();
    String[] cordenada = btn.getId().split(",");
    int posFila = Integer.parseInt(cordenada[0]);
    int posColumna = Integer.parseInt(cordenada[1]);
    tableroMinesweeper.seleccionarCasillas(posFila, posColumna);
    }
    
    /**
    * Método para cerrar la ventana de juego y volver al menú principal.
    * Carga la vista 'mainMenu.fxml' y la muestra en una nueva ventana.
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
            java.util.logging.Logger.getLogger(gameViewController.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }
}