package grafos;

import java.io.*;
import java.util.*;

public class AlgoritmosEmGrafosImpl implements AlgoritmosEmGrafos {

    @Override
    public Grafo carregarGrafo(String path, TipoDeRepresentacao t) throws IOException {
        FileManager fm = new FileManager();
        ArrayList<String> linhas = fm.stringReader(path);

        if (linhas == null) {
            throw new IOException("Erro ao ler o arquivo.");
        }

        Grafo grafo;
        switch (t) {
            case MATRIZ_DE_ADJACENCIA:
                grafo = new MatrizAdjacencia(5);
                break;
            case MATRIZ_DE_INCIDENCIA:
                grafo = new MatrizIncidencia(5);
                break;
            case LISTA_DE_ADJACENCIA:
                grafo = new GrafoImplementacao();
                break;
            default:
                throw new IllegalArgumentException("Tipo de representação invalido.");
        }

        for (String linha : linhas) {
            if (linha.split(" ").length < 2) {
                continue;
            }

            String[] partes = linha.split(" ");
            int u = Integer.parseInt(partes[0]);
            String[] arestas = partes[1].split(";");

            for (String aresta : arestas) {
                if (!aresta.isEmpty()) {
                    String[] dados = aresta.split("-");
                    if (dados.length < 2) {
                        continue;
                    }
                    int v = Integer.parseInt(dados[0]);
                    int peso = Integer.parseInt(dados[1]);
                    try {
                        grafo.adicionarAresta(new Vertice(u), new Vertice(v), peso);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        return grafo;
    }

    @Override
    public Collection<Aresta> buscaEmLargura(Grafo g) {
        Set<Vertice> visitados = new HashSet<>();
        Queue<Vertice> fila = new LinkedList<>();
        ArrayList<Aresta> arestasVisitadas = new ArrayList<>();

        for (Vertice vertice : g.vertices()) {
            if (!visitados.contains(vertice)) {
                fila.add(vertice);
                visitados.add(vertice);

                while (!fila.isEmpty()) {
                    Vertice atual = fila.poll();
                    try {
                        for (Vertice vizinho : g.adjacentesDe(atual)) {
                            if (!visitados.contains(vizinho)) {
                                fila.add(vizinho);
                                visitados.add(vizinho);
                                arestasVisitadas.add(new Aresta(atual, vizinho));
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        return arestasVisitadas;
    }

    @Override
    public Collection<Aresta> buscaEmProfundidade(Grafo g) {
        Set<Vertice> visitados = new HashSet<>();
        Stack<Vertice> pilha = new Stack<>();
        ArrayList<Aresta> arestasVisitadas = new ArrayList<>();

        for (Vertice vertice : g.vertices()) {
            if (!visitados.contains(vertice)) {
                pilha.push(vertice);
                visitados.add(vertice);

                while (!pilha.isEmpty()) {
                    Vertice atual = pilha.pop();
                    try {
                        for (Vertice vizinho : g.adjacentesDe(atual)) {
                            if (!visitados.contains(vizinho)) {
                                pilha.push(vizinho);
                                visitados.add(vizinho);
                                arestasVisitadas.add(new Aresta(atual, vizinho));
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        return arestasVisitadas;
    }

    @Override
    public ArrayList<Aresta> menorCaminho(Grafo g, Vertice origem, Vertice destino) throws Exception {
        Map<Vertice, Double> distancias = new HashMap<>();
        PriorityQueue<Vertice> filaPrioridade = new PriorityQueue<>(Comparator.comparing(distancias::get));
        Map<Vertice, Vertice> predecessores = new HashMap<>();

        distancias.put(origem, 0.0);
        filaPrioridade.add(origem);

        while (!filaPrioridade.isEmpty()) {
            Vertice atual = filaPrioridade.poll();

            for (Aresta aresta : g.arestasEntre(atual, atual)) {
                Vertice vizinho = aresta.getDestino();
                double novaDistancia = distancias.get(atual) + aresta.getPeso();

                if (novaDistancia < distancias.getOrDefault(vizinho, Double.MAX_VALUE)) {
                    distancias.put(vizinho, novaDistancia);
                    predecessores.put(vizinho, atual);
                    filaPrioridade.add(vizinho);
                }
            }
        }

        ArrayList<Aresta> caminho = new ArrayList<>();
        Vertice atual = destino;
        while (atual != null) {
            Vertice predecessor = predecessores.get(atual);
            if (predecessor != null) {
                caminho.add(new Aresta(predecessor, atual));
                atual = predecessor;
            } else {
                break;
            }
        }

        return caminho;
    }

    @Override
    public boolean existeCiclo(Grafo g) {
        Set<Vertice> visitados = new HashSet<>();
        Set<Vertice> pilhaRecursao = new HashSet<>();

        for (Vertice vertice : g.vertices()) {
            try {
                if (existeCicloUtil(vertice, visitados, pilhaRecursao, g)) {
                    return true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    private boolean existeCicloUtil(Vertice vertice, Set<Vertice> visitados, Set<Vertice> pilhaRecursao, Grafo g) throws Exception {
        if (pilhaRecursao.contains(vertice)) {
            return true;
        }

        if (visitados.contains(vertice)) {
            return false;
        }

        visitados.add(vertice);
        pilhaRecursao.add(vertice);

        for (Vertice vizinho : g.adjacentesDe(vertice)) {
            if (existeCicloUtil(vizinho, visitados, pilhaRecursao, g)) {
                return true;
            }
        }

        pilhaRecursao.remove(vertice);
        return false;
    }

    @Override
    public Collection<Aresta> agmUsandoKruskall(Grafo g) {
        PriorityQueue<Aresta> arestas = new PriorityQueue<>(Comparator.comparingDouble(Aresta::getPeso));
        Set<Aresta> agm = new HashSet<>();
        Map<Vertice, Vertice> pai = new HashMap<>();
        Map<Vertice, Integer> rank = new HashMap<>();

        for (Vertice vertice : g.vertices()) {
            pai.put(vertice, vertice);
            rank.put(vertice, 0);
        }

        for (Vertice vertice : g.vertices()) {
            try {
                for (Aresta aresta : g.arestasEntre(vertice, vertice)) {
                    arestas.add(aresta);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        while (!arestas.isEmpty() && agm.size() < g.numeroDeVertices() - 1) {
            Aresta aresta = arestas.poll();
            Vertice origem = aresta.getOrigem();
            Vertice destino = aresta.getDestino();

            if (find(origem, pai) != find(destino, pai)) {
                union(origem, destino, pai, rank);
                agm.add(aresta);
            }
        }

        return agm;
    }

    private Vertice find(Vertice vertice, Map<Vertice, Vertice> pai) {
        if (pai.get(vertice) != vertice) {
            pai.put(vertice, find(pai.get(vertice), pai));
        }
        return pai.get(vertice);
    }

    private void union(Vertice x, Vertice y, Map<Vertice, Vertice> pai, Map<Vertice, Integer> rank) {
        Vertice rootX = find(x, pai);
        Vertice rootY = find(y, pai);

        if (rank.get(rootX) < rank.get(rootY)) {
            pai.put(rootX, rootY);
        } else if (rank.get(rootX) > rank.get(rootY)) {
            pai.put(rootY, rootX);
        } else {
            pai.put(rootY, rootX);
            rank.put(rootX, rank.get(rootX) + 1);
        }
    }

    @Override
    public double custoDaArvoreGeradora(Grafo g, Collection<Aresta> arestas) throws Exception {
        double custoTotal = 0;
        for (Aresta aresta : arestas) {
            custoTotal += aresta.getPeso();
        }
        return custoTotal;
    }

    @Override
    public boolean ehArvoreGeradora(Grafo g, Collection<Aresta> arestas) {
        Set<Vertice> visitados = new HashSet<>();
        int numVertices = 0;

        for (Aresta aresta : arestas) {
            if (!visitados.contains(aresta.getOrigem())) {
                visitados.add(aresta.getOrigem());
                numVertices++;
            }
            if (!visitados.contains(aresta.getDestino())) {
                visitados.add(aresta.getDestino());
                numVertices++;
            }
        }

        return numVertices == g.numeroDeVertices() && arestas.size() == g.numeroDeVertices() - 1;
    }

    @Override
    public ArrayList<Aresta> caminhoMaisCurto(Grafo g, Vertice origem, Vertice destino) {
        try {
            return menorCaminho(g, origem, destino);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public double custoDoCaminho(Grafo g, ArrayList<Aresta> arestas, Vertice origem, Vertice destino) throws Exception {
        double custoTotal = 0;
        for (Aresta aresta : arestas) {
            custoTotal += aresta.getPeso();
        }
        return custoTotal;
    }

    @Override
    public boolean ehCaminho(ArrayList<Aresta> arestas, Vertice origem, Vertice destino) {
        Set<Vertice> visitados = new HashSet<>();
        visitados.add(origem);

        for (Aresta aresta : arestas) {
            if (visitados.contains(aresta.getOrigem())) {
                visitados.add(aresta.getDestino());
            } else {
                return false;
            }
        }

        return visitados.contains(destino);
    }

    @Override
    public Collection<Aresta> arestasDeArvore(Grafo g) {
        return agmUsandoKruskall(g);
    }

    @Override
    public Collection<Aresta> arestasDeRetorno(Grafo g) {
        Set<Aresta> arestasDeRetorno = new HashSet<>();
        Set<Vertice> visitados = new HashSet<>();
        Stack<Vertice> pilha = new Stack<>();

        for (Vertice vertice : g.vertices()) {
            if (!visitados.contains(vertice)) {
                pilha.push(vertice);
                visitados.add(vertice);

                while (!pilha.isEmpty()) {
                    Vertice atual = pilha.pop();
                    try {
                        for (Vertice vizinho : g.adjacentesDe(atual)) {
                            if (visitados.contains(vizinho)) {
                                arestasDeRetorno.add(new Aresta(atual, vizinho));
                            } else {
                                pilha.push(vizinho);
                                visitados.add(vizinho);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        return arestasDeRetorno;
    }

    @Override
    public Collection<Aresta> arestasDeAvanco(Grafo g) {
        Set<Aresta> arestasDeAvanco = new HashSet<>();
        Set<Vertice> visitados = new HashSet<>();
        Stack<Vertice> pilha = new Stack<>();

        for (Vertice vertice : g.vertices()) {
            if (!visitados.contains(vertice)) {
                pilha.push(vertice);
                visitados.add(vertice);

                while (!pilha.isEmpty()) {
                    Vertice atual = pilha.pop();
                    try {
                        for (Vertice vizinho : g.adjacentesDe(atual)) {
                            if (!visitados.contains(vizinho)) {
                                arestasDeAvanco.add(new Aresta(atual, vizinho));
                                pilha.push(vizinho);
                                visitados.add(vizinho);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        return arestasDeAvanco;
    }

    @Override
    public Collection<Aresta> arestasDeCruzamento(Grafo g) {
        Set<Aresta> arestasDeCruzamento = new HashSet<>();
        Set<Vertice> visitados = new HashSet<>();
        Stack<Vertice> pilha = new Stack<>();

        for (Vertice vertice : g.vertices()) {
            if (!visitados.contains(vertice)) {
                pilha.push(vertice);
                visitados.add(vertice);

                while (!pilha.isEmpty()) {
                    Vertice atual = pilha.pop();
                    try {
                        for (Vertice vizinho : g.adjacentesDe(atual)) {
                            if (!visitados.contains(vizinho) && !pilha.contains(vizinho)) {
                                arestasDeCruzamento.add(new Aresta(atual, vizinho));
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        return arestasDeCruzamento;
    }
}
