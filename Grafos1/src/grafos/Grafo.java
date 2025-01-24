package grafos;

import java.util.ArrayList;

public interface Grafo {
    void adicionarAresta(Vertice origem, Vertice destino) throws Exception;
    void adicionarAresta(Vertice origem, Vertice destino, double peso) throws Exception;
    boolean existeAresta(Vertice origem, Vertice destino);
    int grauDoVertice(Vertice vertice) throws Exception;
    int numeroDeVertices();
    int numeroDeArestas();
    ArrayList<Vertice> adjacentesDe(Vertice vertice) throws Exception;
    void setarPeso(Vertice origem, Vertice destino, double peso) throws Exception;
    ArrayList<Aresta> arestasEntre(Vertice origem, Vertice destino) throws Exception;
    ArrayList<Vertice> vertices();

    // Adicionando métodos para busca em profundidade, largura, AGM, Dijkstra e Edmonds-Karp
    void buscaEmProfundidade(int verticeInicial);
    void buscaEmLargura(int verticeInicial);
    void primMST();
    void dijkstra(int src, int dest);  // Novo método para Dijkstra
    void edmondsKarp(int source, int sink);  // Novo método para Edmonds-Karp
}
