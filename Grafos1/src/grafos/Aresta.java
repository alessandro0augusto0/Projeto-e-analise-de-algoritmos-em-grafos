package grafos;

public class Aresta {

    private Vertice origem;
    private Vertice destino;
    private double peso;

    public Aresta(Vertice origem, Vertice destino) {
        this.origem = origem;
        this.destino = destino;
        this.peso = 1;
    }

    public Aresta(Vertice origem, Vertice destino, double peso) {
        this.origem = origem;
        this.destino = destino;
        this.peso = peso;
    }

    public Vertice getOrigem() {
        return origem;
    }

    public void setOrigem(Vertice origem) {
        this.origem = origem;
    }

    public Vertice getDestino() {
        return destino;
    }

    public void setDestino(Vertice destino) {
        this.destino = destino;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    @Override
    public String toString() {
        return origem.getId() + " -> " + destino.getId() + " (peso: " + peso + ")";
    }
}
