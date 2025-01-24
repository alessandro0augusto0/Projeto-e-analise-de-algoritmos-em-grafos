package implementacoes;

import java.util.ArrayList;

import grafos.Aresta;
import grafos.Grafo;
import grafos.Vertice;

public class MatrizInc implements Grafo {

	private int numVerts;
	private int numAres;
	private double[][] matAres;
	private ArrayList<Vertice> arrayVerts;
	private ArrayList<Aresta> arrayAres;

	public int getNumAres() {
		return numAres;
	}

	public void setNumAres(int numAres) {
		this.numAres = numAres;
	}

	public double[][] getmatAres() {
		return matAres;
	}

	public int getNumVerts() {
		return numVerts;
	}

	public void setNumVerts(int numVerts) {
		this.numVerts = numVerts;
	}

	public void setmatAres(double[][] matAres) {
		this.matAres = matAres;
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
		String saida = "Matriz de Incidência: \"arestas nas colunas, vértices nas linhas\"\n\n";

		for (int i = 0; i < this.numVerts; i++) {
			for (int j = 0; j < this.numAres; j++) {
				saida += "  |  " + this.matAres[i][j];
				while (saida.length() - saida.lastIndexOf('|') < 10)
					saida += ' ';
			}
			saida += "\n";
		}

		return saida;
	}

	public MatrizInc(ArrayList<String> entrada) {
		this.numVerts = Integer.parseInt(entrada.get(0));
		entrada.remove(0);

		this.numAres = 0;
		for (String l : entrada)
			this.numAres += l.split(" ").length - 1;

		this.matAres = new double[this.numVerts][this.numAres];
		this.arrayAres = new ArrayList<Aresta>();
		this.arrayVerts = new ArrayList<Vertice>();

		for (int i = 0; i < this.numVerts; i++) {
			this.arrayVerts.add(new Vertice(i));
		}

		int aux = 0;
		for (String l : entrada) {
			String[] linha = l.split(" ");
			Vertice vOrigem = this.arrayVerts.get(Integer.parseInt(linha[0]));

			for (int j = 1; j < linha.length; j++) {
				String strDest = linha[j].substring(0, linha[j].indexOf('-'));
				String strPeso = linha[j].substring(linha[j].indexOf('-') + 1, linha[j].indexOf(';'));

				Vertice vDest = this.arrayVerts.get(Integer.parseInt(strDest));

				this.arrayAres.add(new Aresta(vOrigem, vDest, Integer.parseInt(strPeso)));

				this.matAres[vOrigem.id()][aux] = -Integer.parseInt(strPeso);
				this.matAres[Integer.parseInt(strDest)][aux] = Integer.parseInt(strPeso);
				aux++;
			}
		}
	}

	@Override
	public int grauDoVertice(Vertice vertice) throws Exception {
		int grau = 0;

		for (int i = 0; i < this.numAres; i++)
			if (this.matAres[vertice.id()][i] > 0)
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
	public void adicionarAresta(Vertice origem, Vertice destino) throws Exception {
		this.numAres++;
		double[][] newArray = new double[this.numVerts][this.numAres];

		for (int i = 0; i < this.numVerts; i++)
			for (int j = 0; j < this.numAres - 1; j++)
				newArray[i][j] = this.matAres[i][j];

		this.matAres = newArray;
		this.matAres[destino.id()][this.numAres - 1] = 1;
		this.arrayAres.add(new Aresta(origem, destino));
	}

	@Override
	public void adicionarAresta(Vertice origem, Vertice destino, double peso) throws Exception {
		this.numAres++;
		double[][] newArray = new double[this.numVerts][this.numAres];

		for (int i = 0; i < this.numVerts; i++)
			for (int j = 0; j < this.numAres - 1; j++)
				newArray[i][j] = this.matAres[i][j];

		this.matAres = newArray;
		this.matAres[destino.id()][this.numAres - 1] = peso;
		this.arrayAres.add(new Aresta(origem, destino, peso));
	}

	@Override
	public boolean existeAresta(Vertice origem, Vertice destino) {
		for (Aresta a : this.arrayAres)
			if (a.origem() == origem && a.destino() == destino)
				return true;
		return false;
	}

	@Override
	public ArrayList<Vertice> adjacentesDe(Vertice vertice) throws Exception {
		ArrayList<Vertice> listVerts = new ArrayList<Vertice>();

		for (int i = 0; i < this.numAres; i++)
			if (this.matAres[vertice.id()][i] != 0) {
				for (int j = 0; j < this.numVerts; j++)
					if (this.matAres[j][i] > 0) {
						listVerts.add(this.arrayVerts.get(j));
						break;
					}
			}

		return listVerts;
	}

	@Override
	public void setarPeso(Vertice origem, Vertice destino, double peso) throws Exception {
		try {
			int vO = this.arrayVerts.indexOf(origem);
			int vD = this.arrayVerts.indexOf(destino);
			if (vO == -1 || vD == -1)
				vO = 1 / 0;
			for (int i = 0; i < this.numAres; i++) {
				Aresta a = this.arrayAres.get(i);
				if (a.destino() == destino && a.origem() == origem) {
					a.setarPeso(peso);
					this.matAres[destino.id()][i] = peso;
					break;
				}
			}
		} catch (Exception e) {
			System.err.println(e);
		}
		this.matAres[origem.id()][destino.id()] = peso;

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