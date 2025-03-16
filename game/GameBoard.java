package game;


public class GameBoard {
    private static final int WIDTH = 10;
    private static final int HEIGHT = 23;
    private int[][] grid = new int[WIDTH][HEIGHT];

    public boolean canFall(TetrisPiece piece, int[][] grid) {
        int[][] shape = piece.getShape();
        int x = piece.getX();
        int y = piece.getY();

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (shape[i][j] != 0) { // Only check if the piece has a pixel here
                    if (y + i + 1 >= HEIGHT || grid[x + j][y + i + 1] != 0) {
                        return false; // Collision detected
                    }
                }
            }
        }
        return true; // No collision
    }

    public void placePiece(TetrisPiece piece) {
        // Agrega la pieza a la cuadrícula y revisa líneas completas
    }

    public void clearLines() {
        // Borra líneas completas y ajusta el puntaje
    }
}
