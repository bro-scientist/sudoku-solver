import de.broscientist.sudokusolver.SudokuSolver;
import de.broscientist.sudokusolver.SudokuSolverUI;

public class Run
{
    public static void main(String[] args) {
        SudokuSolverUI ui = new SudokuSolverUI();
        SudokuSolver solver = new SudokuSolver();

        ui.userInput();
        // solver.setGrid(input);
        solver.solve();
    }
}
