package game;

import java.util.Queue;
import java.util.Random;

public class PieceFactory {

    static final int[] lastTwoInts = new int[2];
    static final Random random = new Random();

    // Define the Tetris pieces as 2D integer arrays
    static TetrisPiece IPiece(){ return new TetrisPiece(new int[][]{
            {0, 0, 0, 0},
            {1, 1, 1, 1},
            {0, 0, 0, 0},
            {0, 0, 0, 0}
    }); // I
    }

    static TetrisPiece ZPiece(){ return new TetrisPiece(new int[][]{
            {0, 0, 0, 0},
            {2, 2, 0, 0},
            {0, 2, 2, 0},
            {0, 0, 0, 0}
    }); // Z
    }

    static TetrisPiece SPiece() {
        return new TetrisPiece(new int[][]{
                {0, 0, 0, 0},
                {0, 3, 3, 0},
                {3, 3, 0, 0},
                {0, 0, 0, 0}
        }); // S
    }

    static TetrisPiece LPiece(){ return new TetrisPiece(new int[][]{
            {0, 0, 0, 0},
            {4, 0, 0, 0},
            {4, 4, 4, 0},
            {0, 0, 0, 0}
    }); // L
    }

    static TetrisPiece TPiece(){ return new TetrisPiece(new int[][]{
            {0, 0, 0, 0},
            {5, 5, 5, 0},
            {0, 5, 0, 0},
            {0, 0, 0, 0}
    }); // T
    }

    private static TetrisPiece JPiece(){ return new TetrisPiece(new int[][]{
            {0, 0, 0, 0},
            {0, 0, 6, 0},
            {6, 6, 6, 0},
            {0, 0, 0, 0}
    }); // J
    }

    private static TetrisPiece OPiece(){ return new TetrisPiece(new int[][]{
            {0, 0, 0, 0},
            {0, 7, 7, 0},
            {0, 7, 7, 0},
            {0, 0, 0, 0}
    });
    } // O

    public static TetrisPiece newPiece() {


        Integer randomNumber = random.nextInt(7);
        while(randomNumber == lastTwoInts[0]||randomNumber == lastTwoInts[1]) {
            randomNumber = random.nextInt(7);
        }
        lastTwoInts[1] = lastTwoInts[0];
        lastTwoInts[0] = randomNumber;

        TetrisPiece piece = switch (randomNumber) {
            case 0 -> IPiece();
            case 1 -> ZPiece();
            case 2 -> SPiece();
            case 3 -> LPiece();
            case 4 -> TPiece();
            case 5 -> JPiece();
            case 6 -> OPiece();
            default -> IPiece(); // Added default to avoid potential errors
        };
        System.out.println("New piece created: X=" + piece.getX() + ", Y=" + piece.getY() + "random value was: " + randomNumber);
        return piece;
    }
}