package grafos;

import java.util.*;

public class MatrizIncidencia implements Grafo {
    private int[][] matriz;
    private int numVertices;
    private int numArestas;

    public MatrizIncidencia(int numVertices) {
        this.numVertices = numVertices;
        this.numArestas = 0;
        this.matriz = new int[numVertices][numVertices * (numVertices - 1) / 2];
    }

    @Override
    public void adicionarAresta(Vertice origem, Vertice destino) throws Exception {
        adicionarAresta(origem, destino, 1);
    }

    @Override
    public void adicionarAresta(Vertice origem, Vertice destino, double peso) throws Exception {
        if (peso != (int) peso) {
            throw new Exception("Peso nao pode ser fracionario para matriz de incidencia.");
        }
        matriz[origem.getId()][numArestas] = (int) peso;
        matriz[destino.getId()][numArestas] = (int) peso;
        numArestas++;
    }

    @Override
    public boolean existeAresta(Vertice origem, Vertice destino) {
        for (int i = 0; i < numArestas; i++) {
            if (matriz[origem.getId()][i] != 0 && matriz[destino.getId()][i] != 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int grauDoVertice(Vertice vertice) throws Exception {
        int grau = 0;
        for (int i = 0; i < numArestas; i++) {
            if (matriz[vertice.getId()][i] != 0) {
                grau++;
            }
        }
        return grau;
    }

    @Override
    public int numeroDeVertices() {
        return numVertices;
    }

    @Override
    public int numeroDeArestas() {
        return numArestas;
    }

    @Override
    public ArrayList<Vertice> adjacentesDe(Vertice vertice) throws Exception {
        ArrayList<Vertice> adjacentes = new ArrayList<>();
        for (int i = 0; i < numArestas; i++) {
            if (matriz[vertice.getId()][i] != 0) {
                for (int j = 0; j < numVertices; j++) {
                    if (j != vertice.getId() && matriz[j][i] != 0) {
                        adjacentes.add(new Vertice(j));
                    }
                }
            }
        }
        return adjacentes;
    }

    @Override
    public void setarPeso(Vertice origem, Vertice destino, double peso) throws Exception {
        if (peso != (int) peso) {
            throw new Exception("Peso nao pode ser fracionario para matriz de incidencia.");
        }
        for (int i = 0; i < numArestas; i++) {
            if (matriz[origem.getId()][i] != 0 && matriz[destino.getId()][i] != 0) {
                matriz[origem.getId()][i] = (int) peso;
                matriz[destino.getId()][i] = (int) peso;
                break;
            }
        }
    }

    @Override
    public ArrayList<Aresta> arestasEntre(Vertice origem, Vertice destino) throws Exception {
        ArrayList<Aresta> arestas = new ArrayList<>();
        for (int i = 0; i < numArestas; i++) {
            if (matriz[origem.getId()][i] != 0 && matriz[destino.getId()][i] != 0) {
                arestas.add(new Aresta(origem, destino, matriz[origem.getId()][i]));
            }
        }
        return arestas;
    }

    @Override
    public ArrayList<Vertice> vertices() {
        ArrayList<Vertice> vertices = new ArrayList<>();
        for (int i = 0; i < numVertices; i++) {
            vertices.add(new Vertice(i));
        }
        return vertices;
    }

    @Override
    public void buscaEmProfundidade(int verticeInicial) {
        boolean[] visitado = new boolean[numVertices];
        int[] d = new int[numVertices];
        int[] f = new int[numVertices];
        int[] tempo = new int[1];  // Usar array para passar por referência
        List<Aresta> arestasDeArvore = new ArrayList<>();
        List<Aresta> arestasDeRetorno = new ArrayList<>();
        List<Aresta> arestasDeAvanco = new ArrayList<>();
        List<Aresta> arestasDeCruzamento = new ArrayList<>();

        buscaEmProfundidadeUtil(verticeInicial, visitado, d, f, tempo, arestasDeArvore, arestasDeRetorno, arestasDeAvanco, arestasDeCruzamento);

        System.out.println("Tempos de descoberta (d) e finalizacao (f):");
        for (int i = 0; i < numVertices; i++) {
            System.out.println("Vértice " + i + ": d = " + d[i] + ", f = " + f[i]);
        }

        System.out.println("Arestas de arvore: " + arestasDeArvore);
        System.out.println("Arestas de retorno: " + arestasDeRetorno);
        System.out.println("Arestas de avanco: " + arestasDeAvanco);
        System.out.println("Arestas de cruzamento: " + arestasDeCruzamento);
    }

    private void buscaEmProfundidadeUtil(int v, boolean[] visitado, int[] d, int[] f, int[] tempo, List<Aresta> arestasDeArvore, List<Aresta> arestasDeRetorno, List<Aresta> arestasDeAvanco, List<Aresta> arestasDeCruzamento) {
        visitado[v] = true;
        d[v] = ++tempo[0];

        for (int i = 0; i < numArestas; i++) {
            if (matriz[v][i] != 0) {
                for (int j = 0; j < numVertices; j++) {
                    if (matriz[j][i] != 0) {
                        if (!visitado[j]) {
                            arestasDeArvore.add(new Aresta(new Vertice(v), new Vertice(j), matriz[v][i]));
                            buscaEmProfundidadeUtil(j, visitado, d, f, tempo, arestasDeArvore, arestasDeRetorno, arestasDeAvanco, arestasDeCruzamento);
                        } else if (visitado[j] && d[v] < d[j]) {
                            arestasDeRetorno.add(new Aresta(new Vertice(v), new Vertice(j), matriz[v][i]));
                        } else if (visitado[j] && d[v] > d[j]) {
                            arestasDeAvanco.add(new Aresta(new Vertice(v), new Vertice(j), matriz[v][i]));
                        } else {
                            arestasDeCruzamento.add(new Aresta(new Vertice(v), new Vertice(j), matriz[v][i]));
                        }
                    }
                }
            }
        }

        f[v] = ++tempo[0];
    }

    @Override
    public void buscaEmLargura(int verticeInicial) {
        boolean[] visitado = new boolean[numVertices];
        int[] d = new int[numVertices];
        int[] pai = new int[numVertices];
        Queue<Integer> fila = new LinkedList<>();

        visitado[verticeInicial] = true;
        d[verticeInicial] = 0;
        fila.add(verticeInicial);

        while (!fila.isEmpty()) {
            int v = fila.poll();

            for (int i = 0; i < numArestas; i++) {
                if (matriz[v][i] != 0) {
                    for (int j = 0; j < numVertices; j++) {
                        if (matriz[j][i] != 0 && !visitado[j]) {
                            visitado[j] = true;
                            d[j] = d[v] + 1;
                            pai[j] = v;
                            fila.add(j);
                        }
                    }
                }
            }
        }

        System.out.println("Tempos de descoberta (d) e pai (π):");
        for (int i = 0; i < numVertices; i++) {
            System.out.println("Vertice " + i + ": d = " + d[i] + ", π = " + pai[i]);
        }
    }

    @Override
    public void primMST() {
        int[] key = new int[numVertices];
        int[] pai = new int[numVertices];
        boolean[] mstSet = new boolean[numVertices];

        for (int i = 0; i < numVertices; i++) {
            key[i] = Integer.MAX_VALUE;
            mstSet[i] = false;
        }

        key[0] = 0;
        pai[0] = -1;

        PriorityQueue<Vertice> pq = new PriorityQueue<>(Comparator.comparingInt(v -> key[v.getId()]));
        pq.add(new Vertice(0));

        while (!pq.isEmpty()) {
            Vertice u = pq.poll();
            mstSet[u.getId()] = true;

            for (int v = 0; v < numVertices; v++) {
                if (existeAresta(u, new Vertice(v)) && !mstSet[v] && key[v] > matriz[u.getId()][v]) {
                    pai[v] = u.getId();
                    key[v] = matriz[u.getId()][v];
                    pq.add(new Vertice(v));
                }
            }
        }

        System.out.println("Arestas da Arvore Geradora Minima (AGM):");
        for (int i = 1; i < numVertices; i++) {
            System.out.println(pai[i] + " - " + i);
        }
    }

    @Override
    public void dijkstra(int src, int dest) {
        int[] dist = new int[numVertices];
        boolean[] sptSet = new boolean[numVertices];
        int[] parent = new int[numVertices];

        for (int i = 0; i < numVertices; i++) {
            dist[i] = Integer.MAX_VALUE;
            sptSet[i] = false;
        }

        dist[src] = 0;
        parent[src] = -1;

        PriorityQueue<Vertice> pq = new PriorityQueue<>(Comparator.comparingInt(v -> dist[v.getId()]));
        pq.add(new Vertice(src));

        while (!pq.isEmpty()) {
            Vertice u = pq.poll();
            sptSet[u.getId()] = true;

            for (int v = 0; v < numVertices; v++) {
                if (!sptSet[v] && matriz[u.getId()][v] != 0 && dist[u.getId()] != Integer.MAX_VALUE && dist[u.getId()] + matriz[u.getId()][v] < dist[v]) {
                    dist[v] = dist[u.getId()] + matriz[u.getId()][v];
                    parent[v] = u.getId();
                    pq.add(new Vertice(v));
                }
            }
        }

        printPath(dest, parent);
    }

    private void printPath(int dest, int[] parent) {
        if (parent[dest] == -1) {
            System.out.print(dest + " ");
            return;
        }
        printPath(parent[dest], parent);
        System.out.print(dest + " ");
    }

    @Override
    public void edmondsKarp(int source, int sink) {
        int[][] residualGraph = new int[numVertices][numVertices];
        for (int u = 0; u < numVertices; u++) {
            for (int v = 0; v < numVertices; v++) {
                residualGraph[u][v] = matriz[u][v];
            }
        }

        int[] parent = new int[numVertices];
        int maxFlow = 0;

        while (bfsEdmondsKarp(residualGraph, source, sink, parent)) {
            int pathFlow = Integer.MAX_VALUE;
            for (int v = sink; v != source; v = parent[v]) {
                int u = parent[v];
                pathFlow = Math.min(pathFlow, residualGraph[u][v]);
            }

            for (int v = sink; v != source; v = parent[v]) {
                int u = parent[v];
                residualGraph[u][v] -= pathFlow;
                residualGraph[v][u] += pathFlow;
            }

            maxFlow += pathFlow;
        }

        System.out.println("O fluxo máximo e " + maxFlow);
    }

    private boolean bfsEdmondsKarp(int[][] residualGraph, int s, int t, int[] parent) {
        boolean[] visited = new boolean[numVertices];
        Queue<Integer> queue = new LinkedList<>();
        queue.add(s);
        visited[s] = true;
        parent[s] = -1;

        while (!queue.isEmpty()) {
            int u = queue.poll();

            for (int v = 0; v < numVertices; v++) {
                if (!visited[v] && residualGraph[u][v] > 0) {
                    if (v == t) {
                        parent[v] = u;
                        return true;
                    }
                    queue.add(v);
                    parent[v] = u;
                    visited[v] = true;
                }
            }
        }
        return false;
    }
}
