package Model;

/**
 *
 * @author Dylan Meza
 */
public class Pila {

    private Node head;
    private int size;

    /**
    * Constructor de la clase Pila. Inicializa la cabeza de la lista enlazada a null
    * y el tamaño a cero.
    */
    public Pila() {
        this.head = null;
        this.size = 0;
    }

    /**
    * Inserta un elemento en la cima de la pila.
    * @param data Elemento de tipo Casillas que se desea insertar.
    */
    public void push(Casillas data) {
        Node newNode = new Node();
        newNode.setData(data);
        newNode.setNext(head);
        head = newNode;
        size++;
    }

    /**
    * Remueve el elemento en la cima de la pila.
    * Si la pila está vacía lanza una excepción de tipo RuntimeException.
    */
    public void pop() {
        if (isEmpty()) {
            throw new RuntimeException("La pila esta vacia.");
        }
        head = head.getNext();
        size--;
    }

    /**
    * Retorna el elemento en la cima de la pila sin removerlo.
    * Si la pila está vacía lanza una excepción de tipo RuntimeException.
    * @return Elemento de tipo Casillas en la cima de la pila.
    */
    public Object peek() {
        if (!isEmpty()) {
            return head.getData();
        }   
        throw new RuntimeException("La pila esta vacia.");  
    }
    
    /**
    * Verifica si la pila está vacía.
    * @return true si la pila está vacía, false en caso contrario.
    */
    public boolean isEmpty() {
        return head == null;
    }

    /**
    * Retorna el tamaño actual de la pila.
    * @return Tamaño actual de la pila.
    */
    public int size() {
        return size;
    }
    
}