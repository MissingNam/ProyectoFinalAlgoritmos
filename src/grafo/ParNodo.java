package grafo;

public class ParNodo implements Comparable<ParNodo> {
    public int nodo;
    public double distancia;

    public ParNodo(int nodo, double distancia) {
        this.nodo = nodo;
        this.distancia = distancia;
    }

    @Override
    public int compareTo(ParNodo otro) {
        return Double.compare(this.distancia, otro.distancia);
    }
}