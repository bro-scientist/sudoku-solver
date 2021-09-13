package de.broscientist.sudokusolver;

public class SudokuSolver
{
    private static final int SIZE = 9;
    private int[][] grid;

    public void setGrid(int[][] grid) {
        this.grid = grid;
    }
    public int[][] solve() {
        return this.grid;
    }

}
