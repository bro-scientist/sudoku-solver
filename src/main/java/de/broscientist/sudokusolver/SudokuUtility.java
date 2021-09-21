package de.broscientist.sudokusolver;

public class SudokuUtility
{
    public static final int GRID_ROOT = 3;
    public static final int GRID_SIZE = GRID_ROOT*GRID_ROOT;

    public static boolean rowContains(int[][] grid, int row, int number) {
        for (int i = 0; i < GRID_SIZE; i++) {
            if (grid[row][i] == number) return true;
        }
        return false;
    }

    public static boolean colContains(int[][] grid, int col, int number) {
        for (int i = 0; i < GRID_SIZE; i++) {
            if (grid[i][col] == number) return true;
        }
        return false;
    }

    public static boolean boxContains(int[][] grid, int row, int col, int number) {
        int startX = col - col % 3;
        int startY = row - row % 3;

        for (int i = startY; i < startY + 3; i++) {
            for (int j = startX; j < startX + 3; j++) {
                if (grid[i][j] == number) return true;
            }
        }
        return false;
    }

    public static boolean isValidPlacement(int[][] grid, int row, int col, int number) {
        return !rowContains(grid, row, number) && !colContains(grid, col, number) && !boxContains(grid, row, col, number);
    }

    public static void printGrid(int[][] grid) {
        for (int col = 0; col < GRID_SIZE; col++) {
            if(col % 3 == 0) System.out.println(" -------------------");
            for (int row = 0; row < GRID_SIZE; row++) {
                if(row % 3 == 0) System.out.print(" | ");
                System.out.print(grid[col][row]);
            }
            System.out.println(" |");
        }
        System.out.println(" -------------------");
    }
}
