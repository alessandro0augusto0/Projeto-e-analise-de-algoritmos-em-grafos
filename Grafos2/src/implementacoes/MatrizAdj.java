package implementacoes;

import java.util.ArrayList;

import grafos.Aresta;
import grafos.Grafo;
import grafos.Vertice;

public class MatrizAdj implements Grafo {

	private int numVerts;
	private double[][][] matVerts;
	private ArrayList<Vertice> arrayVerts;
	private ArrayList<Aresta> arrayAres;

	public double[][][] getMatVerts() {
		return matVerts;
	}

	public int getNumVerts() {
		return numVerts;
	}

	public void setNumVerts(int numVerts) {
		this.numVerts = numVerts;
	}

	public void setMatVerts(double[][][] matVerts) {
		this.matVerts = matVerts;
	}

	public ArrayList<Vertice> getArrayVerts() {
		return arrayVerts;
	}

	public void setArrayVerts(ArrayList<Vertice> arrayVerts) {
		this.arrayVerts = arrayVerts;
	}

	public ArrayList<Aresta> getArrayAres() {
		return arrayAres;
	}

	public void setArrayAres(ArrayList<Aresta> arrayAres) {
		this.arrayAres = arrayAres;
	}

	@Override
	public String toString() {
		String saida = "Matriz de Adjacência: \"origens nas linhas, destinos nas colunas\"\n\n";

		for (int i = 0; i < this.numVerts; i++) {
			for (int j = 0; j < this.numVerts; j++) {
				saida += "  |  ";
				switch (this.matVerts[i][j].length) {
					case 0:
						saida += "0.00";
						break;
					case 1:
						saida += matVerts[i][j][0];
						break;
					default:
						saida += "[";
						for (double d : matVerts[i][j])
							saida += "  " + d;
						saida += " ]";
						break;
				}
				while (saida.length() - saida.lastIndexOf('|') < 15)
					saida += ' ';
			}
			saida += "\n";
		}

		return saida;
	}

	public MatrizAdj(ArrayList<String> entrada) {
		this.setNumVerts(Integer.parseInt(entrada.get(0)));
		entrada.remove(0);

		this.matVerts = new double[this.numVerts][this.numVerts][0];
		this.arrayAres = new ArrayList<Aresta>();
		this.arrayVerts = new ArrayList<Vertice>();

		for (int i = 0; i < this.numVerts; i++) {
			this.arrayVerts.add(new Vertice(i));
		}

		for (String l : entrada) {
			String[] linha = l.split(" ");
			Vertice vOrigem = this.arrayVerts.get(Integer.parseInt(linha[0]));

			for (int j = 1; j < linha.length; j++) {
				String strDest = linha[j].substring(0, linha[j].indexOf('-'));
				String strPeso = linha[j].substring(linha[j].indexOf('-') + 1, linha[j].indexOf(';'));

				Vertice vDest = this.arrayVerts.get(Integer.parseInt(strDest));

				this.arrayAres.add(new Aresta(vOrigem, vDest, Integer.parseInt(strPeso)));

				double[] aux = new double[this.matVerts[this.arrayVerts.indexOf(vOrigem)][this.arrayVerts
						.indexOf(vDest)].length + 1];
				for (int i = 0; i < this.matVerts[this.arrayVerts.indexOf(vOrigem)][this.arrayVerts
						.indexOf(vDest)].length; i++)
					aux[i] = this.matVerts[this.arrayVerts.indexOf(vOrigem)][this.arrayVerts.indexOf(vDest)][i];
				this.matVerts[this.arrayVerts.indexOf(vOrigem)][this.arrayVerts.indexOf(vDest)] = aux;
				int espaco = this.matVerts[this.arrayVerts.indexOf(vOrigem)][this.arrayVerts.indexOf(vDest)].length - 1;
				this.matVerts[this.arrayVerts.indexOf(vOrigem)][this.arrayVerts.indexOf(vDest)][espaco] = Double
						.parseDouble(strPeso);
			}
		}
	}

	@Override
	public void adicionarAresta(Vertice origem, Vertice destino) throws Exception {
		double[] aux = new double[this.matVerts[this.arrayVerts.indexOf(origem)][this.arrayVerts
				.indexOf(destino)].length + 1];
		for (int i = 0; i < this.matVerts[this.arrayVerts.indexOf(origem)][this.arrayVerts
				.indexOf(destino)].length; i++)
			aux[i] = this.matVerts[this.arrayVerts.indexOf(origem)][this.arrayVerts.indexOf(destino)][i];
		this.matVerts[this.arrayVerts.indexOf(origem)][this.arrayVerts.indexOf(destino)] = aux;
		int espaco = this.matVerts[this.arrayVerts.indexOf(origem)][this.arrayVerts.indexOf(destino)].length - 1;
		this.matVerts[this.arrayVerts.indexOf(origem)][this.arrayVerts.indexOf(destino)][espaco] = 1;
		this.arrayAres.add(new Aresta(origem, destino));
	}

	@Override
	public void adicionarAresta(Vertice origem, Vertice destino, double peso) throws Exception {
		double[] aux = new double[this.matVerts[this.arrayVerts.indexOf(origem)][this.arrayVerts
				.indexOf(destino)].length + 1];
		for (int i = 0; i < this.matVerts[this.arrayVerts.indexOf(origem)][this.arrayVerts
				.indexOf(destino)].length; i++)
			aux[i] = this.matVerts[this.arrayVerts.indexOf(origem)][this.arrayVerts.indexOf(destino)][i];
		this.matVerts[this.arrayVerts.indexOf(origem)][this.arrayVerts.indexOf(destino)] = aux;
		int espaco = this.matVerts[this.arrayVerts.indexOf(origem)][this.arrayVerts.indexOf(destino)].length - 1;
		this.matVerts[this.arrayVerts.indexOf(origem)][this.arrayVerts.indexOf(destino)][espaco] = peso;
		this.arrayAres.add(new Aresta(origem, destino, peso));
	}

	@Override
	public boolean existeAresta(Vertice origem, Vertice destino) {
		if (this.matVerts[origem.id()][destino.id()].length > 0)
			return true;
		else
			return false;
	}

	@Override
	public int grauDoVertice(Vertice vertice) throws Exception {
		int grau = 0;

		for (int i = 0; i < this.numVerts; i++)
			if (this.matVerts[vertice.id()][i].length > 0)
				grau++;

		return grau;
	}

	@Override
	public int numeroDeVertices() {
		return this.numVerts;
	}

	@Override
	public int numeroDeArestas() {
		return this.arrayAres.size();
	}

	@Override
	public ArrayList<Vertice> adjacentesDe(Vertice vertice) throws Exception {
		int idVert = this.arrayVerts.indexOf(vertice);
		ArrayList<Vertice> listVerts = new ArrayList<Vertice>();

		for (int i = 0; i < this.numVerts; i++)
			if (this.matVerts[idVert][i].length > 0)
				listVerts.add(this.arrayVerts.get(i));

		return listVerts;
	}

	@Override
	public void setarPeso(Vertice origem, Vertice destino, double peso) throws Exception {
		this.arestasEntre(origem, destino).get(0).setarPeso(peso);
		this.matVerts[origem.id()][destino.id()][0] = peso;
	}

	@Override
	public ArrayList<Vertice> vertices() {
		ArrayList<Vertice> clone = new ArrayList<Vertice>();
		clone.addAll(arrayVerts);
		return clone;
	}

	@Override
	public ArrayList<Aresta> arestasEntre(Vertice origem, Vertice destino) throws Exception {
		ArrayList<Aresta> listAres = new ArrayList<Aresta>();

		for (Aresta a : this.arrayAres)
			if (a.destino() == destino && a.origem() == origem)
				listAres.add(a);

		return listAres;
	}
}