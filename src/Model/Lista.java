package Model;

import java.util.function.Consumer;

/**
 *
 * @author Dylan Meza
 */
public class Lista {
    private Node head;
    public int size;
    
    public void Lista(){
        this.head = null;
        this.size = 0;
    }
    
    public boolean isEmpty(){
        return this.head == null;
    }
    
    public int size(){
        return this.size;
    }
    
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
    
    public void forEach(Consumer<Casillas> action) {
    Node auxNode = head;
    while (auxNode != null) {
        action.accept((Casillas) auxNode.getData());
        auxNode = auxNode.getNext();
    }
}

    public void imprimir(){
        if (isEmpty()){
            Node auxNode = head;
            int i=0;
            while(auxNode!=null){
                System.out.println(i + "[" + auxNode.getData().toString() + "]" + " -> ");
                auxNode = auxNode.getNext();
                i++;
            }
        }
    }
}
