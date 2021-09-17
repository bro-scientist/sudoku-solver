package de.broscientist.sudokusolver;

import static de.broscientist.sudokusolver.SudokuUtility.isValidPlacement;
import static de.broscientist.sudokusolver.SudokuUtility.GRID_SIZE;

public class SudokuSolver
{
    private int[][] grid;

    public void setGrid(int[][] grid) {
        this.grid = grid;
    }

    public int[][] getGrid() {
        return this.grid;
    }

    public boolean solve() {
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                if (grid[row][col] == 0) {
                    for (int i = 1; i <= GRID_SIZE; i++) {
                        if (isValidPlacement(this.grid, row, col, i)) {
                            grid[row][col] = i;
                            if (solve()) return true;
                            else grid[row][col] = 0;
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }
}
