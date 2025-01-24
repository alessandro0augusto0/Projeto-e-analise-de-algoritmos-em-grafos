package grafos;

import javax.swing.*;
import java.util.*;

public class Algoritmos implements AlgoritmosEmGrafos {
    private JTextArea outputArea;
    private boolean useTerminalOutput;

    public Algoritmos(JTextArea outputArea) {
        this.outputArea = outputArea;
        this.useTerminalOutput = (outputArea == null);
    }

    private void printOutput(String message) {
        if (useTerminalOutput) {
            System.out.println(message);
        } else {
            outputArea.append(message + "\n");
        }
    }

    @Override
    public Grafo carregarGrafo(String path, TipoDeRepresentacao t) throws Exception {
        FileManager fileManager = new FileManager();
        ArrayList<String> arqinfo = new ArrayList<>(fileManager.stringReader(path));
        return carregarGrafoFromStrings(arqinfo, t);
    }

    public Grafo carregarGrafoFromStrings(ArrayList<String> arqinfo, TipoDeRepresentacao t) throws Exception {
        String nums = arqinfo.get(0);
        int quantDeVertices = Integer.parseInt(nums);

        if (t == TipoDeRepresentacao.LISTA_DE_ADJACENCIA) {
            ListaAdjacencia listaAdjacencia = new ListaAdjacencia();
            listaAdjacencia.Inicializar(quantDeVertices);
            for (int i = 1; i <= quantDeVertices; i++) {
                String input = arqinfo.get(i);

                input = input.trim();
                if (input.endsWith(";")) {
                    input = input.substring(0, input.length() - 1);
                }

                String[] pairs = input.split("; ");

                List<Integer> valuesList = new ArrayList<>();
                Integer subKey = null;

                for (String pair : pairs) {
                    if (pair.contains(" ")) {
                        String[] parts = pair.split(" ");
                        subKey = Integer.parseInt(parts[0]);
                        String[] subKeyValue = parts[1].split("-");
                        int mainKey = Integer.parseInt(subKeyValue[0]);
                        int value = Integer.parseInt(subKeyValue[1]);

                        if (valuesList.isEmpty()) {
                            valuesList.add(subKey);
                        }

                        valuesList.add(mainKey);
                        valuesList.add(value);
                    } else {
                        String[] keyValue = pair.split("-");
                        int mainKey = Integer.parseInt(keyValue[0]);
                        int value = Integer.parseInt(keyValue[1]);

                        if (valuesList.isEmpty()) {
                            valuesList.add(null);
                        }

                        valuesList.add(mainKey);
                        valuesList.add(value);
                    }
                }

                int[] valores = valuesList.stream().mapToInt(k -> (k == null ? -1 : k)).toArray();

                int j = 1;
                Vertice origem = new Vertice(valores[0]);
                int peso = 0;

                do {
                    Vertice destino = new Vertice(valores[j]);
                    j++;
                    peso = valores[j];
                    j++;
                    listaAdjacencia.adicionarAresta(origem, destino, peso);

                } while (j < valores.length);
            }
            return listaAdjacencia;
        } else if (t == TipoDeRepresentacao.MATRIZ_DE_ADJACENCIA) {
            MatrizAdjacencia matrizAdjacencia = new MatrizAdjacencia();
            matrizAdjacencia.Inicializar(quantDeVertices);
            for (int i = 1; i <= quantDeVertices; i++) {
                String input = arqinfo.get(i);

                input = input.trim();
                if (input.endsWith(";")) {
                    input = input.substring(0, input.length() - 1);
                }

                String[] pairs = input.split("; ");

                List<Integer> valuesList = new ArrayList<>();
                Integer subKey = null;

                for (String pair : pairs) {
                    if (pair.contains(" ")) {

                        String[] parts = pair.split(" ");
                        subKey = Integer.parseInt(parts[0]);
                        String[] subKeyValue = parts[1].split("-");
                        int mainKey = Integer.parseInt(subKeyValue[0]);
                        int value = Integer.parseInt(subKeyValue[1]);

                        if (valuesList.isEmpty()) {
                            valuesList.add(subKey);
                        }

                        valuesList.add(mainKey);
                        valuesList.add(value);
                    } else {

                        String[] keyValue = pair.split("-");
                        int mainKey = Integer.parseInt(keyValue[0]);
                        int value = Integer.parseInt(keyValue[1]);

                        if (valuesList.isEmpty()) {
                            valuesList.add(null);
                        }

                        valuesList.add(mainKey);
                        valuesList.add(value);
                    }
                }

                int[] valores = valuesList.stream().mapToInt(k -> (k == null ? -1 : k)).toArray();

                int j = 1;
                Vertice origem = new Vertice(valores[0]);
                int peso = 0;

                do {
                    Vertice destino = new Vertice(valores[j]);
                    j++;
                    peso = valores[j];
                    j++;
                    matrizAdjacencia.adicionarAresta(origem, destino, peso);

                } while (j < valores.length);
            }

            return matrizAdjacencia;
        } else {
            MatrizIncidencia matrizIncidencia = new MatrizIncidencia();
            matrizIncidencia.inicializar(quantDeVertices);
            for (int i = 1; i <= quantDeVertices; i++) {
                String input = arqinfo.get(i);

                input = input.trim();
                if (input.endsWith(";")) {
                    input = input.substring(0, input.length() - 1);
                }

                String[] pairs = input.split("; ");

                List<Integer> valuesList = new ArrayList<>();
                Integer subKey = null;

                for (String pair : pairs) {
                    if (pair.contains(" ")) {
                        String[] parts = pair.split(" ");
                        subKey = Integer.parseInt(parts[0]);
                        String[] subKeyValue = parts[1].split("-");
                        int mainKey = Integer.parseInt(subKeyValue[0]);
                        int value = Integer.parseInt(subKeyValue[1]);

                        if (valuesList.isEmpty()) {
                            valuesList.add(subKey);
                        }

                        valuesList.add(mainKey);
                        valuesList.add(value);
                    } else {

                        String[] keyValue = pair.split("-");
                        int mainKey = Integer.parseInt(keyValue[0]);
                        int value = Integer.parseInt(keyValue[1]);

                        if (valuesList.isEmpty()) {
                            valuesList.add(null);
                        }

                        valuesList.add(mainKey);
                        valuesList.add(value);
                    }
                }

                int[] valores = valuesList.stream().mapToInt(k -> (k == null ? -1 : k)).toArray();

                int j = 1;
                Vertice origem = new Vertice(valores[0]);
                int peso = 0;

                do {
                    Vertice destino = new Vertice(valores[j]);
                    j++;
                    peso = valores[j];
                    j++;
                    matrizIncidencia.adicionarAresta(origem, destino, peso);

                } while (j < valores.length);
            }
            return matrizIncidencia;
        }
    }

    @Override
    public Collection<Aresta> buscaEmLargura(Grafo g) throws Exception {
        int branco = 0, cinza = 1, preto = 2, inicial, tempo = 0, i;
        double[] cor = new double[g.numeroDeVertices()], d = new double[g.numeroDeVertices()];
        int[] t = new int[g.numeroDeVertices()];
        Vertice[] pai = new Vertice[g.numeroDeVertices()];
        ArrayList<Vertice> q = new ArrayList<>();
        ArrayList<Aresta> arestas = new ArrayList<>();

        inicial = Integer.parseInt(JOptionPane.showInputDialog("Escolha o Vertice Inicial:"));
        for (i = 0; i < g.numeroDeVertices(); i++) {
            cor[i] = branco;
            d[i] = Double.MAX_VALUE;
            pai[i] = null;
            t[i] = -1;
        }
        cor[inicial] = cinza;
        d[inicial] = 0;
        pai[inicial] = null;
        t[inicial] = tempo;
        q.add(g.vertices().get(inicial));

        while (!q.isEmpty()) {
            int tamanhoNivel = q.size();
            tempo++;
            for (int j = 0; j < tamanhoNivel; j++) {
                Vertice u = q.removeFirst();
                for (Vertice v : g.adjacentesDe(u)) {
                    if (cor[v.id()] == branco) {
                        arestas.add(g.arestasEntre(u, v).getFirst());
                        cor[v.id()] = cinza;
                        d[v.id()] = d[u.id()] + 1;
                        pai[v.id()] = u;
                        t[v.id()] = tempo;
                        q.add(v);
                    }
                }
                cor[u.id()] = preto;
            }
        }

        printOutput("==-==-Vertices-==-==");
        for (i = 0; i < g.numeroDeVertices(); i++) {
            printOutput("[" + i + "] ");
        }
        printOutput("");
        for (i = 0; i < g.numeroDeVertices(); i++) {
            if (pai[i] == null) {
                printOutput("[X] ");
            } else {
                printOutput("[" + pai[i].id() + "] ");
            }
        }
        printOutput("\n=-==-==-Tempo-==-==-=");
        for (i = 0; i < g.numeroDeVertices(); i++) {
            printOutput("[" + t[i] + "] ");
        }
        printOutput("\n==-==-==-==-==-==-==");
        return arestas;
    }

    @Override
    public Collection<Aresta> buscaEmProfundidade(Grafo g) throws Exception {
        ArvoreDeBusca[] arvoreBusca = new ArvoreDeBusca[g.numeroDeVertices()];
        ArrayList<Aresta> arestas = new ArrayList<>();
        int branco = 0, cinza = 1, preto = 2, inicial = 0;

        inicial = Integer.parseInt(JOptionPane.showInputDialog("Escolha o Vertice Inicial:"));

        for (int i = 0; i < g.numeroDeVertices(); i++) {
            arvoreBusca[i] = new ArvoreDeBusca(0, 0, 0, new ArrayList<Aresta>(), new ArrayList<Aresta>(), new ArrayList<Aresta>());
        }
        AndarNaArvore(arvoreBusca, g, g.vertices().get(inicial), 0, 0, arestas);
        for (int i = 0; i < g.numeroDeVertices(); i++) {
            if (arvoreBusca[i].getCor() == branco) {
                AndarNaArvore(arvoreBusca, g, g.vertices().get(i), 0, 0, arestas);
            }
        }
        for (int i = 0; i < arvoreBusca.length; i++) {
            printOutput("Vertice " + i + " --> Tempo de Descoberta: " + arvoreBusca[i].getTempo() + " Tempo de Finalizacao: " + arvoreBusca[i].getF());
            if (!arvoreBusca[i].getTree().isEmpty()) {
                printOutput("Arestas de Arvore para Vertice:");
                for (Aresta a : arvoreBusca[i].getTree()) {
                    printOutput("--> " + a.destino().id());
                }
                printOutput("");
            }
            if (!arvoreBusca[i].getBack().isEmpty()) {
                printOutput("Arestas de Retorno para Vertice:");
                for (Aresta a : arvoreBusca[i].getBack()) {
                    printOutput("--> " + a.destino().id());
                }
                printOutput("");
            }
            if (!arvoreBusca[i].getForward().isEmpty()) {
                printOutput("Arestas de Avanco para Vertice:");
                for (Aresta a : arvoreBusca[i].getForward()) {
                    printOutput("--> " + a.destino().id());
                }
                printOutput("");
            }
            if (!arvoreBusca[i].getCross().isEmpty()) {
                printOutput("Arestas de Cruzamento para Vertice:");
                for (Aresta a : arvoreBusca[i].getCross()) {
                    printOutput("--> " + a.destino().id());
                }
                printOutput("");
            }
        }
        return arestas;
    }

    @Override
    public ArrayList<Aresta> menorCaminho(Grafo g, Vertice origem, Vertice destino) throws Exception {
        int branco = 0, cinza = 1, preto = 2, inicial = origem.id(), tempo = 0, i;
        double[] cor = new double[g.numeroDeVertices()], d = new double[g.numeroDeVertices()];
        int[] t = new int[g.numeroDeVertices()];
        Vertice[] pai = new Vertice[g.numeroDeVertices()];
        ArrayList<Vertice> q = new ArrayList<>();
        ArrayList<Vertice> adj = new ArrayList<>();
        ArrayList<Aresta> arestas = new ArrayList<>();
        ArrayList<Aresta> aux = new ArrayList<>();

        for (i = 0; i < g.numeroDeVertices(); i++) {
            if (i == inicial) {
                cor[i] = cinza;
                d[i] = 0;
                pai[i] = null;
                t[i] = tempo;
            } else {
                cor[i] = branco;
                d[i] = Double.MAX_VALUE;
                pai[i] = null;
                t[i] = 0;
            }
        }
        q.add(g.vertices().get(inicial));

        while (!q.isEmpty()) {
            Vertice u = q.getFirst();
            q.removeFirst();
            adj = g.adjacentesDe(u);
            tempo++;
            for (Vertice v : adj) {
                if (cor[v.id()] == branco) {
                    arestas.add(g.arestasEntre(u, v).getFirst());
                    t[v.id()] = tempo;
                    cor[v.id()] = cinza;
                    d[v.id()] = d[u.id()] + 1;
                    pai[v.id()] = u;
                    q.add(v);
                }
                if (v.id() == destino.id()) {
                    break;
                }
                cor[u.id()] = preto;
            }
        }
        for (i = arestas.size() - 1; i >= 0; i--) {
            if (arestas.get(i).destino().id() == destino.id()) {
                aux.add(arestas.get(i));
            } else if (!aux.isEmpty()) {
                if (arestas.get(i).destino().id() == aux.getFirst().origem().id()) {
                    aux.addFirst(arestas.get(i));
                }
            }
        }
        for (Aresta a : aux) {
            printOutput(a.origem().id() + "->" + a.destino().id());
        }

        double somaPesosCaminhoMinimo = 0;
        for (Aresta a : aux) {
            somaPesosCaminhoMinimo += a.peso();
        }

        printOutput("Soma total dos pesos do caminho mínimo: " + somaPesosCaminhoMinimo);

        return aux;
    }

    @Override
    public boolean existeCiclo(Grafo g) throws Exception {
        return false;
    }

    @Override
    public Collection<Aresta> agmUsandoKruskall(Grafo g) throws Exception {
        ArrayList<Aresta>[] arestas = new ArrayList[g.numeroDeVertices()];
        ArrayList<Vertice> vertices = new ArrayList<>(g.vertices());
        ArrayList<Vertice> adj = new ArrayList<>();
        ArrayList<Aresta> arestaFinal = new ArrayList<>();
        int inicial = 0;

        inicial = Integer.parseInt(JOptionPane.showInputDialog("Escolha o Vertice Inicial:"));

        for (int i = 0; i < g.numeroDeVertices(); i++) {
            arestas[i] = new ArrayList<>();
        }
        for (int i = 0; i < g.numeroDeVertices(); i++) {
            adj = g.adjacentesDe(vertices.get(i));
            for (Vertice v : adj) {
                arestas[i].add(g.arestasEntre(vertices.get(i), v).getFirst());
            }
        }
        for (int i = 0; i < g.numeroDeVertices(); i++) {
            arestaFinal.addAll(arestas[i]);
        }

        Collection<Aresta> X = new ArrayList<>();

        Map<Integer, Integer> conjunto = new HashMap<>();

        for (Vertice v : g.vertices()) {
            criarConjunto(conjunto, v.id());
        }

        ArrayList<Aresta> arestasOrdenadas = new ArrayList<>(arestaFinal);
        arestasOrdenadas.sort(Comparator.comparingDouble(Aresta::peso));
        for (int i = 0; i < arestasOrdenadas.size(); i++) {
            if ((arestasOrdenadas.get(i).origem().id() == inicial) && (arestasOrdenadas.get(i).origem().id() != arestasOrdenadas.get(i).destino().id())) {
                Aresta aux = arestasOrdenadas.get(i);
                arestasOrdenadas.remove(i);
                arestasOrdenadas.addFirst(aux);
                break;
            }
        }

        for (int i = 0; i < arestasOrdenadas.size(); i++) {
            if (arestasOrdenadas.get(i).origem().id() == inicial) {
                Aresta arestaInicial = arestasOrdenadas.remove(i);
                arestasOrdenadas.addFirst(arestaInicial);
                break;
            }
        }

        for (Aresta aresta : arestasOrdenadas) {
            int u = aresta.origem().id();
            int v = aresta.destino().id();

            if (!conjuntoDe(conjunto, u).equals(conjuntoDe(conjunto, v))) {
                X.add(aresta);
                aplicarUniao(conjunto, u, v);
            }
        }

        for (Aresta aresta : X) {
            printOutput("   Origem: " + aresta.origem().id() +
                    ", Destino: " + aresta.destino().id() +
                    ", Peso: " + aresta.peso());
        }

        // aqui calcula a soma dos pesos das arestas na árvore geradora mínima
        double somaPesosAGM = 0;
        for (Aresta aresta : X) {
            somaPesosAGM += aresta.peso();
        }

        // aqui imprime a soma dos pesos no final do método
        printOutput("Soma total dos pesos da árvore geradora mínima: " + somaPesosAGM);

        return X;
    }

    @Override
    public double custoDaArvoreGeradora(Grafo g, Collection<Aresta> arestas) throws Exception {
        return 0;
    }

    @Override
    public boolean ehArvoreGeradora(Grafo g, Collection<Aresta> arestas) {
        return false;
    }

    @Override
    public ArrayList<Aresta> caminhoMaisCurto(Grafo g, Vertice origem, Vertice destino) throws Exception {
        double[] d = new double[g.numeroDeVertices()];
        Vertice[] pai = new Vertice[g.numeroDeVertices()];
        ArrayList<Aresta> caminho = new ArrayList<>();

        for (Vertice v : g.vertices()) {
            d[v.id()] = Double.MAX_VALUE;
            pai[v.id()] = null;
        }
        d[origem.id()] = 0;

        ArrayList<Vertice> adj = new ArrayList<>();
        ArrayList<Aresta>[] arestas = new ArrayList[g.numeroDeVertices()];
        ArrayList<Aresta> arestaFinal = new ArrayList<>();
        for (int i = 0; i < g.numeroDeVertices(); i++) {
            arestas[i] = new ArrayList<>();
        }
        for (int i = 0; i < g.numeroDeVertices(); i++) {
            adj = g.adjacentesDe(g.vertices().get(i));
            for (Vertice v : adj) {
                arestas[i].add(g.arestasEntre(g.vertices().get(i), v).getFirst());
            }
        }
        for (int i = 0; i < g.numeroDeVertices(); i++) {
            arestaFinal.addAll(arestas[i]);
        }

        for (int i = 0; i < g.numeroDeVertices() - 1; i++) {
            for (Aresta a : arestaFinal) {
                Relaxa(g, a.origem(), a.destino(), d, pai);
            }
        }

        printOutput("==-==-Vertices-==-==");
        for (int i = 0; i < g.numeroDeVertices(); i++) {
            printOutput("[" + i + "] ");
        }
        printOutput("");
        for (int i = 0; i < g.numeroDeVertices(); i++) {
            if (pai[i] == null) {
                printOutput("[X] ");
            } else {
                printOutput("[" + pai[i].id() + "] ");
            }
        }
        printOutput("");
        for (int i = 0; i < g.numeroDeVertices(); i++) {
            printOutput("Peso para chegar em " + i + ": " + d[i]);
        }

        Vertice atual = destino;
        while (atual != null && pai[atual.id()] != null) {
            Vertice paiAtual = pai[atual.id()];
            Aresta aresta = g.arestasEntre(paiAtual, atual).getFirst();
            caminho.addFirst(aresta);
            atual = paiAtual;
        }

        printOutput("Peso total para chegar em " + destino.id() + ": " + d[caminho.getLast().destino().id()]);

        return caminho;
    }

    @Override
    public double custoDoCaminho(Grafo g, ArrayList<Aresta> arestas, Vertice origem, Vertice destino) throws Exception {
        double custo = 0;
        int i = 0;
        ArrayList<Aresta> aux = new ArrayList<>();
        for (i = arestas.size() - 1; i >= 0; i--) {
            if (arestas.get(i).destino().id() == destino.id()) {
                aux.add(arestas.get(i));
            } else if (!aux.isEmpty()) {
                if (arestas.get(i).destino().id() == aux.getFirst().origem().id()) {
                    aux.addFirst(arestas.get(i));
                }
            }

        }
        for (Aresta a : aux) {
            if (!(ehCaminho(aux, origem, destino))) {
                aux.remove(a);
            }
            custo += a.peso();
        }

        return custo;
    }

    @Override
    public boolean ehCaminho(ArrayList<Aresta> arestas, Vertice origem, Vertice destino) {
        Map<Integer, List<Integer>> grafo = new HashMap<>();
        for (Aresta aresta : arestas) {
            grafo.putIfAbsent(aresta.origem().id(), new ArrayList<>());
            grafo.get(aresta.origem().id()).add(aresta.destino().id());
        }

        Set<Integer> visitados = new HashSet<>();
        Queue<Integer> fila = new LinkedList<>();
        fila.add(origem.id());

        while (!fila.isEmpty()) {
            int atual = fila.poll();
            if (atual == destino.id()) {
                return true;
            }
            visitados.add(atual);
            if (grafo.containsKey(atual)) {
                for (int vizinho : grafo.get(atual)) {
                    if (!visitados.contains(vizinho)) {
                        fila.add(vizinho);
                    }
                }
            }
        }

        return false;
    }

    @Override
    public Collection<Aresta> arestasDeArvore(Grafo g) {
        return List.of();
    }

    @Override
    public Collection<Aresta> arestasDeRetorno(Grafo g) {
        return List.of();
    }

    @Override
    public Collection<Aresta> arestasDeAvanco(Grafo g) {
        return List.of();
    }

    @Override
    public Collection<Aresta> arestasDeCruzamento(Grafo g) {
        return List.of();
    }

    public int AndarNaArvore(ArvoreDeBusca[] arvore, Grafo grafo, Vertice vertice, int t, int f, ArrayList<Aresta> arestas) throws Exception {
        int branco = 0, cinza = 1, preto = 2;

        arvore[vertice.id()].setCor(cinza);
        t++;
        arvore[vertice.id()].setTempo(t);

        ArrayList<Vertice> adj = grafo.adjacentesDe(vertice);

        for (Vertice adjacente : adj) {
            if (arvore[adjacente.id()].getCor() == branco) {
                Aresta aresta = grafo.arestasEntre(vertice, adjacente).get(0);
                arestas.add(aresta);
                arvore[vertice.id()].setTree(aresta);

                t = AndarNaArvore(arvore, grafo, adjacente, t, f, arestas);
            } else if (arvore[adjacente.id()].getCor() == cinza) {
                Aresta aresta = grafo.arestasEntre(vertice, adjacente).get(0);
                arvore[vertice.id()].setBack(aresta);
            } else if (arvore[adjacente.id()].getTempo() > arvore[vertice.id()].getTempo()) {
                Aresta aresta = grafo.arestasEntre(vertice, adjacente).get(0);
                arvore[vertice.id()].setForward(aresta);
            } else {
                Aresta aresta = grafo.arestasEntre(vertice, adjacente).get(0);
                arvore[vertice.id()].setCross(aresta);
            }
        }

        arvore[vertice.id()].setCor(preto);
        t++;
        arvore[vertice.id()].setF(t);
        return t;
    }

    private void criarConjunto(Map<Integer, Integer> conjunto, int vertice) {
        conjunto.put(vertice, vertice);
    }

    private Integer conjuntoDe(Map<Integer, Integer> conjunto, int vertice) {
        if (conjunto.get(vertice) != vertice) {
            conjunto.put(vertice, conjuntoDe(conjunto, conjunto.get(vertice)));
        }
        return conjunto.get(vertice);
    }

    private void aplicarUniao(Map<Integer, Integer> conjunto, int u, int v) {
        int conjuntoU = conjuntoDe(conjunto, u);
        int conjuntoV = conjuntoDe(conjunto, v);
        conjunto.put(conjuntoU, conjuntoV);
    }

    public void Relaxa(Grafo g, Vertice u, Vertice v, double[] d, Vertice[] pai) throws Exception {
        if (d[v.id()] < (d[u.id()] + custoDoCaminho(g, g.arestasEntre(u, v), u, v))) {
            return;
        }
        if (d[v.id()] > (d[u.id()] + custoDoCaminho(g, g.arestasEntre(u, v), u, v))) {
            d[v.id()] = d[u.id()] + custoDoCaminho(g, g.arestasEntre(u, v), u, v);
            pai[v.id()] = u;
        }
    }

    public void FluxoMaximo(Grafo g, Vertice origem, Vertice destino) throws Exception {
        double[][] fluxo = new double[g.numeroDeVertices()][g.numeroDeVertices()];
        for (int u = 0; u < g.numeroDeVertices(); u++) {
            for (int v = 0; v < g.numeroDeVertices(); v++) {
                fluxo[u][v] = 0;
            }
        }

        Map<Aresta, Double> pesosOriginais = new HashMap<>();
        for (Vertice v : g.vertices()) {
            for (Vertice adj : g.adjacentesDe(v)) {
                for (Aresta aresta : g.arestasEntre(v, adj)) {
                    pesosOriginais.put(aresta, aresta.peso());
                }
            }
        }

        double fluxoMaximo = 0;
        ArrayList<Aresta> arestas = new ArrayList<>(pesosOriginais.keySet());
        Map<Integer, Integer> pai;
        while ((pai = construirPai(arestas, origem, destino)) != null) {
            double cf = Double.MAX_VALUE;
            int atual = destino.id();

            while (atual != origem.id()) {
                int paiAtual = pai.get(atual);
                cf = Math.min(cf, capacidadeResidual(arestas, paiAtual, atual, fluxo));
                atual = paiAtual;
            }

            atual = destino.id();
            while (atual != origem.id()) {
                int paiAtual = pai.get(atual);
                fluxo[paiAtual][atual] += cf;
                fluxo[atual][paiAtual] -= cf;
                atual = paiAtual;
            }

            fluxoMaximo += cf;

            for (Aresta aresta : arestas) {
                int origemId = aresta.origem().id();
                int destinoId = aresta.destino().id();
                double pesoOriginal = pesosOriginais.get(aresta);
                aresta.setarPeso(pesoOriginal - fluxo[origemId][destinoId]);
            }
        }

        printOutput("Fluxo Máximo: " + fluxoMaximo);

        for (Map.Entry<Aresta, Double> entry : pesosOriginais.entrySet()) {
            entry.getKey().setarPeso(entry.getValue());
        }
    }

    private void atualizarGrafoResidual(ArrayList<Aresta> arestas, double[][] fluxo) {
        for (Aresta aresta : arestas) {
            int origem = aresta.origem().id();
            int destino = aresta.destino().id();
            aresta.setarPeso(aresta.peso() - fluxo[origem][destino]);
        }
    }

    private double capacidadeResidual(ArrayList<Aresta> arestas, int origem, int destino, double[][] fluxo) {
        for (Aresta aresta : arestas) {
            if (aresta.origem().id() == origem && aresta.destino().id() == destino) {
                return aresta.peso() - fluxo[origem][destino];
            }
        }
        return 0;
    }

    private Map<Integer, Integer> construirPai(ArrayList<Aresta> arestas, Vertice origem, Vertice destino) {
        Map<Integer, List<Integer>> grafo = new HashMap<>();
        Map<Integer, Integer> pai = new HashMap<>();

        for (Aresta aresta : arestas) {
            if (aresta.peso() > 0) {
                grafo.putIfAbsent(aresta.origem().id(), new ArrayList<>());
                grafo.get(aresta.origem().id()).add(aresta.destino().id());
            }
        }

        Set<Integer> visitados = new HashSet<>();
        Queue<Integer> fila = new LinkedList<>();
        fila.add(origem.id());
        pai.put(origem.id(), null);

        while (!fila.isEmpty()) {
            int atual = fila.poll();
            visitados.add(atual);

            if (grafo.containsKey(atual)) {
                for (int vizinho : grafo.get(atual)) {
                    if (!visitados.contains(vizinho)) {
                        pai.put(vizinho, atual);
                        fila.add(vizinho);
                        if (vizinho == destino.id()) {
                            return pai;
                        }
                    }
                }
            }
        }

        return null;
    }
}
