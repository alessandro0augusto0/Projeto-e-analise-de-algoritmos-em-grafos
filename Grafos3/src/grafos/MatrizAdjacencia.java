package grafos;

import java.util.ArrayList;

public class MatrizAdjacencia implements Grafo {

    Aresta[][] matriz;

    @Override
    public void adicionarAresta(Vertice origem, Vertice destino) throws Exception {
        matriz[origem.id()][destino.id()].setarDestino(destino);
        matriz[origem.id()][destino.id()].setarOrigem(origem);
        matriz[origem.id()][destino.id()].setarPeso(1);
    }

    @Override
    public void adicionarAresta(Vertice origem, Vertice destino, double peso) throws Exception {
        matriz[origem.id()][destino.id()].setarDestino(destino);
        matriz[origem.id()][destino.id()].setarOrigem(origem);
        matriz[origem.id()][destino.id()].setarPeso(peso);
    }

    @Override
    public boolean existeAresta(Vertice origem, Vertice destino) {
        for (int i = 0; i < matriz.length; i++) {
            if (matriz[origem.id()][i].destino() == destino) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int grauDoVertice(Vertice vertice) throws Exception {
        return 0;
    }

    @Override
    public int numeroDeVertices() {
        return matriz.length;
    }

    @Override
    public int numeroDeArestas() {
        int n = 0;
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                if (matriz[i][j].peso() < Double.MAX_VALUE) {
                    n++;
                }
            }
        }
        return n;
    }

    @Override
    public ArrayList<Vertice> adjacentesDe(Vertice vertice) throws Exception {

        ArrayList<Vertice> adjacentes = new ArrayList<>();

        for (int i = 0; i < matriz.length; i++) {
            if ((matriz[vertice.id()][i].peso() < Double.MAX_VALUE)) {
                adjacentes.add(matriz[vertice.id()][i].destino());
            }
        }

        return adjacentes;
    }

    @Override
    public void setarPeso(Vertice origem, Vertice destino, double peso) throws Exception {
        matriz[origem.id()][destino.id()].setarPeso(peso);
    }

    @Override
    public ArrayList<Aresta> arestasEntre(Vertice origem, Vertice destino) throws Exception {
        ArrayList<Aresta> arestas = new ArrayList<>();
        arestas.add(matriz[origem.id()][destino.id()]);
        return arestas;
    }

    @Override
    public ArrayList<Vertice> vertices() {
        ArrayList<Vertice> vertices = new ArrayList<>();
        for (int i = 0; i < matriz.length; i++) {
            vertices.add(new Vertice(i));
        }
        return vertices;
    }

    @Override
    public void ShowGrafo() {
        System.out.print("(X) ");
        for (int j = 0; j < matriz.length; j++) {
            System.out.print("( " + (j) + " )  ");
        }
        System.out.println();
        for (int i = 0; i < matriz.length; i++) {
            System.out.print("(" + (i) + ") ");
            for (int j = 0; j < matriz[i].length; j++) {
                if (matriz[i][j].peso() < Double.MAX_VALUE) {
                    System.out.print("[" + matriz[i][j].peso() + "] ");
                } else {
                    System.out.print("[ X ] ");
                }
            }
            System.out.println();
        }
    }

    public void Inicializar(int tam) {
        matriz = new Aresta[tam][tam];
        for (int i = 0; i < tam; i++) {
            for (int j = 0; j < tam; j++) {
                matriz[i][j] = new Aresta(new Vertice(0), new Vertice(0), 0);
                matriz[i][j].setarPeso(Double.MAX_VALUE);
            }
        }
    }

}
