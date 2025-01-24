package grafos;

import java.util.ArrayList;

public class ArvoreDeBusca {

    private int cor,tempo,f;
    private ArrayList<Aresta> tree = new ArrayList<>();
    private ArrayList<Aresta> back = new ArrayList<>();
    private ArrayList<Aresta> forward = new ArrayList<>();
    private ArrayList<Aresta> cross = new ArrayList<>();
    public ArvoreDeBusca(int cor, int tempo, int f, ArrayList<Aresta> tree, ArrayList<Aresta> back, ArrayList<Aresta> forward) {
        this.cor = cor;
        this.tempo = tempo;
        this.f = f;
        this.tree = tree;
        this.back = back;
        this.forward = forward;
    }
    public int getCor() {
        return cor;
    }
    public void setCor(int cor) {
        this.cor = cor;
    }
    public int getTempo() {
        return tempo;
    }
    public void setTempo(int tempo) {
        this.tempo = tempo;
    }
    public int getF() {
        return f;
    }
    public void setF(int f) {
        this.f = f;
    }
    public ArrayList<Aresta> getTree() {
        return tree;
    }
    public void setTree(Aresta newAresta) {
        this.tree.add(newAresta);
    }
    public ArrayList<Aresta> getBack() {
        return back;
    }
    public void setBack(Aresta newback) {
        this.back.add(newback);
    }
    public ArrayList<Aresta> getForward() {
        return forward;
    }
    public void setForward(Aresta newforward) {
        this.forward.add(newforward);
    }
    public ArrayList<Aresta> getCross() {
        return cross;
    }
    public void setCross(Aresta newcross) {
        this.cross.add(newcross);
    }
}
