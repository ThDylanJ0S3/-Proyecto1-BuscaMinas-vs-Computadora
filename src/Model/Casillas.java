package Model;

/**
 *
 * @author Dylan Meza
 */
public class Casillas {
    private int posFila; // posición de la casilla en la fila
    private int posColumna; // posición de la casilla en la columna
    private boolean mina; // indica si la casilla tiene una mina
    private int numMinasAlrededor; // número de minas en las casillas adyacentes
    private boolean abierta; // indica si la casilla está abierta

    /**
    *Constructor de la clase Casillas.
    *@param posFila posición de la casilla en la fila.
    *@param posColumna posición de la casilla en la columna. 
    */
    public Casillas(int posFila, int posColumna) {
        this.posFila = posFila;
        this.posColumna = posColumna;
    }

    /**
    *Constructor vacío de la clase Casillas.
    */
    public Casillas() {
    
    }
    
    /**
    * Retorna una representación de la casilla en formato String.
    * Se usa principalmente para imprimir la matriz del tablero.
    * @return una cadena con la posición de la casilla en formato [fila,columna].
    */

    @Override
    public String toString() {
        posFila = posFila+1;
        posColumna = posColumna+1;
        return "[" + posFila + "," + posColumna + "]";
    }
    
    /**
    * Retorna la posición de la casilla en la fila.
    * @return la posición de la casilla en la fila.
    */
    public int getPosFila() {
        return posFila;
    }

    /**
    * Establece la posición de la casilla en la fila.
    * @param posFila la posición de la casilla en la fila.
    */
    public void setPosFila(int posFila) {
        this.posFila = posFila;
    }

    /**
    * Retorna la posición de la casilla en la columna.
    * @return la posición de la casilla en la columna.
    */
    public int getPosColumna() {
        return posColumna;
    }

    /**
    * Establece la posición de la casilla en la columna.
    * @param posColumna la posición de la casilla en la columna.
    */
    public void setPosColumna(int posColumna) {
        this.posColumna = posColumna;
    }

    /**
    * Indica si la casilla tiene una mina.
    * @return true si la casilla tiene una mina, false en caso contrario.
    */
    public boolean isMina() {
        return mina;
    }

    /**
    *Establece si la casilla tiene una mina.
    *@param mina true si la casilla tiene una mina, false en caso contrario.
    */
    public void setMina(boolean mina) {
        this.mina = mina;
    }

    /**
    * Retorna el número de minas en las casillas adyacentes.
    * @return el número de minas en las casillas adyacentes.
    */
    public int getNumMinasAlrededor() {
        return numMinasAlrededor;
    }

    /**
    * Cambia el número de minas en las casillas adyacentes.
    * @param numMinasAlrededor el nuevo número de minas en las casillas adyacentes.
    */
    public void setNumMinasAlrededor(int numMinasAlrededor) {
        this.numMinasAlrededor = numMinasAlrededor;
    }
    
    /**
    * Incrementa el número de minas en las casillas adyacentes en uno.
    */
    public void incrementarNumeroMinasAlrededor(){
        this.numMinasAlrededor++;
    }

    /**
    * Retorna si la casilla está abierta o no.
    * @return true si la casilla está abierta, false en caso contrario.
    */
    public boolean isAbierta() {
        return abierta;
    }

    /**
    * Cambia si la casilla está abierta o no.
    * @param abierta true si la casilla está abierta, false en caso contrario.
    */
    public void setAbierta(boolean abierta) {
        this.abierta = abierta;
    }
 
}
