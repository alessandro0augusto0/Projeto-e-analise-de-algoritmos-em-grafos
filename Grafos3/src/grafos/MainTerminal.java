package grafos;

import java.util.Scanner;

public class MainTerminal {
    public static void main(String[] args) throws Exception {
        int op = 0, entrada = 0;
        Scanner sc = new Scanner(System.in);
        Vertice origem = new Vertice(0);
        Vertice destino = new Vertice(0);
        Algoritmos alg = new Algoritmos(null);
        Grafo grafo;

        System.out.println(
                "Escolha o tipo de representação: \n1. Matriz de Adjacência\n2. Matriz de Incidência\n3. Lista de Adjacência");
        entrada = sc.nextInt();
        TipoDeRepresentacao tipo = TipoDeRepresentacao.fromTipoRe(entrada);

        String filePath = "src/test/Teste2.txt";

        grafo = alg.carregarGrafo(filePath, tipo);
        grafo.ShowGrafo();

        do {
            System.out.println("Menu:");
            System.out.println(
                    "1) Busca em Largura.\n2) Busca em Profundidade.\n3) Arvore Geradora Minima.\n4) Caminho Minimo.\n5) Fluxo Maximo.\n6) Sair.");
            op = sc.nextInt();
            switch (op) {
                case 1:
                    alg.buscaEmLargura(grafo);
                    break;
                case 2:
                    alg.buscaEmProfundidade(grafo);
                    break;
                case 3:
                    alg.agmUsandoKruskall(grafo);
                    break;
                case 4:
                    System.out.println("Escolha o vertice de origem e o vertice de destino respectivamente: ");
                    origem = new Vertice(sc.nextInt());
                    destino = new Vertice(sc.nextInt());
                    alg.caminhoMaisCurto(grafo, origem, destino);
                    break;
                case 5:
                    System.out.println("Escolha o vertice de origem e o vertice de destino respectivamente: ");
                    origem = new Vertice(sc.nextInt());
                    destino = new Vertice(sc.nextInt());
                    alg.FluxoMaximo(grafo, origem, destino);
                    break;
                case 6:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opcao invalida.");
                    break;
            }
        } while (op != 6);

        sc.close();
    }
}
