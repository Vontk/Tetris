package game;

public class TetrisPiece {
    private int[][] shape;
    private int x, y;

    public TetrisPiece(int[][] shape) {
        this.shape = shape;
        this.x = 3;
        this.y = 0;
    }

    public int[][] getShape() {
        return shape;
    }
    public int getX() {return x;}
    public int getY() {return y;}
    public void moveLeft(){
        x--;
    }
    public void moveRight(){
        x++;
    }
    public void fall(){
        y++;
    }

    public void rotate() {
        if (shape == null || shape.length != 4 || shape[0].length != 4) {
            throw new IllegalArgumentException("Matrix must be a 4x4 array.");
        }

        int[][] rotatedMatrix = new int[4][4];

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                rotatedMatrix[j][3 - i] = shape[i][j];
            }
        }

        shape = rotatedMatrix; // Update the internal shape array
    }
    /*
    {0, 1, 2, 3},         [12, 8, 4, 0]
    {4, 5, 6, 7},   --->  [13, 9, 5, 1]
    {8, 9, 10, 11},       [14, 10, 6, 2]
    {12, 13, 14, 15}      [15, 11, 7, 3]
     */
}