package grafos;

public class Vertice {
    private int vertice;
    private char cor;
    private double d, f;
    private Vertice pi;
    private double dist;

    public Vertice(int v) {
        this.vertice = v;
        this.cor = 'v';
        this.d = -1;
        this.f = -1;
        this.pi = null;
        this.dist = -1;
    }

    public int id() {
        return vertice;
    }

    public void setarVertice(int vertice) {
        this.vertice = vertice;
    }

    public char getCor() {
        return cor;
    }

    public void setCor(char cor) {
        this.cor = cor;
    }

    public double getD() {
        return d;
    }

    public void setD(double d) {
        this.d = d;
    }

    public double getF() {
        return f;
    }

    public void setF(double f) {
        this.f = f;
    }

    public Vertice getPi() {
        return pi;
    }

    public void setPi(Vertice pi) {
        this.pi = pi;
    }

    public double getDist() {
        return dist;
    }

    public void setDist(double dist) {
        this.dist = dist;
    }
}
