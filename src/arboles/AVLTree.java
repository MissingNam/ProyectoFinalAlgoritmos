package arboles;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class AVLTree {


    private Nodo root;

    private int altura(Nodo n)
    {
        if (n == null)
            return 0;
        else
            return n.getAltura();
    }

    private int getBalance(Nodo n)
    {
       if(n == null) {
           return 0;
       }else{
           return altura(n.getLeft())-altura(n.getRight());
       }

    }

    private Nodo rotateRight(Nodo n) {
        Nodo nod = n.getLeft();
        Nodo temp = n.getRight();

        nod.setRight(n);
        n.setLeft(temp);

        n.altura = 1 + Math.max(altura(n.left), altura(nod.right));
        nod.altura = 1 + Math.max(altura(n.left), altura(nod.right));

        return nod;
    }

    private Nodo rotateLeft(Nodo x) {
        Nodo y = x.right;
        Nodo temp = y.left;

        y.left = x;
        x.right = temp;

        x.altura = 1 + Math.max(altura(x.left), altura(x.right));
        y.altura = 1 + Math.max(altura(y.left), altura(y.right));

        return y;
    }

    // Insertar elemento
    public void insert(Producto producto) {
        root = insertRecursivo(root, producto);
    }

    private Nodo insertRecursivo(Nodo nodo, Producto producto) {
        if (nodo == null) return new Nodo(producto);

        if (producto.getID() < nodo.getKey())
            nodo.left = insertRecursivo(nodo.left, producto);
        else if (producto.getID() > nodo.getKey())
            nodo.right = insertRecursivo(nodo.right, producto);
        else
            return nodo; // no duplica

        // Actualiza altura
        nodo.altura = 1 + Math.max(altura(nodo.left), altura(nodo.right));

        // Verifica balance y aplica rotaciones (igual que antes)
        int balance = getBalance(nodo);

        // 4 casos
        if (balance > 1 && producto.getID() < nodo.left.getKey())
            return rotateRight(nodo);

        if (balance < -1 && producto.getID() > nodo.right.getKey())
            return rotateLeft(nodo);

        if (balance > 1 && producto.getID() > nodo.left.getKey()) {
            nodo.left = rotateLeft(nodo.left);
            return rotateRight(nodo);
        }

        if (balance < -1 && producto.getID() < nodo.right.getKey()) {
            nodo.right = rotateRight(nodo.right);
            return rotateLeft(nodo);
        }

        return nodo;
    }


    public Producto search(int key) {
        return searchRecursiva(root, key);
    }

    private Producto searchRecursiva(Nodo node, int key)
    {
        if (node == null) return null;
        if (key == node.getKey()) return node.getProducto();
        if (key < node.getKey()) return searchRecursiva(node.left, key);
        return searchRecursiva(node.right, key);
    }

    // Recorridos
    public void inorder() {
        inorderRec(root);
        System.out.println();
    }

    private void inorderRec(Nodo n) {
        if (n != null) {
            inorderRec(n.left);
            System.out.println(n.getProducto());
            inorderRec(n.right);
        }
    }

    public void preorder() {
        preorderRec(root);
        System.out.println();
    }

    private void preorderRec(Nodo n) {
        if (n != null) {
            System.out.println(n.getProducto());
            preorderRec(n.left);
            preorderRec(n.right);
        }
    }

    public void postorder() {
        postorderRec(root);
        System.out.println();
    }

    private void postorderRec(Nodo n) {
        if (n != null) {
            postorderRec(n.left);
            postorderRec(n.right);
            System.out.println(n.getProducto());
        }
    }

    public void cargarCSV(String nombreCSV) {
        String rutaCSV = "./CSVs/" + nombreCSV + ".csv";
        try (BufferedReader br = new BufferedReader(new FileReader(rutaCSV))) {
            String linea;
            boolean primeraLinea = true;
            while ((linea = br.readLine()) != null) {
                if (primeraLinea) {
                    primeraLinea = false;
                    continue;
                }

                String[] campos = linea.split(",");
                int id = Integer.parseInt(campos[0].trim());
                String nombre = campos[1].trim();
                int stock = Integer.parseInt(campos[2].trim());
                String ubicacion = campos[3].trim();

                Producto producto = new Producto(id, nombre, stock, ubicacion);
                this.insert(producto);
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo CSV: " + e.getMessage());
        }
    }



}