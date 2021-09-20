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

import static de.broscientist.sudokusolver.SudokuUtility.GRID_SIZE;


public class SudokuSolverUI extends MouseAdapter implements ActionListener
{
    private static final SudokuSolver solver = new SudokuSolver();
    Scanner keyboard = new Scanner(System.in);

    private enum UIState {INPUT, SOLVED, ERROR};
    private UIState state;

    private static final int SCALE = 8;
    private static final int OUTER_RATIO = 6;
    private static final int SQUARE_RATIO = 10;
    private static final int WINDOW_WIDTH_RATIO = 2*OUTER_RATIO+GRID_SIZE*SQUARE_RATIO+2;
    private static final int WINDOW_HEIGHT_RATIO = WINDOW_WIDTH_RATIO+4;

    private static final String WINDOW_TITLE = "Sudoku Solver";
    private static final String INPUT_BTN_TEXT = "Sudoku lösen?";

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

    private final JLayeredPane contentPanel;
    private JLabel background;

    private final JPanel sudokuContainer;
    private final JPanel[][] gridPanels;
    private final JLabel[][] gridLabels;
    private int [][] inputNumbers;

    private final JPanel submitButtonPanel;
    private final JButton submitButton;

    public SudokuSolverUI() {
        this.frame = new JFrame();
        this.contentPanel = new JLayeredPane();

        this.sudokuContainer = new JPanel();
        this.sudokuContainer.setLayout(new GridLayout(GRID_SIZE, GRID_SIZE));
        this.sudokuContainer.setBounds(OUTER_RATIO*SCALE, OUTER_RATIO*SCALE, SQUARE_RATIO*GRID_SIZE*SCALE, SQUARE_RATIO*GRID_SIZE*SCALE);

        try {
            this.background = new JLabel(new ImageIcon(ImageIO.read(new File(BACKGROUND_PATH))));
            this.contentPanel.add(background, JLayeredPane.DEFAULT_LAYER);
        } catch (IOException e) {
            this.background = new JLabel();
            this.background.setBackground(BACKGROUND_COLOR);
        }

        this.background.setBounds(0, 0, WINDOW_WIDTH_RATIO*SCALE, WINDOW_HEIGHT_RATIO*SCALE);
        this.contentPanel.add(sudokuContainer, JLayeredPane.PALETTE_LAYER);
        this.frame.setContentPane(contentPanel);
        this.background.setLayout(new FlowLayout());

        this.frame.setTitle(WINDOW_TITLE);
        this.frame.setIconImage(new ImageIcon(ICON_PATH).getImage());
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setSize(WINDOW_WIDTH_RATIO*SCALE, WINDOW_HEIGHT_RATIO*SCALE);
        this.frame.setResizable(true);
        this.frame.setLayout(null);

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

        this.submitButton = new JButton(INPUT_BTN_TEXT);

        this.submitButtonPanel = new JPanel();
        this.submitButtonPanel.setBackground(null);
        this.submitButtonPanel.add(this.submitButton);
        this.submitButtonPanel.setBounds((OUTER_RATIO+SQUARE_RATIO*4)*SCALE, (OUTER_RATIO+SQUARE_RATIO*9)* SCALE, 110, 30);

        this.submitButton.addActionListener(this);
        this.contentPanel.add(submitButtonPanel, JLayeredPane.MODAL_LAYER);

        this.sudokuContainer.setVisible(true);
        this.frame.setVisible(true);
    }

    private void refreshLabels() {
        for (int y = 0; y < GRID_SIZE; y++) {
            for (int x = 0; x < GRID_SIZE; x++) {
                JLabel label = this.gridLabels[y][x];
                int inputNumber = this.inputNumbers[y][x];

                // TODO: switch case
                if (this.state == UIState.SOLVED) {
                    try {
                        Integer.parseInt(label.getText());
                    }
                    catch (Exception ex)
                    {
                        label.setForeground(SOLVED_NUMBER_COLOR);
                        label.setText(inputNumber != 0 ? Integer.toString(inputNumber) : "0");
                    }
                }
                else {
                    label.setText(inputNumber != 0 ? Integer.toString(inputNumber) : "");
                }
                // TODO: selected Label rendering
            }
        }
    }

    private boolean isInGrid(int posX, int posY) {
        int outerBorderSize = OUTER_RATIO*SCALE;
        return outerBorderSize < posX && posX < outerBorderSize + GRID_SIZE*SQUARE_RATIO*SCALE &&
                outerBorderSize < posY && posY < outerBorderSize + GRID_SIZE*SQUARE_RATIO*SCALE;
    }

    private boolean solvePuzzle() {
        solver.setNumbers(inputNumbers);
        boolean success = solver.solve();
        this.state = success ? UIState.SOLVED : UIState.ERROR;
        return success;
        // TODO: unset editable flag
        // TODO: delete numbers array
        // TODO: use return value
    }

    public void userInput() {
        this.state = UIState.INPUT;
        this.refreshLabels();

        this.frame.getRootPane().addMouseListener(this);

        while (true){
        }
    }

    // TODO: could be moved into mousePressed()
    @Override
    public void actionPerformed(ActionEvent e) {
        if (this.state == UIState.SOLVED) {
            this.inputNumbers = new int[GRID_SIZE][GRID_SIZE];
        }
        else if (e.getSource() == this.submitButton) {
            this.solvePuzzle();
        }
        refreshLabels();
    }

    @Override
    public void mousePressed(MouseEvent event) {
        if (this.state == UIState.SOLVED) return;
        int mouseX = event.getX();
        int mouseY = event.getY();
        if (isInGrid(mouseX, mouseY)) {
            int row = (mouseY/SCALE-OUTER_RATIO)/SQUARE_RATIO;
            int col = (mouseX/SCALE-OUTER_RATIO)/SQUARE_RATIO;
            Color before = this.gridPanels[row][col].getBackground();
            this.gridPanels[row][col].setBackground(SELECTED_FIELD_COLOR);
            this.refreshLabels();

            //System.out.println(keyboard.nextInt());

            try {
                int number = Integer.parseInt(JOptionPane.showInputDialog("enter number"));
                System.out.println(number);
                this.inputNumbers[row][col] = number;
            }
            catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
            this.gridPanels[row][col].setBackground(before);
            this.refreshLabels();
        }
    }
}
