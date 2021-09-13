package de.broscientist.sudokusolver;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

public class SolveTest
{
    @Test
    public void testSolve() {
        int [][] testGrid = {
                {7,0,0, 0,3,9, 8,1,0},
                {0,1,0, 2,8,5, 0,9,0},
                {0,9,0, 0,0,0, 0,2,0},

                {9,0,0, 0,0,0, 0,4,3},
                {2,3,0, 0,0,0, 0,6,8},
                {4,8,0, 0,0,0, 0,0,0},

                {0,7,0, 0,0,0, 0,3,0},
                {0,6,0, 3,1,8, 0,0,0},
                {0,4,9, 6,2,0, 0,0,5}
        };

        int [][] expectedResult = {
                {7,2,5, 4,3,9, 8,1,6},
                {6,1,4, 2,8,5, 3,9,7},
                {8,9,3, 1,7,6, 5,2,4},

                {9,5,1, 8,6,2, 7,4,3},
                {2,3,7, 5,4,1, 9,6,8},
                {4,8,6, 7,9,3, 2,5,1},

                {1,7,8, 9,5,4, 6,3,2},
                {5,6,2, 3,1,8, 4,7,9},
                {3,4,9, 6,2,7, 1,8,5}
        };

        SudokuSolver solver = new SudokuSolver();
        solver.setGrid(testGrid);

        assertEquals(expectedResult, solver.solve());
    }
}
