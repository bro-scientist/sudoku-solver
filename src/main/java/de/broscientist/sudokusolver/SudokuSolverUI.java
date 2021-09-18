package de.broscientist.sudokusolver;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Scanner;

import static de.broscientist.sudokusolver.SudokuUtility.GRID_SIZE;


public class SudokuSolverUI extends MouseAdapter implements ActionListener
{
    private static final SudokuSolver solver = new SudokuSolver();
    Scanner keyboard = new Scanner(System.in);

    private static final int SCALE = 8;
    private static final int OUTER_RATIO = 6;
    private static final int SQUARE_RATIO = 10;
    private static final int WINDOW_WIDTH_RATIO = 2*OUTER_RATIO+GRID_SIZE*SQUARE_RATIO + 2;
    private static final int WINDOW_HEIGHT_RATIO = WINDOW_WIDTH_RATIO + 2;

    private static final String WINDOW_TITLE = "Sudoku Solver";
    private static final String IMAGE_PATH = "sudoku.png";

    private static final Font NUMBER_FONT = new Font("Trebuchet MS", Font.PLAIN,6* SCALE);

    private static final Color BACKGROUND_COLOR = new Color(10968413);
    private static final Color FIELD_COLOR_1 = Color.DARK_GRAY;
    private static final Color FIELD_COLOR_2 = Color.LIGHT_GRAY;
    private static final Color SELECTED_FIELD_COLOR = Color.YELLOW;
    private static final Color INPUT_NUMBER_COLOR = Color.BLACK;
    private static final Color SOLVED_NUMBER_COLOR = Color.YELLOW;

    private static final Border BORDER = new LineBorder(Color.BLACK);

    private final JFrame frame;

    private final JPanel[][] gridPanels;
    private final JLabel[][] gridLabels;
    private int [][] inputNumbers;

    private final JButton submitButton = new JButton("Sudoku lösen?");

    public SudokuSolverUI() {
        this.frame = new JFrame();

        this.frame.setTitle(WINDOW_TITLE);
        this.frame.setIconImage(new ImageIcon(IMAGE_PATH).getImage());
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setSize(WINDOW_WIDTH_RATIO* SCALE, WINDOW_HEIGHT_RATIO* SCALE);
        this.frame.setResizable(true);
        this.frame.getContentPane().setBackground(BACKGROUND_COLOR);
        this.frame.setLayout(null);

        this.gridPanels = new JPanel[GRID_SIZE][GRID_SIZE];
        this.gridLabels = new JLabel[GRID_SIZE][GRID_SIZE];
        this.inputNumbers = new int[GRID_SIZE][GRID_SIZE];

        for (int y = 0; y < GRID_SIZE; y++) {
            for (int x = 0; x < GRID_SIZE; x++) {
                JPanel panel = new JPanel();
                this.gridPanels[y][x] = panel;
                panel.setBounds((OUTER_RATIO + SQUARE_RATIO*x)* SCALE, (OUTER_RATIO + (SQUARE_RATIO)*y)* SCALE, SQUARE_RATIO* SCALE, SQUARE_RATIO* SCALE);
                panel.setBackground(((x/3)+(y/3))%2 == 0 ? FIELD_COLOR_1 : FIELD_COLOR_2);
                panel.setBorder(BORDER);
                this.frame.add(panel);

                JLabel label = new JLabel(null, null, JLabel.CENTER);
                this.gridLabels[y][x] =  label;
                label.setForeground(INPUT_NUMBER_COLOR);
                label.setFont(NUMBER_FONT);
                label.setVisible(true);
                panel.add(label);
            }
        }
    }

    private void refreshLabels(boolean solved) {
        for (int y = 0; y < GRID_SIZE; y++) {
            for (int x = 0; x < GRID_SIZE; x++) {
                JLabel label = gridLabels[y][x];
                int inputNumber = inputNumbers[y][x];

                if (solved) {
                    try {
                        Integer.parseInt(label.getText());
                    }
                    catch (Exception ex)
                    {
                        label.setForeground(SOLVED_NUMBER_COLOR);
                        label.setText(Integer.toString(inputNumber));
                    }
                }
                else if(inputNumber != 0) {
                    label.setText(Integer.toString(inputNumber));
                }
            }
        }
    }

    private void refreshLabels() {
        refreshLabels(false);
    }

    private boolean isInGrid(int posX, int posY) {
        int outerBorderSize = OUTER_RATIO * SCALE;
        return outerBorderSize < posX && posX < outerBorderSize + GRID_SIZE*SQUARE_RATIO* SCALE &&
                outerBorderSize < posY && posY < outerBorderSize + GRID_SIZE*SQUARE_RATIO* SCALE;
    }

    private boolean solvePuzzle() {
        solver.setNumbers(inputNumbers);
        return solver.solve();
        // TODO: unset editable flag
        // TODO: delete numbers array
        // TODO: use return value
    }

    public void userInput() {
        this.refreshLabels();

        JPanel submitButtonPanel = new JPanel();
        submitButtonPanel.setBackground(null);
        submitButtonPanel.add(submitButton);
        submitButtonPanel.setBounds((OUTER_RATIO + SQUARE_RATIO*4)* SCALE, (OUTER_RATIO + SQUARE_RATIO*9)* SCALE, 110, 30);

        submitButton.addActionListener(this);
        this.frame.add(submitButtonPanel);

        this.frame.getRootPane().addMouseListener(this);
        this.frame.setVisible(true);
        while (true){
        }
    }

    // TODO: could be moved into mousePressed()
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submitButton) {
            this.solvePuzzle();
            refreshLabels(true);
        }
    }

    @Override
    public void mousePressed(MouseEvent event) {
        int mouseX = event.getX();
        int mouseY = event.getY();
        if (isInGrid(mouseX, mouseY)) {
            int row = (mouseY/ SCALE - OUTER_RATIO) / SQUARE_RATIO;
            int col = (mouseX/ SCALE - OUTER_RATIO) / SQUARE_RATIO;
            Color before = gridPanels[row][col].getBackground();
            gridPanels[row][col].setBackground(SELECTED_FIELD_COLOR);
            this.refreshLabels();

            //System.out.println(keyboard.nextInt());

            try {
                inputNumbers[row][col] = Integer.parseInt(JOptionPane.showInputDialog("enter number"));
            }
            catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
            gridPanels[row][col].setBackground(before);
            this.refreshLabels();
        }
    }
}
