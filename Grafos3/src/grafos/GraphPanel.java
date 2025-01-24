package grafos;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * @author Alessandro Augusto
 * @since 09/01/2025
 */

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
                    int adjIndex = adj.id();
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
