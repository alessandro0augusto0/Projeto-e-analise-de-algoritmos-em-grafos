package grafos;

import java.util.ArrayList;

public class MatrizIncidencia implements Grafo {
    double[][] matriz;
    int numeroDeVertices;
    int numeroDeArestas;

    @Override
    public void adicionarAresta(Vertice origem, Vertice destino) throws Exception {
        expandirMatriz();
        matriz[origem.id()][numeroDeArestas - 1] = -1;
        matriz[destino.id()][numeroDeArestas - 1] = 1;
    }

    @Override
    public void adicionarAresta(Vertice origem, Vertice destino, double peso) throws Exception {
        expandirMatriz();
        matriz[origem.id()][numeroDeArestas - 1] = -peso;
        matriz[destino.id()][numeroDeArestas - 1] = peso;
    }

    @Override
    public boolean existeAresta(Vertice origem, Vertice destino) {
        for(int j=0;j<matriz.length;j++){
            if((matriz[origem.id()][j] == -matriz[destino.id()][j])){
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
        return numeroDeVertices;
    }

    @Override
    public int numeroDeArestas() {
        return numeroDeArestas;
    }

    @Override
    public ArrayList<Vertice> adjacentesDe(Vertice vertice) throws Exception {
        ArrayList<Vertice> adjacentes = new ArrayList<>();
        for (int j = 0; j < matriz[0].length; j++) {
            if (matriz[vertice.id()][j] < 0) {
                for (int k = 0; k < matriz.length; k++) {
                    if (matriz[k][j] > 0) {
                        adjacentes.add(new Vertice(k));
                    }
                }
            }
        }
        return adjacentes;
    }


    @Override
    public void setarPeso(Vertice origem, Vertice destino, double peso) throws Exception {
        for (int j = 0; j < matriz[0].length; j++) {
            if (matriz[origem.id()][j] < 0 && matriz[destino.id()][j] > 0) {
                matriz[origem.id()][j] = -peso;
                matriz[destino.id()][j] = peso;
                return;
            }
        }
    }

    @Override
    public ArrayList<Aresta> arestasEntre(Vertice origem, Vertice destino) throws Exception {
        ArrayList<Aresta> arestas = new ArrayList<>();
        for (int j = 0; j < matriz[0].length; j++) {
            if (matriz[origem.id()][j] < 0 && matriz[destino.id()][j] > 0) {
                arestas.add(new Aresta(origem, destino, matriz[destino.id()][j]));
            }
        }
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
        System.out.print("    ");
        for (int j = 0; j < numeroDeArestas; j++) {
            System.out.print("A" + j + "   ");
        }
        System.out.println();
        for (int i = 0; i < numeroDeVertices; i++) {
            System.out.print("V" + i + " ");
            for (int j = 0; j < numeroDeArestas; j++) {
                System.out.printf("%4.1f ", matriz[i][j]);
            }
            System.out.println();
        }
    }
    public void inicializar(int numeroDeVertices){
        this.numeroDeVertices = numeroDeVertices;
        this.matriz = new double[numeroDeVertices][0];
        this.numeroDeArestas = 0;
    }
    private void expandirMatriz() {
        int novoNumeroDeArestas = numeroDeArestas + 1;
        double[][] novaMatriz = new double[numeroDeVertices][novoNumeroDeArestas];
        for (int i = 0; i < numeroDeVertices; i++) {
            System.arraycopy(matriz[i], 0, novaMatriz[i], 0, numeroDeArestas);
        }
        matriz = novaMatriz;
        numeroDeArestas = novoNumeroDeArestas;
    }

}
