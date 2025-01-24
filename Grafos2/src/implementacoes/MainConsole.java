package implementacoes;

import java.util.ArrayList;
import java.util.Collection;

import grafos.*;

public class MainConsole {
	public static void mainConsole(String[] args) {
		String caminhoTeste = "Teste2.txt";

		Algoritmos alg = new Algoritmos();
		Grafo g;
		try {
			g = alg.carregarGrafo(caminhoTeste, TipoDeRepresentacao.LISTA_DE_ADJACENCIA);

			Collection<Aresta> DFS = alg.buscaEmProfundidade(g);
			System.out.println(DFS);

			Collection<Aresta> BFS = alg.buscaEmLargura(g);
			System.out.println(BFS);

			Collection<Aresta> AGM = alg.agmUsandoKruskall(g);
			System.out.println(AGM);

			Vertice origem = g.vertices().get(0), destino = g.vertices().get(10);
			ArrayList<Aresta> CMC = alg.caminhoMaisCurto(g, origem, destino);
			System.out.println(CMC);

			if (alg.ehCaminho(CMC, origem, destino))
				System.out.println("Esse caminho é válido");
			else
				System.out.println("Esse caminho não é válido");

			double custoCMC = alg.custoDoCaminho(g, CMC, origem, destino);
			System.out.println("Menor custo do caminho 0->10: " + custoCMC);

			double custoAGM = alg.custoDaArvoreGeradora(g, AGM);
			System.out.println("Custo da AGM: " + custoAGM);

		} catch (Exception e) {
			System.err.println(e);
		}
	}
}