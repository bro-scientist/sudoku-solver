package de.broscientist.sudokusolver;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import static de.broscientist.sudokusolver.SudokuUtility.isValidPlacement;
import static de.broscientist.sudokusolver.SudokuUtility.GRID_SIZE;


public class SudokuSolverUI extends MouseAdapter implements ActionListener
{
    private static final SudokuSolver solver = new SudokuSolver();
    Scanner keyboard = new Scanner(System.in);

    private enum UIState {INPUT, SOLVED, ERROR};
    private UIState state;

    private static final int SCALE = 9;
    private static final int OUTER_RATIO = 6;
    private static final int SQUARE_RATIO = 10;
    private static final int WINDOW_WIDTH_RATIO = 2*OUTER_RATIO+GRID_SIZE*SQUARE_RATIO+2;
    private static final int WINDOW_HEIGHT_RATIO = WINDOW_WIDTH_RATIO+4;

    private static final String WINDOW_TITLE = "Sudoku Solver";
    private static final String SOLVE_BTN_TEXT = "SOLVE PUZZLE";
    private static final String RESET_BTN_TEXT = "RESET";

    private static final String ICON_PATH = "resources/sudoku.png";
    private static final String BACKGROUND_PATH = "resources/background.jpg";

    private static final Font NUMBER_FONT = new Font("Trebuchet MS", Font.PLAIN,6*SCALE);

    private static final Color BACKGROUND_COLOR = new Color(0xA75D5D);
    private static final Color FIELD_COLOR_1 = new Color(0x808080);
    private static final Color FIELD_COLOR_2 = Color.LIGHT_GRAY;
    private static final Color SELECTED_FIELD_COLOR = Color.YELLOW;
    private static final Color INPUT_NUMBER_COLOR = Color.BLACK;
    private static final Color SOLVED_NUMBER_COLOR = Color.YELLOW;

    private static final Border BORDER = new LineBorder(Color.BLACK);

    private final JFrame frame;

    private JLabel background;

    private final JPanel sudokuContainer;
    private final JPanel[][] gridPanels;
    private final JLabel[][] gridLabels;
    private int [][] inputNumbers;

    private final JButton submitButton;

    public SudokuSolverUI() {
        // initialize frame
        this.frame = new JFrame(WINDOW_TITLE);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setSize(WINDOW_WIDTH_RATIO*SCALE, WINDOW_HEIGHT_RATIO*SCALE);
        this.frame.setResizable(false);
        this.frame.setIconImage(new ImageIcon(ICON_PATH).getImage());

        JLayeredPane contentPane = new JLayeredPane();
        this.frame.setContentPane(contentPane);
        this.sudokuContainer = new JPanel(new GridLayout(GRID_SIZE, GRID_SIZE));
        this.sudokuContainer.setBounds(OUTER_RATIO*SCALE, OUTER_RATIO*SCALE, SQUARE_RATIO*GRID_SIZE*SCALE, SQUARE_RATIO*GRID_SIZE*SCALE);
        this.sudokuContainer.setVisible(true);
        contentPane.add(sudokuContainer, JLayeredPane.PALETTE_LAYER);

        try {
            this.background = new JLabel(new ImageIcon(ImageIO.read(new File(BACKGROUND_PATH))));
        } catch (IOException ioe) {
            this.background = new JLabel();
        }

        this.background.setBounds(0, 0, WINDOW_WIDTH_RATIO*SCALE, WINDOW_HEIGHT_RATIO*SCALE);
        contentPane.add(background, JLayeredPane.DEFAULT_LAYER);

        // initialize sudoku grid
        this.gridPanels = new JPanel[GRID_SIZE][GRID_SIZE];
        this.gridLabels = new JLabel[GRID_SIZE][GRID_SIZE];
        this.inputNumbers = new int[GRID_SIZE][GRID_SIZE];

        for (int y = 0; y < GRID_SIZE; y++) {
            for (int x = 0; x < GRID_SIZE; x++) {
                JPanel panel = new JPanel();
                this.gridPanels[y][x] = panel;
                panel.setBounds((OUTER_RATIO+SQUARE_RATIO*x)*SCALE, (OUTER_RATIO+SQUARE_RATIO*y)*SCALE, SQUARE_RATIO*SCALE, SQUARE_RATIO*SCALE);
                panel.setBackground(((x/3)+(y/3))%2 == 0 ? FIELD_COLOR_1 : FIELD_COLOR_2);
                panel.setBorder(BORDER);
                this.sudokuContainer.add(panel);

                JLabel label = new JLabel(null, null, JLabel.CENTER);
                this.gridLabels[y][x] =  label;
                label.setForeground(INPUT_NUMBER_COLOR);
                label.setFont(NUMBER_FONT);
                label.setVisible(true);
                panel.add(label);
            }
        }

        // initialize buttons and add action listeners
        this.submitButton = new JButton();
        this.submitButton.setBounds((OUTER_RATIO+SQUARE_RATIO*3)*SCALE, (OUTER_RATIO+SQUARE_RATIO*GRID_SIZE)*SCALE, SQUARE_RATIO*3*SCALE, 30);

        // this.frame.getRootPane().addMouseListener(this);
        this.sudokuContainer.addMouseListener(this);
        this.submitButton.addActionListener(this);
        contentPane.add(submitButton, JLayeredPane.MODAL_LAYER);

        // show frame
        this.frame.setVisible(true);
    }

    private void refreshLabels() {
        for (int y = 0; y < GRID_SIZE; y++) {
            for (int x = 0; x < GRID_SIZE; x++) {
                JLabel label = this.gridLabels[y][x];
                int inputNumber = this.inputNumbers[y][x];

                switch (this.state) {
                    case SOLVED:
                        try {
                            Integer.parseInt(label.getText());
                        }
                        catch (NumberFormatException nfe)
                        {
                            label.setForeground(SOLVED_NUMBER_COLOR);
                            label.setText(inputNumber != 0 ? Integer.toString(inputNumber) : "0");
                        }
                        break;
                    case INPUT:
                        label.setForeground(INPUT_NUMBER_COLOR);
                        label.setText(inputNumber != 0 ? Integer.toString(inputNumber) : "");
                        break;
                    case ERROR:
                        JOptionPane.showMessageDialog(this.frame, "ERROR (refreshing)"); // TODO
                        return;
                }
                // TODO: selected Label rendering
            }
        }
    }

    public void userInput() {
        this.state = UIState.INPUT;
        this.submitButton.setText(SOLVE_BTN_TEXT);
        this.refreshLabels();
    }

    private boolean solvePuzzle() {
        solver.setNumbers(inputNumbers);
        boolean success = solver.solve();
        this.state = success ? UIState.SOLVED : UIState.ERROR;
        this.submitButton.setText(RESET_BTN_TEXT);
        refreshLabels();
        return success;
    }

    // TODO: could be moved into mousePressed()
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (state) {
            case SOLVED:
                this.inputNumbers = new int[GRID_SIZE][GRID_SIZE];
                userInput();
                break;
            case INPUT:
                if (e.getSource() == this.submitButton) this.solvePuzzle();
                break;
            case ERROR:
                JOptionPane.showMessageDialog(this.frame, "ERROR (button)"); // TODO
                break;
        }
    }

    @Override
    public void mousePressed(MouseEvent event) {
        switch (this.state)
        {
            case SOLVED:
                return;
            case INPUT:
                int row = event.getY()/(SCALE*SQUARE_RATIO);
                int col = event.getX()/(SCALE*SQUARE_RATIO);
                Color before = this.gridPanels[row][col].getBackground();
                this.gridPanels[row][col].setBackground(SELECTED_FIELD_COLOR);
                this.refreshLabels();

                //System.out.println(keyboard.nextInt());

                try {
                    // TODO: better input method
                    int number = Integer.parseInt(JOptionPane.showInputDialog("enter number"));
                    if (number <= 9 && number >= 1 && isValidPlacement(this.inputNumbers, row, col, number)) this.inputNumbers[row][col] = number;
                }
                catch (Exception ex) {
                    System.out.println("EXC: " + ex.getMessage());
                }
                this.gridPanels[row][col].setBackground(before);
                this.refreshLabels();
                break;
            case ERROR:
                JOptionPane.showMessageDialog(this.frame, "ERROR (mouse)"); // TODO
                break;
        }

    }
}
