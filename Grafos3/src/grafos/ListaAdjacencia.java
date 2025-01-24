package grafos;

import java.util.ArrayList;

public class ListaAdjacencia implements Grafo{

    ArrayList<Aresta>[] arestas;

    @Override
    public void adicionarAresta(Vertice origem, Vertice destino) throws Exception {
        arestas[origem.id()].add(new Aresta(origem, destino, 1));
    }

    @Override
    public void adicionarAresta(Vertice origem, Vertice destino, double peso) throws Exception {
        arestas[origem.id()].add(new Aresta(origem, destino, peso));
    }

    @Override
    public boolean existeAresta(Vertice origem, Vertice destino) {
        boolean existe = false;

        for(Aresta a : arestas[origem.id()]) {
            if(a.destino().id() == destino.id()) {
                existe = true;
            }
        }

        return existe;
    }

    @Override
    public int grauDoVertice(Vertice vertice) throws Exception {
        return 0;
    }

    @Override
    public int numeroDeVertices() {
        return arestas.length;
    }

    @Override
    public int numeroDeArestas() {
        int j=0;
        for(int i = 0; i < arestas.length; i++) {
            for(Aresta a : arestas[i]) {
                j++;
            }
        }
        return j;
    }

    @Override
    public ArrayList<Vertice> adjacentesDe(Vertice vertice) throws Exception {
        ArrayList<Vertice> adjacentes = new ArrayList<>();

        for(Aresta a : arestas[vertice.id()]) {
            adjacentes.add(a.destino());
        }

        return adjacentes;
    }

    @Override
    public void setarPeso(Vertice origem, Vertice destino, double peso) throws Exception {
        arestas[origem.id()].set(origem.id(), new Aresta(origem, destino, peso));
    }

    @Override
    public ArrayList<Aresta> arestasEntre(Vertice origem, Vertice destino) throws Exception {

        ArrayList<Aresta> arestasEntre = new ArrayList<>();

        for(Aresta a : arestas[origem.id()]) {
            if(a.destino().id() == destino.id()) {
                arestasEntre.add(a);
            }
        }
        for(Aresta a : arestas[destino.id()]){
            if(a.destino().id() == origem.id()){
                arestasEntre.add(a);
            }
        }

        return arestasEntre;
    }

    @Override
    public ArrayList<Vertice> vertices() {
        ArrayList<Vertice> vertices = new ArrayList<>();

        for(int i = 0; i < arestas.length; i++) {
            vertices.add(arestas[i].getFirst().origem());
        }

        return vertices;
    }

    public void Inicializar(int tam){
        arestas = new ArrayList[tam];
        Vertice vertice = new Vertice(0);
        vertice.setarVertice(0);

        for (int i = 0; i < tam; i++) {
            arestas[i] = new ArrayList<>();
        }
    }
    @Override
    public void ShowGrafo() {

        for(int i=0; i<arestas.length; i++){
            System.out.print(i+"--");
            for(Aresta a: arestas[i]){
                System.out.print("> [" + a.destino().id() + ", " + a.peso() + "]-");
            }
            System.out.println("//");
        }
    }
    public void MontarGrafo(int[] valores) throws Exception {

        int i=1;
        Vertice origem = new Vertice(valores[0]);
        int peso = 0;

        do{
            Vertice destino = new Vertice(valores[i]);
            i++;
            peso = valores[i];
            i++;
            adicionarAresta(origem, destino, peso);

        }while(i<valores.length);

    }
    public ArrayList<Aresta>[] getListaAdj(){
        return arestas;
    }
}
