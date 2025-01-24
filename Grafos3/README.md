# Projeto e Análise de Algoritmos em Grafos

## Descrição

Neste trabalho, você deverá implementar um programa que atenda aos seguintes requisitos sobre Grafos:

1. **Carregar Grafo**: O programa deverá carregar um grafo a partir de um arquivo de texto (especificado pelo usuário), como exemplificado no arquivo `Teste.txt`. O arquivo `Teste - Grafo.jpg` apresenta uma visualização gráfica do grafo contido no `Teste.txt`.

2. **Estruturas de Dados**: O grafo deverá ser armazenado em uma das três estruturas computacionais vistas em aula (matriz de adjacência, matriz de incidência ou lista de adjacência), conforme escolha do usuário.

3. **Algoritmos de Grafos**: Ao carregar o grafo, o programa deverá solicitar ao usuário qual algoritmo de grafos ele deseja executar. O programa deverá implementar os seguintes algoritmos:
    - **Busca em Profundidade (DFS)**: O usuário deverá informar o vértice inicial da busca. Os resultados incluirão os tempos de descoberta (d) e finalização (f) de cada vértice. Além disso, o algoritmo deverá classificar as arestas em: arestas de árvore, arestas de retorno, arestas de avanço e arestas de cruzamento.
    - **Busca em Largura (BFS)**: O usuário deverá informar o vértice inicial da busca. Os resultados incluirão os tempos de descoberta (d) e o pai de cada vértice (π).
    - **Árvore Geradora Mínima (AGM)**: O usuário deverá informar o vértice inicial da busca. O resultado será o conjunto de arestas que compõem a AGM do grafo.
    - **Caminho Mínimo**: Algoritmo que calcula o caminho mínimo entre dois vértices. O usuário deverá informar o vértice inicial (s) e o vértice final (t). O resultado será o conjunto de arestas que compõem o caminho mínimo entre os vértices s e t.
    - **Fluxo Máximo**: Algoritmo que identifica o fluxo máximo em um grafo ponderado e orientado.

## Interface Gráfica

O programa possui uma interface gráfica para facilitar a interação do usuário com os algoritmos de grafos. A interface permite carregar grafos, executar algoritmos e visualizar os resultados de forma gráfica.

![Image](https://github.com/user-attachments/assets/99aee921-fff4-4bbf-808c-55b7eefee0fb)

## Estrutura do Projeto

O projeto está organizado da seguinte forma:

- **`grafos`**: Pacote contendo as classes principais do projeto.
  - **`TipoDeRepresentacao`**: Enum para representar os tipos de representação de grafos.
  - **`Vertice`**: Classe para representar um vértice do grafo.
  - **`MatrizIncidencia`**: Implementação da estrutura de matriz de incidência.
  - **`MatrizAdjacencia`**: Implementação da estrutura de matriz de adjacência.
  - **`ListaAdjacencia`**: Implementação da estrutura de lista de adjacência.
  - **`MainTerminal`**: Classe principal para execução no terminal.
  - **`Main`**: Classe principal para execução da interface gráfica.
  - **`GraphGUI`**: Classe da interface gráfica.
  - **`GraphPanel`**: Painel para desenhar o grafo na interface gráfica.
  - **`FileManager`**: Classe para gerenciar leitura e escrita de arquivos.
  - **`ArvoreDeBusca`**: Classe para representar a árvore de busca.
  - **`Aresta`**: Classe para representar uma aresta do grafo.
  - **`Algoritmos`**: Implementação dos algoritmos de grafos.
  - **`AlgoritmosEmGrafos`**: Interface para os algoritmos de grafos.

## Como Executar

1. Clone o repositório:
    ```bash
    git clone https://github.com/alessandro0augusto0/projeto-e-analise-de-algoritmos-em-grafos.git
    ```
2. Navegue até o diretório do projeto:
    ```bash
    cd projeto-e-analise-de-algoritmos-em-grafos
    ```
3. Compile o projeto:
    ```bash
    javac -d bin src/grafos/*.java
    ```
4. Execute o programa:
    - Para a interface gráfica:
      ```bash
      java -cp bin grafos.Main
      ```
    - Para o terminal:
      ```bash
      java -cp bin grafos.MainTerminal
      ```

## Contribuição

Contribuições são bem-vindas! Sinta-se à vontade para abrir issues e pull requests.

## Licença

Este projeto está licenciado sob a licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.