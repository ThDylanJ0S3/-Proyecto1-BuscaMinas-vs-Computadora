package Model;

import java.util.function.Consumer;

/**
 *
 * @author Dylan Meza
 */

public class Lista {
    
    private Node head;
    public int size;
    
    /**
     * Constructor de la clase Lista que inicializa la cabeza de la lista como null y el tamaño en 0.
     */
    public void Lista(){
        this.head = null;
        this.size = 0;
    }
    
    /**
     * Devuelve un array de objetos Casillas con los datos de cada nodo de la lista.
     * @return Un array de objetos Casillas con los datos de cada nodo de la lista.
     */
    public Casillas[] getCasillas() {
        Casillas[] casillas = new Casillas[size];
        Node auxNode = head;
        int i = 0;
        while (auxNode != null) {
            casillas[i] = (Casillas) auxNode.getData();
            auxNode = auxNode.getNext();
            i++;
        }
        return casillas;
    }
    
    /**
     * Devuelve el objeto Casillas del último nodo de la lista.
     * @return El objeto Casillas del último nodo de la lista.
     */
    public Object getCasilla() {
        Casillas casillas = new Casillas();
        Node auxNode = head;
        int i = 0;
        while (auxNode != null) {
            casillas = (Casillas) auxNode.getData();
            auxNode = auxNode.getNext();
            i++;
        }
        return casillas;
    }
    
    /**
     * Verifica si la lista está vacía.
     * @return true si la lista está vacía, false de lo contrario.
     */
    public boolean isEmpty(){
        return this.head == null;
    }
    
    /**
     * Devuelve el tamaño de la lista.
     * @return El tamaño de la lista.
     */
    public int size(){
        return this.size;
    }
    
    /**
     * Agrega un nuevo nodo con el objeto Casillas pasado como parámetro al final de la lista.
     * @param data El objeto Casillas a agregar al final de la lista.
     */
    public void agregarFinal(Casillas data){
        Node newNode = new Node();
        newNode.setData(data);
        
        if(isEmpty()){
            head = newNode;
        }
        else{
            Node auxNode = head;
            while(auxNode.getNext()!=null){
                auxNode = auxNode.getNext();
            }
            auxNode.setNext(newNode);
        }
        size++;
    }
    
    /**
     * Busca el nodo en la posición indicada y devuelve su objeto Casillas.
     * @param pos La posición del nodo a buscar.
     * @return El objeto Casillas del nodo en la posición indicada.
     */
    public Object search(int pos) {
        Node auxNode = head;
        while (auxNode != null) {
            for (int i = 0; i < pos; i++) {
                auxNode = auxNode.getNext();  
            }
            auxNode.getData();
        }
        return null;
    }
    
    /**
     * Elimina el primer nodo de la lista que contiene el objeto Casillas pasado como parámetro.
     * Si no se encuentra el objeto en la lista, no hace nada.
     * @param data El objeto Casillas a eliminar de la lista.
     */
    public void delete(Casillas data) {
        Node auxNode = head;
        Node prevNode = null;
        while (auxNode != null) {
            if (auxNode.getData().equals(data)) {
                if (prevNode == null) {
                    head = auxNode.getNext();
                } else {
                    prevNode.setNext(auxNode.getNext());
                }
                size--;
                return;
            }
            prevNode = auxNode;
            auxNode = auxNode.getNext();
        }
    }
    
    /**
    * Aplica el consumidor pasado como parametro a cada elemento de la lista enlazada.
    * @param action el consumidor a aplicar a cada elemento de la lista enlazada.
    */
    public void forEach(Consumer<Casillas> action) {
    Node auxNode = head;
    while (auxNode != null) {
        action.accept((Casillas) auxNode.getData());
        auxNode = auxNode.getNext();
        }
    }

    /** 
    * Imprime el contenido de la lista enlazada.
    * Si la lista esta vacia, no se imprime nada.
    */
    public void imprimir(){
        if (!isEmpty()){
            int i = 0;
            Node auxNode = head;
            while(auxNode!=null){
                System.out.print(i+"[" + auxNode.getData().toString() + "]" + " -> ");
                auxNode = auxNode.getNext();
                i++;
            }           
        }
    }
}
