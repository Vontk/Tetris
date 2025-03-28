package game;


import render.Renderer;

public class GameBoard {
    private final int WIDTH = 10;
    private final int HEIGHT = 23;
    public final int[][] grid = new int[WIDTH][HEIGHT];
    Renderer renderer;
    boolean rendererSet = false;
    private int eliminatedLines = 0;
    public int score = 0;
    public int level = 0;

    public void setRenderer(Renderer renderer) {
        this.renderer = renderer;
        rendererSet = true;
    }
    /*
    public boolean canMoveDown(TetrisPiece piece) {

        int[][] shape = piece.getShape();
        int x = piece.getX();
        int y = piece.getY();
        System.out.println("About to check if it can move down, piece at x=" + piece.getX() + " y=" + piece.getY());

        for (int i = 0; i < 4; i++) {
            System.out.println("Checking row " + i);
            for (int j = 0; j < 4; j++) {
                System.out.println("Checking column " + j);
                if (shape[i][j] == 0) continue;
                // Check for bottom boundary and grid collision
                if (y + j + 1 >= HEIGHT || grid[x + i][y + j + 1] != 0) {
                    System.out.println("Collision detected at " + (x + i) + ", " + (y + j + 1) + "because " + grid[x + i][y + j + 1] + "!= 0 ||" + (y + j + 1) + ">= HEIGHT");
                    return false;
                }
            }
        }
        System.out.println("No collision detected");
        return true;
     }
     */
    public boolean canMoveDown(TetrisPiece piece) {
        System.out.println("Initializing moveDownCheck for piece at x=" + piece.getX() + " y=" + piece.getY());
        int[][] shape = piece.getShape();
        int x = piece.getX();
        int y = piece.getY();

        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[i].length; j++) {
                try {
                    System.out.println("Checking row " + i + " column " + j);
                    if (shape[i][j] != 0) {
                        // Adjust these coordinates based on how your grid is structured
                        int gridX = x + i;
                        int gridY = y + j + 1;

                        // Check boundary first
                        if (gridY >= HEIGHT) {
                            System.out.println("Bottom boundary hit at " + gridX + "," + gridY);
                            return false;
                        }

                        // Check other pieces
                        if (gridX >= 0 && gridX < WIDTH && gridY >= 0 && grid[gridX][gridY] != 0) {
                            System.out.println("Piece collision at " + gridX + "," + gridY);
                            return false;
                        }
                    }
                } catch (Exception e) {
                    System.err.println("Error at i=" + i + ", j=" + j + ": " + e.getMessage());
                    e.printStackTrace();
                    return false;
                }
            }
        }
        System.out.println("Can move down: true");
        return true;
    }

    public void placePiece(TetrisPiece piece) {
        int[][] shape = piece.getShape();
        int x = piece.getX();
        int y = piece.getY();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (shape[i][j] != 0) {
                    int gridX = x + i;
                    int gridY = y + j;
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
