package game;

public class GameBoard {
    private static final int WIDTH = 10;
    private static final int HEIGHT = 23;
    private int[][] grid = new int[HEIGHT][WIDTH];

    public boolean canPlace(Tetrinimo piece, int x, int y) {
        // Verifica si la pieza cabe en la posición
    }

    public void placePiece(Tetrinimo piece) {
        // Agrega la pieza a la cuadrícula y revisa líneas completas
    }

    public void clearLines() {
        // Borra líneas completas y ajusta el puntaje
    }
}
