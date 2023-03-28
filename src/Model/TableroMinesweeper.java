package Model;

/**
 *
 * @author Dylan Meza
 */
public class TableroMinesweeper {
    
    Casillas[][] tablero;
    
    private int numF;
    private int numC;
    private int numMina;
    private Lista numerarCasillas;

    public TableroMinesweeper(int numF, int numC, int numMina) {
        this.numF = numF;
        this.numC = numC;
        this.numMina = numMina;
        this.inicializarCasillas();
    }
    
    public void inicializarCasillas(){
        tablero = new Casillas[this.numF][this.numC];
        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero[i].length; j++) {
                tablero[i][j] = new Casillas(i, j);
            }
            
        }
        generarMinas();
    }
    
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
    
    public void imprimir(){
        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero[i].length; j++) {
                System.out.print(tablero[i][j].isMina()?"*":"0");
            }
            System.out.println("");
        }
    }
    
    public void imprimirpistas(){
        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero[i].length; j++) {
                System.out.print(tablero[i][j].getNumMinasAlrededor());
            }
            System.out.println("");
        }
    }
    
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
        listaCasillas.imprimir();
        return listaCasillas;
    }
        
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
    
}
