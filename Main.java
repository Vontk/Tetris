import java.util.Arrays;

public class Main {  // Fixed class name

    public static void main(String[] args) {
        int[][] FourByFour = {
                {0, 1, 2, 3},
                {4, 5, 6, 7},
                {8, 9, 10, 11},
                {12, 13, 14, 15}
        };

        rotate(FourByFour);
        System.out.println(Arrays.deepToString(FourByFour));  // Proper way to print a 2D array
    }

    public static void rotate(int[][] grid) {  // Moved outside main
        if (grid == null || grid.length != 4 || grid[0].length != 4) {
            throw new IllegalArgumentException("Matrix must be a 4x4 array.");
        }

        int[][] rotatedMatrix = new int[4][4];

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                rotatedMatrix[j][3 - i] = grid[i][j];  // Correct rotation logic
            }
        }

        // Copy rotatedMatrix back to grid
        for (int i = 0; i < 4; i++) {
            System.arraycopy(rotatedMatrix[i], 0, grid[i], 0, 4);
        }
    }
}
