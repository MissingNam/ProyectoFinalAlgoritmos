package arboles;

public class Nodo {
    public Producto key;
    public int altura;
    public Nodo left;
    public Nodo right;

    public Nodo(Producto llave) {
        this.key = llave;
        this.altura = 1;
    }

    public int getKey() {
        return key.getID();
    }

    public void setKey(Producto llave) {
        this.key = llave;
    }
    public int getAltura() {
        return altura;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }
    public Nodo getLeft() {
        return left;
    }

    public void setLeft(Nodo left) {
        this.left = left;
    }

    public Nodo getRight() {
        return right;
    }

    public void setRight(Nodo right) {
        this.right = right;
    }

    public Producto getProducto()
    {
        return key;
    }
}
