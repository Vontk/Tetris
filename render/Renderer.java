package render;

import game.GameBoard;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class Renderer extends JPanel {
    private static final int TILE_SIZE = 20; // Tamaño de la imagen (16x16)
    private static final int GRID_WIDTH = 10;
    private static final int GRID_HEIGHT = 20;
    private static final int SCORE_PANEL_HEIGHT = 40;
    private GameBoard gameBoard;  // Aquí puedes obtener la matriz de enteros
    private Map<Integer, Image> imageMap;  // Mapa de imágenes según valores de la matriz

    // Constructor
    public Renderer(GameBoard board) {
        this.gameBoard = board;
        this.imageMap = new HashMap<>();
        loadImages(); // Cargar imágenes correspondientes a cada valor
        setPreferredSize(new Dimension(GRID_WIDTH * TILE_SIZE, GRID_HEIGHT * TILE_SIZE + SCORE_PANEL_HEIGHT));
    }

    private void loadImages() {
        imageMap.put(1, new ImageIcon("blueBlock.png").getImage());
        imageMap.put(2, new ImageIcon("cyanBlock.png").getImage());
        imageMap.put(3, new ImageIcon("greenBlock.png").getImage());
        imageMap.put(4, new ImageIcon("orangeBlock.png").getImage());
        imageMap.put(5, new ImageIcon("purpleBlock.png").getImage());
        imageMap.put(6, new ImageIcon("redBlock.png").getImage());
        imageMap.put(7, new ImageIcon("yellowBlock.png").getImage());
    }

    // Método para renderizar el grid
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Dibujar las celdas del grid
        for (int y = 0; y < GRID_HEIGHT; y++) {
            for (int x = 0; x < GRID_WIDTH; x++) {
                int value = gameBoard.grid[x][y]; // Obtener el valor de la celda de la matriz
                Image img = imageMap.get(value); // Obtener la imagen correspondiente
                if (img != null) {
                    g.drawImage(img, x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE, this); // Dibujar la imagen
                }
            }
        }
        /*
        g.drawImage(imageMap.get(1), 1 * TILE_SIZE, 1 * TILE_SIZE, TILE_SIZE, TILE_SIZE, this);
         */
        // Dibujar el panel para puntaje y nivel
        g.setColor(Color.BLACK);
        g.fillRect(0, GRID_HEIGHT * TILE_SIZE, getWidth(), SCORE_PANEL_HEIGHT); // Fondo para puntaje
        g.setColor(Color.WHITE);
        g.drawString("Score: 1234", 10, GRID_HEIGHT * TILE_SIZE + 20); // Dibuja el score
        g.drawString("Level: 3", 10, GRID_HEIGHT * TILE_SIZE + 40); // Dibuja el nivel
    }

    public void renderAddedScore(int score) {
        // Aquí podrías actualizar el puntaje en la pantalla
        repaint(); // Para redibujar el panel con la nueva información
    }

    public void flashLine(int y) {
        // Lógica para hacer parpadear una línea (si se elimina una línea)
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Tetris");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GameBoard board = new GameBoard();  // Inicializa tu GameBoard aquí
        Renderer renderer = new Renderer(board);
        frame.add(renderer);
        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);
    }
}
