package grafo;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;


public class Grafo {

    public int V;
    public ArrayList<ArrayList<Arista>> listaAdyacencia;
    public HashMap<String, Integer> nombreIdx;
    public ArrayList<String> IdxNombre;

    public Grafo() {
        this.V = 0;
        this.listaAdyacencia = new ArrayList<>();
        this.nombreIdx = new HashMap<>();
        this.IdxNombre = new ArrayList<>();
    }

    public void addNode(String nombre) {
        if (!nombreIdx.containsKey(nombre)) {
            nombreIdx.put(nombre, V);
            IdxNombre.add(nombre);
            listaAdyacencia.add(new ArrayList<>());
            V++;
        } else {
            System.out.println("Ya existe un nodo con el mismo nombre");
        }
    }

    public void addEdge(String origen, String destino, double costo) {
        if (!nombreIdx.containsKey(origen)) {
            addNode(origen);
        }
        if (!nombreIdx.containsKey(destino)) {
            addNode(destino);
        }

        int u = nombreIdx.get(origen);
        int d = nombreIdx.get(destino);

        listaAdyacencia.get(u).add(new Arista(costo, d));
    }


    public double[] dijkstra(String origen)
    {
        double[] distancia  = new double[V];
        boolean[] visitados =  new boolean[V];
        for(int u = 0; u < V; u++)
        {
            distancia[u] = Double.POSITIVE_INFINITY;
            visitados[u] = false;
        }
        distancia[nombreIdx.get(origen)] = 0;

        PriorityQueue<ParNodo> pq = new PriorityQueue<>();
        pq.add(new ParNodo(nombreIdx.get(origen), 0));

        while (!pq.isEmpty()) {
            ParNodo actual = pq.poll(); // nodo con distancia mínima
            int u = actual.nodo;

            if (visitados[u]) continue; // si ya lo procesamos, saltar
            visitados[u] = true; // ahora sí lo marcamos como visitado

            // recorremos todos sus vecinos
            for (Arista arista : listaAdyacencia.get(u)) {
                int v = arista.destino;
                double peso = arista.peso;

                if (!visitados[v] && distancia[u] + peso < distancia[v]) {
                    distancia[v] = distancia[u] + peso;
                    pq.add(new ParNodo(v, distancia[v]));
                }
            }
        }

        for (int i = 0; i < V; i++) {
            System.out.println(IdxNombre.get(i) + " -> " + distancia[i]);
        }
        return distancia;
    }

    private void dijkstraRecursivo(int u, double[] distancia, boolean[] visitados) {
        visitados[u] = true;

        for (Arista arista : listaAdyacencia.get(u)) {
            int v = arista.destino;
            double peso = arista.peso;
            if (distancia[u] + peso < distancia[v]) {
                distancia[v] = distancia[u] + peso;
            }
        }

        // buscar siguiente nodo no visitado con distancia mínima
        int siguiente = -1;
        double minDist = Double.POSITIVE_INFINITY;
        for (int i = 0; i < V; i++) {
            if (!visitados[i] && distancia[i] < minDist) {
                minDist = distancia[i];
                siguiente = i;
            }
        }

        if (siguiente != -1) {
            dijkstraRecursivo(siguiente, distancia, visitados);
        }
    }

    public void dijkstraRec(String origen) {
        double[] distancia = new double[V];
        boolean[] visitados = new boolean[V];
        for (int i = 0; i < V; i++) distancia[i] = Double.POSITIVE_INFINITY;
        distancia[nombreIdx.get(origen)] = 0;

        dijkstraRecursivo(nombreIdx.get(origen), distancia, visitados);

        for (int i = 0; i < V; i++) {
            System.out.println(IdxNombre.get(i) + " -> " + distancia[i]);
        }
    }


    public double[][] FloydWarshall()
    {
        double[][] distancias = new double[V][V];
        for (int i = 0; i < V; i++) {
            for (int j = 0; j < V; j++) {
                distancias[i][j] = Double.POSITIVE_INFINITY;
            }
            distancias[i][i] = 0;
        }

        for (int u = 0; u < V; u++) {
            for (Arista arista : listaAdyacencia.get(u)) {
                int v = arista.destino;
                double peso = arista.peso;
                distancias[u][v] = peso;
            }
        }

        for (int k = 0; k < V; k++) {
            for (int i = 0; i < V; i++) {
                for (int j = 0; j < V; j++) {
                    if (distancias[i][k] + distancias[k][j] < distancias[i][j]) {
                        distancias[i][j] = distancias[i][k] + distancias[k][j];
                    }
                }
            }
        }

        return distancias;
    }

    public void imprimirMatrizFloydWarshall(double[][] distancias) {
        // Imprimir encabezado
        System.out.print(String.format("%15s", ""));
        for (int j = 0; j < V; j++) {
            System.out.print(String.format("%15s", IdxNombre.get(j)));
        }
        System.out.println();

        // Imprimir filas
        for (int i = 0; i < V; i++) {
            System.out.print(String.format("%15s", IdxNombre.get(i)));
            for (int j = 0; j < V; j++) {
                if (distancias[i][j] == Double.POSITIVE_INFINITY) {
                    System.out.print(String.format("%15s", "∞"));
                } else {
                    System.out.print(String.format("%15.2f", distancias[i][j]));
                }
            }
            System.out.println();
        }
    }


    public void Prim()
    {
        boolean[] visitados = new boolean[V];
        double[] key = new double[V];
        int[] padre = new int[V];

        for (int i = 0; i < V; i++) {
            key[i] = Double.POSITIVE_INFINITY;
            padre[i] = -1;
            visitados[i] = false;
        }

        key[0] = 0;
        PriorityQueue<ParNodo> pq = new PriorityQueue<>();
        pq.add(new ParNodo(0, 0));

        while (!pq.isEmpty()) {
            ParNodo actual = pq.poll();
            int u = actual.nodo;

            if (visitados[u]) continue;
            visitados[u] = true;

            // Recorrer todos los vecinos de u
            for (Arista arista : listaAdyacencia.get(u)) {
                int v = arista.destino;
                double peso = arista.peso;

                if (!visitados[v] && peso < key[v]) {
                    key[v] = peso;
                    padre[v] = u;
                    pq.add(new ParNodo(v, key[v]));
                }
            }
        }

        double costoTotal = 0;
        System.out.println("Árbol de Expansión Mínimo (Prim):");
        for (int i = 1; i < V; i++) { // empezamos desde 1 porque 0 es raíz
            System.out.println(IdxNombre.get(padre[i]) + " - " + IdxNombre.get(i) + " : " + key[i]);
            costoTotal += key[i];
        }
        System.out.println("Costo total del MST: " + costoTotal);


    }





    public void cargarCSV(String nombreCSV) throws IOException {
        String ruta = "./CSVs/" + nombreCSV;
        BufferedReader br = new BufferedReader(new FileReader(ruta));
        String linea;
        boolean firstLine = true;
        while ((linea = br.readLine()) !=null)
        {
            if(firstLine)
            {
                firstLine = false;
            } else {
                String[] leido = linea.split(",");
                String origen = leido[0].trim();
                String destino = leido[1].trim();
                double costo = Double.parseDouble(leido[2].trim());

                this.addEdge(origen,destino,costo);
            }
        }
    }



}