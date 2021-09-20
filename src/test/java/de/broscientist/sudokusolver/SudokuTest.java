package de.broscientist.sudokusolver;

import org.junit.jupiter.api.Test;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SudokuTest
{
    // first set
    int [][] testGrid_1 = {
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

    int [][] expectedGrid_1 = {
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

    //second set
    int [][] testGrid_2 = {
            {0,7,2, 0,0,9, 0,0,0},
            {0,3,0, 6,0,0, 4,0,0},
            {0,0,1, 0,0,0, 0,8,7},

            {1,0,0, 0,0,0, 7,0,0},
            {9,0,0, 2,0,3, 0,0,0},
            {0,0,0, 0,0,0, 0,0,6},

            {0,0,0, 3,0,0, 5,6,0},
            {0,0,0, 0,0,4, 9,0,0},
            {0,0,0, 0,1,8, 0,0,2}
    };

    int [][] expectedGrid_2 = {
            {4,7,2, 1,8,9, 6,3,5},
            {8,3,5, 6,2,7, 4,1,9},
            {6,9,1, 4,3,5, 2,8,7},

            {1,2,4, 8,5,6, 7,9,3},
            {9,8,6, 2,7,3, 1,5,4},
            {3,5,7, 9,4,1, 8,2,6},

            {7,4,8, 3,9,2, 5,6,1},
            {2,1,3, 5,6,4, 9,7,8},
            {5,6,9, 7,1,8, 3,4,2}
    };

    @Test
    public void testSolve() {
        SudokuSolver solver = new SudokuSolver();

        // 1st test
        solver.setNumbers(testGrid_1);
        assertTrue("solve function should return true. (test1)", solver.solve());
        assertEquals(expectedGrid_1, solver.getNumbers());

        // 2nd test
        solver.setNumbers(testGrid_2);
        assertTrue("solve function should return true. (test2)", solver.solve());
        assertEquals(expectedGrid_2, solver.getNumbers());
    }
}
