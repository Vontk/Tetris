package render;

import game.GameBoard;
import game.TetrisGame;
import game.TetrisPiece;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Renderer extends JPanel {
    private static final int TILE_SIZE = 35; // Tamaño de la imagen (20x20)
    private static final int GRID_WIDTH = 10;
    private static final int GRID_HEIGHT = 23;
    private static final int GRID_RENDER_HEIGHT = 20;
    private static final int SCORE_PANEL_HEIGHT = 40; // Espacio para el score en la parte superior
    private final GameBoard gameBoard;
    private final Map<Integer, Image> imageMap;
    private Font tetrisFont;
    private TetrisGame tetrisGame;

    public void setTetrisGame(TetrisGame game) {
        tetrisGame = game;
    }

    // Constructor
    public Renderer(GameBoard board) {
        this.gameBoard = board;
        this.imageMap = new HashMap<>();
        loadImages();
        setFocusable(true);
        requestFocusInWindow();
        // Ajustar el tamaño del panel (200x400 + 40 de espacio extra arriba)
        setPreferredSize(new Dimension(GRID_WIDTH * TILE_SIZE, GRID_RENDER_HEIGHT * TILE_SIZE + SCORE_PANEL_HEIGHT));
    }

    // Cargar imágenes de los bloques
    private void loadImages() {
        imageMap.put(0, new ImageIcon("resources/bgBlock.png").getImage());
        imageMap.put(1, new ImageIcon("resources/cyanBlock.png").getImage());
        imageMap.put(2, new ImageIcon("resources/redBlock.png").getImage());
        imageMap.put(3, new ImageIcon("resources/greenBlock.png").getImage());
        imageMap.put(4, new ImageIcon("resources/orangeBlock.png").getImage());
        imageMap.put(5, new ImageIcon("resources/purpleBlock.png").getImage());
        imageMap.put(6, new ImageIcon("resources/blueBlock.png").getImage());
        imageMap.put(7, new ImageIcon("resources/yellowBlock.png").getImage());
        try {tetrisFont = Font.createFont(Font.TRUETYPE_FONT, new File("resources/tetris.ttf")).deriveFont(Font.PLAIN, 24);}
        catch (FontFormatException | IOException e) {e.printStackTrace();} // we don't give a fuck if it fails (it won't)
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Dibujar las celdas del grid, **desplazado hacia abajo por SCORE_PANEL_HEIGHT**
        for (int y = 0; y < GRID_HEIGHT; y++) {
            for (int x = 0; x < GRID_WIDTH; x++) {
                int value = gameBoard.grid[x][y]; // Obtener el valor de la celda
                Image img = imageMap.get(value); // Obtener la imagen correspondiente
                if (img != null) {
                    // Dibujar la imagen desplazada para dejar espacio en la parte superior
                    g.drawImage(img, x * TILE_SIZE, y * TILE_SIZE + SCORE_PANEL_HEIGHT -3*TILE_SIZE, TILE_SIZE, TILE_SIZE, this);
                }
            }
        }

        // Dibujar la pieza activa
        TetrisPiece piece = tetrisGame.activePiece;
        int pieceX = piece.getX();
        int pieceY = piece.getY();
        for (int i = 0; i < 4; i++){
            for (int j = 0; j < 4; j++){
                int value = piece.getShape()[i][j];
                Image image = imageMap.get(value);
                if (image != null && value != 0){
                    g.drawImage(image,(pieceX + i) * TILE_SIZE,(pieceY + j) * TILE_SIZE + SCORE_PANEL_HEIGHT -3*TILE_SIZE, TILE_SIZE, TILE_SIZE, this);
                }
            }
        }
        // Dibujar el área de score en la parte superior
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), SCORE_PANEL_HEIGHT); // Fondo negro
        g.setFont(tetrisFont);
        g.setColor(Color.WHITE);
        g.drawString("Score: "+ gameBoard.score, 140, 28); // Puntaje
        g.drawString("Level: "+ gameBoard.level,10 , 28); // Nivel
    }

    public void renderAddedScore(int score) {
        repaint();
    }

    public void flashLine(int y) {
        // Implementación para hacer parpadear una línea eliminada
    }
}
