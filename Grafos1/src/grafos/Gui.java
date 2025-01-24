package grafos;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

public class Gui extends JFrame {
    private JComboBox<String> estruturaComboBox;
    private JComboBox<String> algoritmoComboBox;
    private JTextField caminhoArquivoField;
    private JTextArea resultadoArea;
    private Grafo grafo;
    private GraphPanel graphPanel;

    public Gui() {
        setTitle("Algoritmos em Grafos");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        setLocationRelativeTo(null);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridBagLayout());
        inputPanel.setBorder(BorderFactory.createTitledBorder("Configurações"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(new JLabel("Caminho do Arquivo:"), gbc);
        caminhoArquivoField = new JTextField("src/Teste.txt");
        gbc.gridx = 1;
        inputPanel.add(caminhoArquivoField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(new JLabel("Estrutura de Dados:"), gbc);
        estruturaComboBox = new JComboBox<>(new String[]{"Matriz de Adjacência", "Matriz de Incidência", "Lista de Adjacência"});
        gbc.gridx = 1;
        inputPanel.add(estruturaComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        inputPanel.add(new JLabel("Algoritmo:"), gbc);
        algoritmoComboBox = new JComboBox<>(new String[]{"Busca em Profundidade (DFS)", "Busca em Largura (BFS)", "Árvore Geradora Mínima (AGM)", "Caminho Mínimo", "Fluxo Máximo"});
        gbc.gridx = 1;
        inputPanel.add(algoritmoComboBox, gbc);

        JButton executarButton = new JButton("Executar");
        executarButton.setBackground(new Color(34, 139, 34));
        executarButton.setForeground(Color.WHITE);
        executarButton.setFocusPainted(false);
        executarButton.setFont(new Font("Arial", Font.BOLD, 14));
        executarButton.addActionListener(new ExecutarButtonListener());
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        inputPanel.add(executarButton, gbc);

        add(inputPanel, BorderLayout.NORTH);

        resultadoArea = new JTextArea();
        resultadoArea.setEditable(false);
        resultadoArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        resultadoArea.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        JScrollPane scrollPane = new JScrollPane(resultadoArea);
        scrollPane.setPreferredSize(new Dimension(800, 150));
        add(scrollPane, BorderLayout.SOUTH);

        graphPanel = new GraphPanel();
        graphPanel.setBackground(Color.WHITE);
        graphPanel.setBorder(BorderFactory.createTitledBorder("Visualização do Grafo"));
        add(graphPanel, BorderLayout.CENTER);

        // Redireciona a saída padrão e a saída de erro para o JTextArea
        PrintStream printStream = new PrintStream(new CustomOutputStream(resultadoArea));
        System.setOut(printStream);
        System.setErr(printStream);
    }

    private class ExecutarButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String caminhoArquivo = caminhoArquivoField.getText();
            int estruturaIndex = estruturaComboBox.getSelectedIndex();
            int algoritmoIndex = algoritmoComboBox.getSelectedIndex();

            try {
                FileManager fm = new FileManager();
                ArrayList<String> linhas = fm.stringReader(caminhoArquivo);

                if (linhas == null) {
                    resultadoArea.setText("Erro ao ler o arquivo. Certifique-se de que o arquivo está no local correto.");
                    return;
                }

                switch (estruturaIndex) {
                    case 0:
                        grafo = new MatrizAdjacencia(5);
                        resultadoArea.setText("Estrutura escolhida: Matriz de Adjacência\n");
                        break;
                    case 1:
                        grafo = new MatrizIncidencia(5);
                        resultadoArea.setText("Estrutura escolhida: Matriz de Incidência\n");
                        break;
                    case 2:
                        grafo = new GrafoImplementacao();
                        resultadoArea.setText("Estrutura escolhida: Lista de Adjacência\n");
                        break;
                    default:
                        resultadoArea.setText("Opção de estrutura inválida.");
                        return;
                }

                for (String linha : linhas) {
                    if (linha.split(" ").length < 2) {
                        continue;
                    }

                    String[] partes = linha.split(" ");
                    int u = Integer.parseInt(partes[0]);
                    String[] arestas = partes[1].split(";");

                    for (String aresta : arestas) {
                        if (!aresta.isEmpty()) {
                            String[] dados = aresta.split("-");
                            if (dados.length < 2) {
                                continue;
                            }
                            int v = Integer.parseInt(dados[0]);
                            int peso = Integer.parseInt(dados[1]);
                            grafo.adicionarAresta(new Vertice(u), new Vertice(v), peso);
                        }
                    }
                }

                graphPanel.setGrafo(grafo);
                graphPanel.repaint();

                resultadoArea.append("Grafo carregado com sucesso:\n");
                resultadoArea.append("Número de vértices: " + grafo.numeroDeVertices() + "\n");
                resultadoArea.append("Número de arestas: " + grafo.numeroDeArestas() + "\n");

                switch (algoritmoIndex) {
                    case 0:
                        String verticeInicialDFS = JOptionPane.showInputDialog("Digite o vértice inicial para a busca em profundidade:");
                        grafo.buscaEmProfundidade(Integer.parseInt(verticeInicialDFS));
                        resultadoArea.append("Busca em profundidade executada a partir do vértice " + verticeInicialDFS + ".\n");
                        break;
                    case 1:
                        String verticeInicialBFS = JOptionPane.showInputDialog("Digite o vértice inicial para a busca em largura:");
                        grafo.buscaEmLargura(Integer.parseInt(verticeInicialBFS));
                        resultadoArea.append("Busca em largura executada a partir do vértice " + verticeInicialBFS + ".\n");
                        break;
                    case 2:
                        grafo.primMST();
                        resultadoArea.append("Árvore Geradora Mínima calculada usando o algoritmo de Prim.\n");
                        break;
                    case 3:
                        String verticeInicialDijkstra = JOptionPane.showInputDialog("Digite o vértice inicial:");
                        String verticeFinalDijkstra = JOptionPane.showInputDialog("Digite o vértice final:");
                        grafo.dijkstra(Integer.parseInt(verticeInicialDijkstra), Integer.parseInt(verticeFinalDijkstra));
                        resultadoArea.append("Caminho mínimo calculado de " + verticeInicialDijkstra + " para " + verticeFinalDijkstra + ".\n");
                        break;
                    case 4:
                        String source = JOptionPane.showInputDialog("Digite o vértice de origem:");
                        String sink = JOptionPane.showInputDialog("Digite o vértice de destino:");
                        grafo.edmondsKarp(Integer.parseInt(source), Integer.parseInt(sink));
                        resultadoArea.append("Fluxo máximo calculado do vértice " + source + " para o vértice " + sink + ".\n");
                        break;
                    default:
                        resultadoArea.append("Opção de algoritmo inválida.\n");
                }

            } catch (Exception ex) {
                ex.printStackTrace();
                resultadoArea.setText("Erro ao executar o algoritmo: " + ex.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Gui gui = new Gui();
            gui.setVisible(true);
        });
    }
}

class CustomOutputStream extends OutputStream {
    private JTextArea textArea;

    public CustomOutputStream(JTextArea textArea) {
        this.textArea = textArea;
    }

    @Override
    public void write(int b) throws IOException {
        textArea.append(String.valueOf((char) b));
        textArea.setCaretPosition(textArea.getDocument().getLength());
    }
}

class GraphPanel extends JPanel {
    private Grafo grafo;

    public void setGrafo(Grafo grafo) {
        this.grafo = grafo;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (grafo != null) {
            drawGraph(g);
        }
    }

    private void drawGraph(Graphics g) {
        int radius = 20;
        int padding = 50;
        int numVertices = grafo.numeroDeVertices();
        int width = getWidth();
        int height = getHeight();
        int centerX = width / 2;
        int centerY = height / 2;
        int angleStep = 360 / numVertices;

        for (int i = 0; i < numVertices; i++) {
            int x = (int) (centerX + (centerX - padding) * Math.cos(Math.toRadians(i * angleStep)));
            int y = (int) (centerY + (centerY - padding) * Math.sin(Math.toRadians(i * angleStep)));
            g.fillOval(x - radius / 2, y - radius / 2, radius, radius);
            g.drawString(String.valueOf(i), x - radius / 2, y - radius / 2);

            try {
                for (Vertice adj : grafo.adjacentesDe(new Vertice(i))) {
                    int adjIndex = adj.getId();
                    int adjX = (int) (centerX + (centerX - padding) * Math.cos(Math.toRadians(adjIndex * angleStep)));
                    int adjY = (int) (centerY + (centerY - padding) * Math.sin(Math.toRadians(adjIndex * angleStep)));
                    g.drawLine(x, y, adjX, adjY);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
