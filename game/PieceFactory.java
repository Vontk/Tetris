package game;

import java.util.Random;

public class PieceFactory {

    // Define the Tetris pieces as 2D integer arrays
    static TetrisPiece onePiece(){ return new TetrisPiece(new int[][]{
            {1, 1, 1, 1},
            {0, 0, 0, 0},
            {0, 0, 0, 0},
            {0, 0, 0, 0}
    }); // I
    }

    static TetrisPiece twoPiece(){ return new TetrisPiece(new int[][]{
            {2, 2, 0, 0},
            {0, 2, 2, 0},
            {0, 0, 0, 0},
            {0, 0, 0, 0}
    }); // Z
    }

    static TetrisPiece threePiece() {
        return new TetrisPiece(new int[][]{
                {0, 3, 3, 0},
                {3, 3, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0}
        }); // S
    }

    static TetrisPiece fourPiece(){ return new TetrisPiece(new int[][]{
            {4, 0, 0, 0},
            {4, 4, 4, 0},
            {0, 0, 0, 0},
            {0, 0, 0, 0}
    }); // L
    }

    static TetrisPiece fivePiece(){ return new TetrisPiece(new int[][]{
            {5, 5, 5, 0},
            {0, 5, 0, 0},
            {0, 0, 0, 0},
            {0, 0, 0, 0}
    }); // T
    }

    private static TetrisPiece sixPiece(){ return new TetrisPiece(new int[][]{
            {0, 0, 6, 0},
            {6, 6, 6, 0},
            {0, 0, 0, 0},
            {0, 0, 0, 0}
    }); // J
    }

    private static TetrisPiece sevenPiece(){ return new TetrisPiece(new int[][]{
            {7, 7, 0, 0},
            {7, 7, 0, 0},
            {0, 0, 0, 0},
            {0, 0, 0, 0}
    });
    } // O

    public static TetrisPiece newPiece() {

        Random random = new Random();
        int randomNumber = random.nextInt(7);
        TetrisPiece piece = switch (randomNumber) {
            case 0 -> onePiece();
            case 1 -> twoPiece();
            case 2 -> threePiece();
            case 3 -> fourPiece();
            case 4 -> fivePiece();
            case 5 -> sixPiece();
            case 6 -> sevenPiece();
            default -> onePiece(); // Added default to avoid potential errors
        };
        System.out.println("New piece created: X=" + piece.getX() + ", Y=" + piece.getY() + "random value was: " + randomNumber);
        return piece;
    }
}