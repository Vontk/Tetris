package game;

public class Tetrinimo {
    private int[][] shape;
    private int x, y;

    public Tetrinimo(int[][] shape) {
        this.shape = shape;
        this.x = 5; // Posición inicial en la mitad del tablero
        this.y = 0;
    }

    public int[][] getShape() {
        return shape;
    }

    public void moveLeft(){
        x--;
    }
    public void moveRight(){
        x++;
    }

    public void fall(){
        y--;
    }

    public void rotate() {

        if (shape == null || shape.length != 4 || shape[0].length != 4) {
            throw new IllegalArgumentException("Matrix must be a 4x4 array.");
        }

        int[][] rotatedMatrix = new int[4][4];

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                rotatedMatrix[3 - j][i] = shape[i][j];
            }
        }
        shape = rotatedMatrix;
    }
}
