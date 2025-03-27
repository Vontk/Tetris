package game;


import render.Renderer;

public class GameBoard {
    private static final int WIDTH = 10;
    private static final int HEIGHT = 23;
    public static final int[][] grid = new int[WIDTH][HEIGHT];
    Renderer renderer;
    boolean rendererSet = false;
    private int eliminatedLines = 0;
    public int score = 0;
    public int level = 0;

    public void setRenderer(Renderer renderer) {
        this.renderer = renderer;
        rendererSet = true;
    }
    public boolean canMoveDown(TetrisPiece piece) {
        int[][] shape = piece.getShape();
        int x = piece.getX();
        int y = piece.getY();

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (shape[i][j] != 0) {
                    // Check for bottom boundary and grid collision
                    if (y + j + 1 >= HEIGHT || grid[x + i][y + j + 1] != 0) {
                        System.out.println("Collision detected at " + (x + i) + ", " + (y + j + 1) + "because " + grid[x + i][y + j + 1] + "!= 0 ||" + (y + j + 1) + ">= HEIGHT");;
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public void placePiece(TetrisPiece piece) {
        int[][] shape = piece.getShape();
        int x = piece.getX();
        int y = piece.getY();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (shape[i][j] != 0) {
                    int gridX = i + x;
                    int gridY = j + y;
                    if (gridX >= 0 && gridX < WIDTH && gridY >= 0 && gridY < HEIGHT) {
                        grid[gridX][gridY] = shape[i][j];
                        System.out.println("Placed block at X: " + gridX + ", Y: " + gridY);
                    }
                }
            }
        }
    piece.setPlaced(true);
    checkLines();
    }

    public void checkLines() {
        int linesClearedInOneMove = 0; // Contador de líneas eliminadas en una sola iteración

        for (int y = HEIGHT - 1; y >= 0; y--) { // Start from bottom row
            if (isLineFull(y)) {
                clearLine(y);
                renderer.flashLine(y);
                shiftDown(y);
                eliminatedLines++;
                linesClearedInOneMove++; // Contamos la línea eliminada
                y++; // Recheck the same row after shifting down
            }
        }

        // Si se eliminaron líneas en este movimiento, actualizamos la puntuación
        if (linesClearedInOneMove > 0) {
            updateScore(linesClearedInOneMove);
            updateLevel();
        }
    }

    private void updateScore(int linesCleared) {
        int baseScore = switch (linesCleared) {
            case 1 -> 40;
            case 2 -> 100;
            case 3 -> 300;
            case 4 -> 1200;
            default -> 0;
        };

        score += baseScore * (level + 1); // Puntos escalados según el nivel
        renderer.renderAddedScore(baseScore *(level +1));
    }

    private void updateLevel() {
        level = eliminatedLines/10;
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
            if (grid[x][y] != 0) {
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
