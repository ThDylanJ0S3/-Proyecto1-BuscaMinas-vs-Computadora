package Model;

/**
 *
 * @author Dylan Meza
 */
public class Node {
    private Node next; // Nodo siguiente
    private Object data; // Datos almacenados en el nodo

    /**
    * Constructor por defecto de la clase "Node".
    */
    public Node() {
        this.next = null;
        this.data = data;
    }

    /**
    * Devuelve el nodo siguiente.
    * 
    * @return el nodo siguiente.
    */
    public Node getNext() {
        return next;
    }

    /**
    * Establece el nodo siguiente.
    * 
    * @param next el nodo siguiente.
    */
    public void setNext(Node next) {
        this.next = next;
    }
    
    
    /**
     * Devuelve los datos almacenados en el nodo.
     * 
     * @return los datos almacenados en el nodo.
     */
    public Object getData() {
        return data;
    }
    
    /**
    * Establece los datos almacenados en el nodo.
    * 
    * @param data los datos a almacenar en el nodo.
    */
    public void setData(Object data) {
        this.data = data;
    }
    
}
