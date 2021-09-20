package de.broscientist.sudokusolver;

import static de.broscientist.sudokusolver.SudokuUtility.isValidPlacement;
import static de.broscientist.sudokusolver.SudokuUtility.GRID_SIZE;

public class SudokuSolver
{
    private int[][] numbers;

    public void setNumbers(int[][] numbers) {
        this.numbers = numbers;
    }

    public int[][] getNumbers() {
        return this.numbers;
    }

    public boolean solve() {
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                if (numbers[row][col] == 0) {
                    for (int i = 1; i <= GRID_SIZE; i++) {
                        if (isValidPlacement(this.numbers, row, col, i)) {
                            numbers[row][col] = i;
                            if (solve()) return true;
                            else numbers[row][col] = 0;
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }
}
