package Model;

import controller.arduinoController;
import java.util.function.Consumer;

/**
 *
 * @author Dylan Meza
 */
public class TableroMinesweeper {
    
    Casillas[][] tablero;
    
    private int numF;
    private int numC;
    private int numMina;
    private int casillasAbiertas;
    private arduinoController arduino = new arduinoController();
    private Consumer<Lista> eventoPartidaPerdida;
    private Consumer<Lista> eventoPartidaGanada;
    private Consumer<Lista> eventoPartidaGanadaDummy;
    private Consumer<Casillas> casillaAbriendose;

    /** 
    * Constructor de la clase TableroMinesweeper.
    * @param numF Número de filas del tablero.
    * @param numC Número de columnas del tablero.
    * @param numMina Número de minas que habrá en el tablero.
    */
    public TableroMinesweeper(int numF, int numC, int numMina) {
        this.numF = numF;
        this.numC = numC;
        this.numMina = numMina;
        this.inicializarCasillas();
    }
    
    /**
    * Inicializa todas las casillas del tablero.
    */
    public void inicializarCasillas(){
        tablero = new Casillas[this.numF][this.numC];
        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero[i].length; j++) {
                tablero[i][j] = new Casillas(i, j);
            }
            
        }
        generarMinas();
    }
    
    /** 
    * Devuelve la casilla en la posición (i,j) del tablero.
    * @param i Fila de la casilla deseada.
    * @param j Columna de la casilla deseada.
    * @return La casilla en la posición (i,j) del tablero.
    */
    public Casillas getCasilla(int i, int j){
        return tablero[i][j];
    }
    
    /** 
    * Genera las minas en el tablero de forma aleatoria.
    */
    public void generarMinas(){
        int minasGen=0;
        while(minasGen!=numMina){
            int posTmpF = (int)(Math.random()*tablero.length);
            int posTmpC = (int)(Math.random()*tablero[0].length);
            if(!tablero[posTmpF][posTmpC].isMina()){
                tablero[posTmpF][posTmpC].setMina(true);
                minasGen++;
            }

        }
        actualizarNMinas();
    }
    
    /**
    * Imprime el tablero en consola con asteriscos para indicar la posición de las minas.
    */
    public void imprimir(){
        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero[i].length; j++) {
                System.out.print(tablero[i][j].isMina()?"*":"0");
            }
            System.out.println("");
        }
    }
    
    /**
    * Imprime en consola las pistas de cada casilla.
    */
    public void imprimirpistas(){
        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero[i].length; j++) {
                System.out.print(tablero[i][j].getNumMinasAlrededor());
            }
            System.out.println("");
        }
    }
    
    /** 
    * Método que devuelve una lista con las casillas que están alrededor de una casilla dada.
    * @param posFila La posición de la fila de la casilla dada.
    * @param posColum La posición de la columna de la casilla dada.
    * @return Una lista con las casillas que están alrededor de la casilla dada.
    */
    private Lista obtenerCasillasAlrededor(int posFila, int posColum){
        Lista listaCasillas = new Lista();
        for (int i = 0; i < 8; i++){
            int tmpPosF = posFila;
            int tmpPosC = posColum;
            switch(i){
                case 0: tmpPosF--;tmpPosC--;break;
                case 1: tmpPosF--;break;
                case 2: tmpPosF--;tmpPosC++;break;
                case 3: tmpPosC--;break;   
                case 4: tmpPosC++;break;   
                case 5: tmpPosF++;tmpPosC--;break;  
                case 6: tmpPosF++;break;
                case 7: tmpPosF++;tmpPosC++;break;
            }
            if(tmpPosF>=0 && tmpPosF<this.tablero.length && tmpPosC>=0 && tmpPosC<this.tablero[0].length){
                listaCasillas.agregarFinal(tablero[tmpPosF][tmpPosC]);
            }
        }
        return listaCasillas;
    }
    
    /**
    * Método que verifica el número de minas alrededor de una casilla dada.
    * @param i La posición de la fila de la casilla dada.
    * @param j La posición de la columna de la casilla dada.
    * @return El número de minas alrededor de la casilla dada.
    */
    public int verificarCasillas(int i, int j) {
    int minasAlrededor = 0;
    Lista casillasAlrededor = obtenerCasillasAlrededor(i, j);
    for (Casillas casilla : casillasAlrededor.getCasillas()) {
        if (casilla.isMina()) {
            minasAlrededor++;
        }
    }
    return minasAlrededor;
}
    
    /**
    * Método que devuelve una lista con las casillas que contienen minas en el tablero.
    * @return Una lista con las casillas que contienen minas en el tablero.
    */
    public Lista casillasConMinas(){
        Lista casillasConMinas = new Lista();
        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero[i].length; j++) {
                if(tablero[i][j].isMina()){
                casillasConMinas.agregarFinal(tablero[i][j]);
                }
            }

        }
        return casillasConMinas;
    }
    
    /**
    * Método que selecciona una casilla en el tablero y realiza las acciones correspondientes.
    * @param posF La posición de la fila de la casilla seleccionada.
    * @param posC La posición de la columna de la casilla seleccionada.
    */
    public void seleccionarCasillas(int posF, int posC){
        casillaAbriendose.accept(this.tablero[posF][posC]);
        if(this.tablero[posF][posC].isMina()){
            eventoPartidaPerdida.accept(casillasConMinas());
        } 
        else if(this.tablero[posF][posC].getNumMinasAlrededor()==0){
            casillaAbierta(posF,posC);
            Lista casillasVacias = obtenerCasillasAlrededor(posF, posC);
            for(Casillas casilla : casillasVacias.getCasillas()){
                if(!casilla.isAbierta()){
                    seleccionarCasillas(casilla.getPosFila(),casilla.getPosColumna());
                    
                }
            }
        }
        else{
            casillaAbierta(posF,posC);
        }
        if(partidaGanada()){
            eventoPartidaGanada.accept(casillasConMinas());
        }
    }
    
    /** 
    * Selecciona las casillas a abrir en el tablero a partir de la posición dada y ejecuta las acciones correspondientes
    * como actualizar el contador de casillas abiertas y verificar si se ha ganado la partida.
    * Si la casilla es una mina, se ejecuta el evento correspondiente de partida ganada.
    * Si la casilla no es una mina y no tiene minas alrededor, se abre la casilla y se buscan las casillas adyacentes vacías para abrirlas.
    * Si la casilla no es una mina y tiene al menos una mina alrededor, simplemente se abre la casilla.
    * @param posF la posición de fila de la casilla seleccionada
    * @param posC la posición de columna de la casilla seleccionada
    */
    public void seleccionarCasillasDummy(int posF, int posC){
        casillaAbriendose.accept(this.tablero[posF][posC]);
        if(this.tablero[posF][posC].isMina()){
            eventoPartidaGanadaDummy.accept(casillasConMinas());
        } 
        else if(this.tablero[posF][posC].getNumMinasAlrededor()==0){
            casillaAbierta(posF,posC);
            Lista casillasVacias = obtenerCasillasAlrededor(posF, posC);
            for(Casillas casilla : casillasVacias.getCasillas()){
                if(!casilla.isAbierta()){
                    seleccionarCasillas(casilla.getPosFila(),casilla.getPosColumna());    
                }
            }
        }
        else{
           casillaAbierta(posF,posC);
        }
        if(partidaGanada()){
            eventoPartidaGanadaDummy.accept(casillasConMinas());
        }
    }

    /**
    * Establece el evento a ejecutar cuando se gana la partida.
    * @param eventoPartidaGanada el evento a ejecutar
    */
    public void setEventoPartidaGanada(Consumer<Lista> eventoPartidaGanada) {
        this.eventoPartidaGanada = eventoPartidaGanada;
    }
    
    /**
    * Establece el evento a ejecutar cuando se gana la partida en modo "dummy".
    * @param eventoPartidaGanadaDummy el evento a ejecutar
    */
    public void setEventoPartidaGanadaDummy(Consumer<Lista> eventoPartidaGanadaDummy) {
        this.eventoPartidaGanadaDummy = eventoPartidaGanadaDummy;
    }

    /**
    * Establece el evento a ejecutar cuando se pierde la partida.
    * @param eventoPartidaPerdida el evento a ejecutar
    */
    public void setEventoPartidaPerdida(Consumer<Lista> eventoPartidaPerdida) {
        this.eventoPartidaPerdida = eventoPartidaPerdida;
    }
    
    /**
    * Establece el evento a ejecutar cuando se abre una casilla.
    * @param casillaAbriendose el evento a ejecutar
    */
    public void setCasillaAbriendose(Consumer<Casillas> casillaAbriendose) {
        this.casillaAbriendose = casillaAbriendose;
    }
    
    /**
    * Actualiza el número de minas alrededor de cada casilla en el tablero.
    */
    public void actualizarNMinas(){
        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero[i].length; j++) {
                if(tablero[i][j].isMina()){
                    Lista miAlrededor = obtenerCasillasAlrededor(i, j);
                    miAlrededor.forEach((c)->c.incrementarNumeroMinasAlrededor());                  
                }
            }
        }
    }
    
    /**
    * Comprueba si se han abierto todas las casillas que no contienen una mina.
    * @return true si todas las casillas que no contienen una mina han sido abiertas, false en caso contrario.
    */
    public boolean partidaGanada(){
        return casillasAbiertas>=(numF*numC)-numMina;
    }
    
    /**
    * Comprueba si la casilla en la posición indicada ha sido abierta.
    * @param posF la fila de la casilla.
    * @param posC la columna de la casilla.
    */
    public void casillaAbierta(int posF, int posC){
        if(!this.tablero[posF][posC].isAbierta()){
            casillasAbiertas++;
            this.tablero[posF][posC].setAbierta(true);
        }
    }
  
}