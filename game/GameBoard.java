package game;


import render.Renderer;

public class GameBoard {
    private static final int WIDTH = 10;
    private static final int HEIGHT = 23;
    private static final int[][] grid = new int[WIDTH][HEIGHT];
    Renderer renderer;

    public void setRenderer(Renderer renderer) {
        this.renderer = renderer;
    }

    public boolean collided(TetrisPiece piece) {
        int[][] shape = piece.getShape();
        int x = piece.getX();
        int y = piece.getY();

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (shape[i][j] != 0) { // Only check if the piece has a pixel here
                    if (y+i+1 >= HEIGHT || grid[x+j][y+i+1] != 0) {
                        return true; // Collision
                    }
                }
            }
        }
        return false; // No collision
    }

    public void placePiece(TetrisPiece piece) {// Agrega la pieza a la cuadrícula y revisa líneas completas
        int[][] shape = piece.getShape();
        int x = piece.getX();
        int y = piece.getY();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (grid[i+x][j+y] == 0) {
                    grid[i+x][j+y] = shape [i][j];
                }
            }
        }
        piece.setPlaced(true);
        checkLines();
    }

    public void checkLines() {
        for (int y = HEIGHT - 1; y >= 0; y--) { // Start from bottom row
            if (isLineFull(y)) {
                clearLine(y);
                renderer.flashLine(y);
                shiftDown(y);
                y++; // Recheck the same row after shifting down
            }
        }
    }

    private boolean isLineFull(int y) {
        for (int x = 0; x < WIDTH; x++) {
            if (grid[x][y] == 0) { // If any cell is empty, line is not full
                return false;
            }
        }
        return true;
    }

    public boolean lineHasBlock(int y) {
        for (int x = 0; x < WIDTH; x++) {
            if (grid[x][y] == 0) {
                return true;
            }
        }
        return false;
    }

    private void clearLine(int y) {
        for (int x = 0; x < WIDTH; x++) {
            grid[x][y] = 0; // Clear the row
        }
    }

    private void shiftDown(int fromY) {
        for (int y = fromY; y > 0; y--) { // For y to all rows above excluding y = 0 (top row)
            for (int x = 0; x < WIDTH; x++) {
                grid[x][y] = grid[x][y - 1]; // Copy the row above
            }
        }
        // Clear the top row since there's no row above it
        for (int x = 0; x < WIDTH; x++) {
            grid[x][0] = 0;
        }
    }

}
