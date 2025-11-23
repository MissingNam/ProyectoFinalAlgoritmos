package menu;

import arboles.AVLTree;
import grafo.Grafo;
import arboles.Producto;
import java.util.Scanner;

public class Menu {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        AVLTree arbol = new  AVLTree();
        Grafo grafo = new Grafo();

        boolean salir = false;

        while (!salir) {
            System.out.println("\n--- MENU PRINCIPAL ---");
            System.out.println("1. Cargar CSV de inventario");
            System.out.println("2. Cargar CSV de rutas");
            System.out.println("3. Mostrar inventario (AVL Inorden)");
            System.out.println("4. Buscar producto por ID");
            System.out.println("5. Dijkstra desde un centro de distribución");
            System.out.println("6. Floyd Warshall desde algun lado");
            System.out.println("7. Prim desde la raiz");
            System.out.println("8. Mostar Preorden del Arbol");
            System.out.println("9. Mostar Inorden del Arbol");
            System.out.println("10. Mostar Postorden del Arbol");
            System.out.println("11. Salir");
            System.out.print("Selecciona una opción: ");
            int opcion = sc.nextInt();
            sc.nextLine(); // limpiar buffer

            switch (opcion) {
                case 1:
                    System.out.print("Nombre del CSV de inventario (sin .csv): ");
                    String csvInventario = sc.nextLine();
                    arbol.cargarCSV(csvInventario);
                    break;

                case 2:
                    System.out.print("Nombre del CSV de rutas (sin .csv): ");
                    String csvRutas = sc.nextLine();
                    try {
                        grafo.cargarCSV(csvRutas);
                        System.out.println("Rutas cargadas correctamente.");
                    } catch (Exception e) {
                        System.out.println("Error al cargar CSV: " + e.getMessage());
                    }
                    break;

                case 3:
                    System.out.println("\n--- Inventario (Inorden) ---");
                    arbol.inorder();
                    break;

                case 4:
                    System.out.print("Ingresa ID del producto a buscar: ");
                    int id = sc.nextInt();
                    Producto producto = arbol.search(id);
                    if (producto != null) {
                        System.out.println("Producto encontrado:\n" + producto);
                    } else {
                        System.out.println("Producto no encontrado.");
                    }
                    break;

                case 5:
                    System.out.print("Ingresa centro de distribución de origen: ");
                    sc.nextLine();
                    String origen = sc.nextLine();
                    System.out.println("\n--- Dijkstra ---");
                    grafo.dijkstra(origen);
                    break;

                case 6:
                    System.out.println("\n--- FloydWarshall ---");
                    double[][] captura = grafo.FloydWarshall();
                    grafo.imprimirMatrizFloydWarshall(captura);
                    break;

                case 7:
                    System.out.println("\n--- Prim ---");
                    grafo.Prim();
                    break;

                case 8:
                    System.out.println("\n--- Preorden ---");
                    arbol.preorder();
                    break;

                case 9:
                    System.out.println("\n--- Inorden ---");
                    arbol.inorder();
                    break;

                case 10:
                    System.out.println("\n--- Posorden ---");
                    arbol.postorder();
                    break;

                case 11:
                    salir = true;
                    System.out.println("Saliendo del sistema...");
                    break;

                default:
                    System.out.println("Opción inválida.");
            }
        }

        sc.close();
    }
}
