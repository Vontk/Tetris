package game;

import java.util.Random;

public class TetrisPiece {
    private final static Random random = new Random();
    private int[][] shape;
    private int x, y;
    private boolean placed;

    public TetrisPiece(int[][] shape) {
        this.shape = shape;
        this.x = random.nextInt(7);
        this.y = 0;
    }

    public TetrisPiece(TetrisPiece tetrisPiece) {
        this.shape = tetrisPiece.shape;
        this.x = tetrisPiece.x;
        this.y = tetrisPiece.y;
        this.placed = tetrisPiece.placed;
    }

    public int[][] getShape() {return shape;}
    public int getX() {return x;}
    public int getY() {return y;}
    public void moveLeft(){x--;}
    public void moveRight(){x++;}
    public void fall(){y++;}
    public boolean isPlaced() {return placed;}
    public void setPlaced(boolean placed) {this.placed = placed;}

    public void rotate() {
        int[][] rotatedMatrix = new int[4][4];

        // Correct 90-degree clockwise rotation
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                rotatedMatrix[j][3 - i] = shape[i][j];
            }
        }

        shape = rotatedMatrix;
    }
    /*
    {0, 1, 2, 3},         [12, 8, 4, 0]
    {4, 5, 6, 7},   --->  [13, 9, 5, 1]
    {8, 9, 10, 11},       [14, 10, 6, 2]
    {12, 13, 14, 15}      [15, 11, 7, 3]
     */
}