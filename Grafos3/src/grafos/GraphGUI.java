package grafos;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Alessandro Augusto
 * @since 09/01/2025
 */

public class GraphGUI extends JFrame {
    private JPanel mainPanel;
    private JButton loadGraphButton;
    private JButton bfsButton;
    private JButton dfsButton;
    private JButton mstButton;
    private JButton shortestPathButton;
    private JButton maxFlowButton;
    private JTextField startVertexField;
    private JTextField endVertexField;
    private JTextArea outputArea;
    private JComboBox<String> representationComboBox;
    private JFileChooser fileChooser;
    private GraphPanel graphPanel;

    private Grafo grafo;
    private Algoritmos alg;

    public GraphGUI() {
        setTitle("Algoritmos de Grafos");
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.BLACK);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(0, 1, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        buttonPanel.setBackground(Color.DARK_GRAY);

        loadGraphButton = createStyledButton("Carregar Grafo");
        bfsButton = createStyledButton("Busca em Largura (BFS)");
        dfsButton = createStyledButton("Busca em Profundidade (DFS)");
        mstButton = createStyledButton("Árvore Geradora Mínima (AGM)");
        shortestPathButton = createStyledButton("Caminho Mínimo");
        maxFlowButton = createStyledButton("Fluxo Máximo");

        startVertexField = createStyledTextField();
        endVertexField = createStyledTextField();

        outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setFont(new Font("Courier New", Font.PLAIN, 14));
        outputArea.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        outputArea.setBackground(Color.BLACK);
        outputArea.setForeground(Color.WHITE);

        String[] representationOptions = {"Matriz de Adjacência", "Matriz de Incidência", "Lista de Adjacência"};
        representationComboBox = new JComboBox<>(representationOptions);
        representationComboBox.setBackground(Color.DARK_GRAY);
        representationComboBox.setForeground(Color.WHITE);

        fileChooser = new JFileChooser();

        JLabel representationLabel = new JLabel("Representação:");
        representationLabel.setForeground(Color.WHITE);
        buttonPanel.add(representationLabel);
        buttonPanel.add(representationComboBox);

        buttonPanel.add(loadGraphButton);
        buttonPanel.add(bfsButton);
        buttonPanel.add(dfsButton);
        buttonPanel.add(mstButton);
        buttonPanel.add(shortestPathButton);
        buttonPanel.add(maxFlowButton);

        JLabel startVertexLabel = new JLabel("Vértice Inicial:");
        startVertexLabel.setForeground(Color.WHITE);
        buttonPanel.add(startVertexLabel);
        buttonPanel.add(startVertexField);

        JLabel endVertexLabel = new JLabel("Vértice Final:");
        endVertexLabel.setForeground(Color.WHITE);
        buttonPanel.add(endVertexLabel);
        buttonPanel.add(endVertexField);

        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        splitPane.setResizeWeight(0.7);
        splitPane.setTopComponent(new JScrollPane(outputArea));
        splitPane.setBottomComponent(buttonPanel);

        mainPanel.add(splitPane, BorderLayout.EAST);

        graphPanel = new GraphPanel();
        graphPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        mainPanel.add(graphPanel, BorderLayout.CENTER);

        add(mainPanel);

        alg = new Algoritmos(outputArea);

        loadGraphButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int returnVal = fileChooser.showOpenDialog(GraphGUI.this);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    String filePath = fileChooser.getSelectedFile().getPath();
                    int selectedIndex = representationComboBox.getSelectedIndex();
                    TipoDeRepresentacao tipo = TipoDeRepresentacao.fromTipoRe(selectedIndex + 1);
                    try {
                        grafo = alg.carregarGrafo(filePath, tipo);
                        outputArea.append("Grafo carregado com sucesso!\n");
                        graphPanel.setGrafo(grafo);
                        graphPanel.repaint();
                    } catch (Exception ex) {
                        outputArea.append("Erro ao carregar o grafo: " + ex.getMessage() + "\n");
                    }
                }
            }
        });

        bfsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    outputArea.append("Busca em Largura:\n");
                    alg.buscaEmLargura(grafo);
                } catch (Exception ex) {
                    outputArea.append("Erro na Busca em Largura: " + ex.getMessage() + "\n");
                }
            }
        });

        dfsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    outputArea.append("Busca em Profundidade:\n");
                    alg.buscaEmProfundidade(grafo);
                } catch (Exception ex) {
                    outputArea.append("Erro na Busca em Profundidade: " + ex.getMessage() + "\n");
                }
            }
        });

        mstButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    outputArea.append("Árvore Geradora Mínima:\n");
                    alg.agmUsandoKruskall(grafo);
                } catch (Exception ex) {
                    outputArea.append("Erro na Árvore Geradora Mínima: " + ex.getMessage() + "\n");
                }
            }
        });

        shortestPathButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String startVertexText = startVertexField.getText().trim();
                    String endVertexText = endVertexField.getText().trim();

                    if (startVertexText.isEmpty() || endVertexText.isEmpty()) {
                        outputArea.append("Erro: Os campos de vértice inicial e final não podem estar vazios.\n");
                        return;
                    }

                    int startVertex = Integer.parseInt(startVertexText);
                    int endVertex = Integer.parseInt(endVertexText);
                    Vertice origem = new Vertice(startVertex);
                    Vertice destino = new Vertice(endVertex);
                    outputArea.append("Caminho Mínimo:\n");
                    alg.caminhoMaisCurto(grafo, origem, destino);
                } catch (NumberFormatException ex) {
                    outputArea.append("Erro: Os campos de vértice inicial e final devem ser inteiros.\n");
                } catch (Exception ex) {
                    outputArea.append("Erro no Caminho Mínimo: " + ex.getMessage() + "\n");
                }
            }
        });

        maxFlowButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String startVertexText = startVertexField.getText().trim();
                    String endVertexText = endVertexField.getText().trim();

                    if (startVertexText.isEmpty() || endVertexText.isEmpty()) {
                        outputArea.append("Erro: Os campos de vértice inicial e final não podem estar vazios.\n");
                        return;
                    }

                    int startVertex = Integer.parseInt(startVertexText);
                    int endVertex = Integer.parseInt(endVertexText);
                    Vertice origem = new Vertice(startVertex);
                    Vertice destino = new Vertice(endVertex);
                    outputArea.append("Fluxo Máximo:\n");
                    alg.FluxoMaximo(grafo, origem, destino);
                } catch (NumberFormatException ex) {
                    outputArea.append("Erro: Os campos de vértice inicial e final devem ser inteiros.\n");
                } catch (Exception ex) {
                    outputArea.append("Erro no Fluxo Máximo: " + ex.getMessage() + "\n");
                }
            }
        });
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(new Color(100, 149, 237));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        return button;
    }

    private JTextField createStyledTextField() {
        JTextField textField = new JTextField(10);
        textField.setBackground(Color.DARK_GRAY);
        textField.setForeground(Color.WHITE);
        textField.setCaretColor(Color.WHITE);
        return textField;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GraphGUI().setVisible(true);
            }
        });
    }
}
