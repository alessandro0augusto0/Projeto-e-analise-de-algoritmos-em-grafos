# Projeto e Análise de Algoritmos em Grafos

## Descrição

Neste trabalho, você deverá implementar um programa que atenda aos seguintes requisitos sobre Grafos:

1. **Carregamento de Grafo**:
    - O programa deverá carregar um grafo a partir de um arquivo de texto (especificado pelo usuário), como exemplificado no arquivo `Teste.txt`.
    - O grafo deverá ser armazenado em uma das três estruturas computacionais vistas em aula (matriz de adjacência, matriz de incidência ou lista de adjacência), conforme escolha do usuário.

2. **Algoritmos de Grafos**:
    - **Busca em Profundidade (DFS)**: O usuário deverá informar o vértice inicial da busca. Os resultados incluirão os tempos de descoberta (d) e finalização (f) de cada vértice. Além disso, o algoritmo deverá classificar as arestas em: arestas de árvore, arestas de retorno, arestas de avanço e arestas de cruzamento.
    - **Busca em Largura (BFS)**: O usuário deverá informar o vértice inicial da busca. Os resultados incluirão os tempos de descoberta (d) e o pai de cada vértice (π).
    - **Árvore Geradora Mínima (AGM)**: O usuário deverá informar o vértice inicial da busca. O resultado será o conjunto de arestas que compõem a AGM do grafo.
    - **Caminho Mínimo**: Algoritmo que calcula o caminho mínimo entre dois vértices. O usuário deverá informar o vértice inicial (s) e o vértice final (t). O resultado será o conjunto de arestas que compõem o caminho mínimo entre os vértices s e t.
    - **Fluxo Máximo**: Algoritmo que identifica o fluxo máximo em um grafo ponderado e orientado.

## Interface Gráfica

O programa possui uma interface gráfica para facilitar a interação do usuário com as funcionalidades implementadas.

![Image](https://github.com/user-attachments/assets/80951d6b-3c6c-4724-a0a3-1d9641917fd0)

## Estrutura do Projeto

O projeto está organizado da seguinte forma:

- **implementacoes**: Contém as implementações das estruturas de dados e algoritmos.
- **grafos**: Contém as definições das classes relacionadas aos grafos.

## Como Executar

1. Clone o repositório:
    ```bash
    git clone https://github.com/alessandro0augusto0/projeto-e-analise-de-algoritmos-em-grafos.git
    ```
2. Navegue até o diretório do projeto:
    ```bash
    cd projeto-e-analise-de-algoritmos-em-grafos
    ```
3. Compile e execute o programa:
    ```bash
    javac -d bin src/**/*.java
    java -cp bin implementacoes.MainFrame
    ```

## Contato

Para mais informações, entre em contato através do [GitHub](https://github.com/alessandro0augusto0).
