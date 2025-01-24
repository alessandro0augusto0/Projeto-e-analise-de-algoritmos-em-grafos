package grafos;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author Alessandro Augusto
 * @since 09/01/2025
 */

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            String caminhoArquivo = "src/Teste.txt";

            FileManager fm = new FileManager();
            ArrayList<String> linhas = fm.stringReader(caminhoArquivo);

            if (linhas == null) {
                System.err.println("Erro ao ler o arquivo. Certifique-se de que o arquivo esta no local correto.");
                return;
            }

            System.out.println("Escolha a estrutura de dados para armazenar o grafo:");
            System.out.println("1. Matriz de Adjacencia");
            System.out.println("2. Matriz de Incidencia");
            System.out.println("3. Lista de Adjacencia");
            int escolhaEstrutura = scanner.nextInt();
            scanner.nextLine();

            TipoDeRepresentacao tipo = null;
            switch (escolhaEstrutura) {
                case 1:
                    tipo = TipoDeRepresentacao.MATRIZ_DE_ADJACENCIA;
                    break;
                case 2:
                    tipo = TipoDeRepresentacao.MATRIZ_DE_INCIDENCIA;
                    break;
                case 3:
                    tipo = TipoDeRepresentacao.LISTA_DE_ADJACENCIA;
                    break;
                default:
                    System.out.println("Opcao invalida.");
                    return;
            }

            AlgoritmosEmGrafos algoritmos = new AlgoritmosEmGrafosImpl();
            Grafo grafo = algoritmos.carregarGrafo(caminhoArquivo, tipo);

            System.out.println("Escolha o algoritmo a ser executado:");
            System.out.println("1. Busca em Profundidade (DFS)");
            System.out.println("2. Busca em Largura (BFS)");
            System.out.println("3. Arvore Geradora Minima (AGM)");
            System.out.println("4. Caminho Minimo");
            System.out.println("5. Fluxo Maximo");
            int escolhaAlgoritmo = scanner.nextInt();
            scanner.nextLine();

            switch (escolhaAlgoritmo) {
                case 1:
                    System.out.println("Digite o vertice inicial para a busca em profundidade:");
                    int verticeInicialDFS = scanner.nextInt();
                    grafo.buscaEmProfundidade(verticeInicialDFS);
                    break;
                case 2:
                    System.out.println("Digite o vertice inicial para a busca em largura:");
                    int verticeInicialBFS = scanner.nextInt();
                    grafo.buscaEmLargura(verticeInicialBFS);
                    break;
                case 3:
                    grafo.primMST();
                    break;
                case 4:
                    System.out.println("Digite o vertice inicial:");
                    int verticeInicialDijkstra = scanner.nextInt();
                    System.out.println("Digite o vertice final:");
                    int verticeFinalDijkstra = scanner.nextInt();
                    grafo.dijkstra(verticeInicialDijkstra, verticeFinalDijkstra);
                    break;
                case 5:
                    System.out.println("Digite o vertice de origem:");
                    int source = scanner.nextInt();
                    System.out.println("Digite o vertice de destino:");
                    int sink = scanner.nextInt();
                    grafo.edmondsKarp(source, sink);
                    break;
                default:
                    System.out.println("Opção invalida.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }
}
