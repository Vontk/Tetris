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
    private static final int TILE_SIZE = 40; // Tamaño de la imagen (20x20)
    private static final int GRID_WIDTH = 10;
    private static final int GRID_HEIGHT = 20;
    private static final int SCORE_PANEL_HEIGHT = 40; // Espacio para el score en la parte superior
    private GameBoard gameBoard;
    private Map<Integer, Image> imageMap;
    private int FONT_SIZE = 24;
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

        // Ajustar el tamaño del panel (200x400 + 40 de espacio extra arriba)
        setPreferredSize(new Dimension(GRID_WIDTH * TILE_SIZE, GRID_HEIGHT * TILE_SIZE + SCORE_PANEL_HEIGHT));
    }

    // Cargar imágenes de los bloques
    private void loadImages() {
        imageMap.put(0, new ImageIcon("bgBlock.png").getImage());
        imageMap.put(1, new ImageIcon("blueBlock.png").getImage());
        imageMap.put(2, new ImageIcon("cyanBlock.png").getImage());
        imageMap.put(3, new ImageIcon("greenBlock.png").getImage());
        imageMap.put(4, new ImageIcon("orangeBlock.png").getImage());
        imageMap.put(5, new ImageIcon("purpleBlock.png").getImage());
        imageMap.put(6, new ImageIcon("redBlock.png").getImage());
        imageMap.put(7, new ImageIcon("yellowBlock.png").getImage());
        try {tetrisFont = Font.createFont(Font.TRUETYPE_FONT, new File("tetris.ttf")).deriveFont(Font.PLAIN, 24);}
        catch (FontFormatException e) {} catch (IOException e) {} // we don't give a fuck if it fails (it won't)
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Dibujar el área de score en la parte superior
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), SCORE_PANEL_HEIGHT); // Fondo negro
        g.setFont(tetrisFont);
        g.setColor(Color.WHITE);
        g.drawString("Score: "+ gameBoard.score, 140, 28); // Puntaje
        g.drawString("Level: "+ gameBoard.level,10 , 28); // Nivel

        // Dibujar las celdas del grid, **desplazado hacia abajo por SCORE_PANEL_HEIGHT**
        TetrisPiece piece = tetrisGame.activePiece;
        int pieceX = piece.getX();
        int pieceY = piece.getY();
        for (int i = 0; i < 4; i++){
            for (int j = 0; j < 4; j++){
                int value = piece.getShape()[i][j];
                Image image = imageMap.get(value);
                if (image != null){
                    g.drawImage(image,(pieceX + i) * TILE_SIZE,(pieceY + j) * TILE_SIZE + SCORE_PANEL_HEIGHT,this);
                }
            }
        }
        for (int y = 0; y < GRID_HEIGHT; y++) {
            for (int x = 0; x < GRID_WIDTH; x++) {
                int value = gameBoard.grid[x][y]; // Obtener el valor de la celda
                Image img = imageMap.get(value); // Obtener la imagen correspondiente
                if (img != null) {
                    // Dibujar la imagen desplazada para dejar espacio en la parte superior
                    g.drawImage(img, x * TILE_SIZE, y * TILE_SIZE + SCORE_PANEL_HEIGHT, TILE_SIZE, TILE_SIZE, this);
                }
            }
        }
    }

    public void renderAddedScore(int score) {
        repaint();
    }

    public void flashLine(int y) {
        // Implementación para hacer parpadear una línea eliminada
    }
}
