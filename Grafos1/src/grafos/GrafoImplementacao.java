package grafos;

import java.util.*;

public class GrafoImplementacao implements Grafo {
    private Map<Vertice, List<Aresta>> adjacencias;

    public GrafoImplementacao() {
        this.adjacencias = new HashMap<>();
    }

    @Override
    public void adicionarAresta(Vertice origem, Vertice destino) throws Exception {
        adicionarAresta(origem, destino, 1);
    }

    @Override
    public void adicionarAresta(Vertice origem, Vertice destino, double peso) throws Exception {
        adjacencias.putIfAbsent(origem, new ArrayList<>());
        adjacencias.get(origem).add(new Aresta(origem, destino, peso));
    }

    @Override
    public boolean existeAresta(Vertice origem, Vertice destino) {
        return adjacencias.containsKey(origem) && adjacencias.get(origem).stream().anyMatch(a -> a.getDestino().equals(destino));
    }

    @Override
    public int grauDoVertice(Vertice vertice) throws Exception {
        if (!adjacencias.containsKey(vertice)) {
            throw new Exception("Vertice nao existe.");
        }
        return adjacencias.get(vertice).size();
    }

    @Override
    public int numeroDeVertices() {
        return adjacencias.size();
    }

    @Override
    public int numeroDeArestas() {
        return adjacencias.values().stream().mapToInt(Collection::size).sum();
    }

    @Override
    public ArrayList<Vertice> adjacentesDe(Vertice vertice) throws Exception {
        if (!adjacencias.containsKey(vertice)) {
            throw new Exception("Vertice nao encontrado.");
        }
        ArrayList<Vertice> adjacentes = new ArrayList<>();
        for (Aresta aresta : adjacencias.get(vertice)) {
            adjacentes.add(aresta.getDestino());
        }
        return adjacentes;
    }

    @Override
    public void setarPeso(Vertice origem, Vertice destino, double peso) throws Exception {
        if (!existeAresta(origem, destino)) {
            throw new Exception("Aresta nao encontrada.");
        }
        for (Aresta aresta : adjacencias.get(origem)) {
            if (aresta.getDestino().equals(destino)) {
                aresta.setPeso(peso);
                break;
            }
        }
    }

    @Override
    public ArrayList<Aresta> arestasEntre(Vertice origem, Vertice destino) throws Exception {
        if (!existeAresta(origem, destino)) {
            throw new Exception("Aresta nao encontrada.");
        }
        ArrayList<Aresta> arestas = new ArrayList<>();
        for (Aresta aresta : adjacencias.get(origem)) {
            if (aresta.getDestino().equals(destino)) {
                arestas.add(aresta);
            }
        }
        return arestas;
    }

    @Override
    public ArrayList<Vertice> vertices() {
        return new ArrayList<>(adjacencias.keySet());
    }

    @Override
    public void buscaEmProfundidade(int verticeInicial) {
        Set<Vertice> visitados = new HashSet<>();
        Stack<Vertice> pilha = new Stack<>();
        Vertice inicial = new Vertice(verticeInicial);
        pilha.push(inicial);

        while (!pilha.isEmpty()) {
            Vertice atual = pilha.pop();
            if (!visitados.contains(atual)) {
                visitados.add(atual);
                System.out.println("Visitado: " + atual.getId());

                try {
                    for (Vertice vizinho : adjacentesDe(atual)) {
                        if (!visitados.contains(vizinho)) {
                            pilha.push(vizinho);
                        }
                    }
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
            }
        }
    }

    @Override
    public void buscaEmLargura(int verticeInicial) {
        Set<Vertice> visitados = new HashSet<>();
        Queue<Vertice> fila = new LinkedList<>();
        Vertice inicial = new Vertice(verticeInicial);
        fila.add(inicial);

        while (!fila.isEmpty()) {
            Vertice atual = fila.poll();
            if (!visitados.contains(atual)) {
                visitados.add(atual);
                System.out.println("Visitado: " + atual.getId());

                try {
                    for (Vertice vizinho : adjacentesDe(atual)) {
                        if (!visitados.contains(vizinho)) {
                            fila.add(vizinho);
                        }
                    }
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
            }
        }
    }

    @Override
    public void primMST() {
        PriorityQueue<Aresta> pq = new PriorityQueue<>(Comparator.comparingDouble(Aresta::getPeso));
        Set<Vertice> mstSet = new HashSet<>();
        Map<Vertice, Aresta> pai = new HashMap<>();

        Vertice inicial = adjacencias.keySet().iterator().next();
        pq.addAll(adjacencias.get(inicial));
        mstSet.add(inicial);

        while (!pq.isEmpty() && mstSet.size() < adjacencias.size()) {
            Aresta aresta = pq.poll();
            Vertice destino = aresta.getDestino();

            if (!mstSet.contains(destino)) {
                mstSet.add(destino);
                pai.put(destino, aresta);
                pq.addAll(adjacencias.get(destino));
            }
        }

        System.out.println("Arestas da Arvore Geradora Minima (AGM):");
        for (Map.Entry<Vertice, Aresta> entry : pai.entrySet()) {
            System.out.println(entry.getValue().getOrigem().getId() + " - " + entry.getKey().getId());
        }
    }

    @Override
    public void dijkstra(int src, int dest) {
        Map<Vertice, Double> distancias = new HashMap<>();
        PriorityQueue<Vertice> filaPrioridade = new PriorityQueue<>(Comparator.comparing(distancias::get));
        Map<Vertice, Vertice> predecessores = new HashMap<>();

        Vertice origem = new Vertice(src);
        distancias.put(origem, 0.0);
        filaPrioridade.add(origem);

        while (!filaPrioridade.isEmpty()) {
            Vertice atual = filaPrioridade.poll();

            for (Aresta aresta : adjacencias.getOrDefault(atual, new ArrayList<>())) {
                Vertice vizinho = aresta.getDestino();
                double novaDistancia = distancias.get(atual) + aresta.getPeso();

                if (novaDistancia < distancias.getOrDefault(vizinho, Double.MAX_VALUE)) {
                    distancias.put(vizinho, novaDistancia);
                    predecessores.put(vizinho, atual);
                    filaPrioridade.add(vizinho);
                }
            }
        }
        System.out.println("Distancia minima de " + src + " para " + dest + ": " + distancias.getOrDefault(new Vertice(dest), Double.MAX_VALUE));
    }

    @Override
    public void edmondsKarp(int source, int sink) {
        int fluxoMaximo = 0;
        Map<Aresta, Double> fluxo = new HashMap<>();
        Queue<Vertice> fila = new LinkedList<>();
        Map<Vertice, Vertice> predecessores = new HashMap<>();

        while (true) {
            fila.add(new Vertice(source));
            predecessores.clear();

            while (!fila.isEmpty()) {
                Vertice atual = fila.poll();
                if (atual.equals(new Vertice(sink))) break;

                for (Aresta aresta : adjacencias.getOrDefault(atual, new ArrayList<>())) {
                    if (!predecessores.containsKey(aresta.getDestino()) && aresta.getPeso() > fluxo.getOrDefault(aresta, 0.0)) {
                        predecessores.put(aresta.getDestino(), atual);
                        fila.add(aresta.getDestino());
                    }
                }
            }
            if (!predecessores.containsKey(new Vertice(sink))) break;
        }
        System.out.println("Fluxo maximo: " + fluxoMaximo);
    }
}
