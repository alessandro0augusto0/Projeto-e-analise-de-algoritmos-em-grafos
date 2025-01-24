# Projeto e Análise de Algoritmos em Grafos

## Descrição

Neste trabalho, você deverá implementar um programa que atenda aos seguintes requisitos sobre Grafos:

1. **Carregamento do Grafo**:
    - O programa deverá carregar um grafo a partir de um arquivo de texto (especificado pelo usuário), como exemplificado no arquivo `Teste.txt`.
    - O grafo deverá ser armazenado em uma das três estruturas computacionais vistas em aula (matriz de adjacência, matriz de incidência ou lista de adjacência), conforme escolha do usuário.

2. **Algoritmos de Grafos**:
    - **Busca em Profundidade (DFS)**: O usuário deverá informar o vértice inicial da busca. Os resultados incluirão os tempos de descoberta (d) e finalização (f) de cada vértice. Além disso, o algoritmo deverá classificar as arestas em: arestas de árvore, arestas de retorno, arestas de avanço e arestas de cruzamento.
    - **Busca em Largura (BFS)**: O usuário deverá informar o vértice inicial da busca. Os resultados incluirão os tempos de descoberta (d) e o pai de cada vértice (π).
    - **Árvore Geradora Mínima (AGM)**: O usuário deverá informar o vértice inicial da busca. O resultado será o conjunto de arestas que compõem a AGM do grafo.
    - **Caminho Mínimo**: Algoritmo que calcula o caminho mínimo entre dois vértices. O usuário deverá informar o vértice inicial (s) e o vértice final (t). O resultado será o conjunto de arestas que compõem o caminho mínimo entre os vértices s e t.
    - **Fluxo Máximo**: Algoritmo que identifica o fluxo máximo em um grafo ponderado e orientado.

## Interface Gráfica

O programa possui uma interface gráfica que permite ao usuário selecionar o arquivo de entrada, escolher a estrutura de dados para armazenar o grafo e executar os algoritmos desejados. A interface também exibe os resultados dos algoritmos e uma visualização gráfica do grafo.

![Image](https://github.com/user-attachments/assets/11e2609e-230d-4ee2-a0dc-490f29f31d3b)

## Estrutura do Projeto

O projeto está organizado da seguinte forma:

- **Pacote `grafos`**:
  - **Classes de Estruturas de Dados**:
     - `Vertice`
     - `Aresta`
     - `MatrizAdjacencia`
     - `MatrizIncidencia`
     - `GrafoImplementacao`
  - **Enumeração**:
     - `TipoDeRepresentacao`
  - **Interface**:
     - `Grafo`
  - **Algoritmos**:
     - `AlgoritmosEmGrafos`
     - `AlgoritmosEmGrafosImpl`
  - **Utilitários**:
     - `FileManager`
  - **Interface Gráfica**:
     - `Gui`
     - `Gui2`
     - `GraphPanel`
     - `CustomOutputStream`

## Como Executar

1. Clone o repositório:
    ```sh
    git clone https://github.com/alessandro0augusto0/projeto-e-analise-de-algoritmos-em-grafos.git
    ```
2. Navegue até o diretório do projeto:
    ```sh
    cd projeto-e-analise-de-algoritmos-em-grafos
    ```
3. Compile e execute o programa:
    ```sh
    javac -d bin src/grafos/*.java
    java -cp bin grafos.Main
    ```

## Autor

- Alessandro Augusto

## Licença

Este projeto está licenciado sob a Licença MIT - veja o arquivo [LICENSE](LICENSE) para mais detalhes.