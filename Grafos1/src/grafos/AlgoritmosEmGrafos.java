package grafos;

import java.util.ArrayList;
import java.util.Collection;

public interface AlgoritmosEmGrafos {

    public Grafo carregarGrafo(String path, TipoDeRepresentacao t) throws Exception;
    public Collection<Aresta> buscaEmLargura(Grafo g);
    public Collection<Aresta> buscaEmProfundidade(Grafo g);
    public ArrayList<Aresta> menorCaminho(Grafo g, Vertice origem, Vertice destino) throws Exception;
    public boolean existeCiclo(Grafo g);
    public Collection<Aresta> agmUsandoKruskall(Grafo g);
    public double custoDaArvoreGeradora(Grafo g, Collection<Aresta> arestas) throws Exception;
    public boolean ehArvoreGeradora(Grafo g, Collection<Aresta> arestas);
    public ArrayList<Aresta> caminhoMaisCurto(Grafo g, Vertice origem, Vertice destino);
    public double custoDoCaminho(Grafo g, ArrayList<Aresta> arestas, Vertice origem, Vertice destino) throws Exception;
    public boolean ehCaminho(ArrayList<Aresta> arestas, Vertice origem, Vertice destino);
    public Collection<Aresta> arestasDeArvore(Grafo g);
    public Collection<Aresta> arestasDeRetorno(Grafo g);
    public Collection<Aresta> arestasDeAvanco(Grafo g);
    public Collection<Aresta> arestasDeCruzamento(Grafo g);
}
