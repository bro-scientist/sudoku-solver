package de.broscientist.sudokusolver;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static de.broscientist.sudokusolver.SudokuUtility.GRID_SIZE;


public class SudokuSolverUI extends MouseAdapter implements ActionListener
{
    private static final int SIZE = 8;
    private static final int WINDOW_WIDTH_RATIO = 104;
    private static final int WINDOW_HEIGHT_RATIO = 106;
    private static final int BORDER_RATIO = 6;
    private static final int SQUARE_RATIO = 10;

    private static final String WINDOW_TITLE = "Sudoku Solver";
    private static final String IMAGE_PATH = "sudoku.png";

    private static final Font NUMBER_FONT = new Font("Trebuchet MS", Font.PLAIN,6*SIZE);

    private static final Color BACKGROUND_COLOR = new Color(10968413);
    private static final Color FIELD_COLOR_1 = Color.DARK_GRAY;
    private static final Color FIELD_COLOR_2 = Color.LIGHT_GRAY;
    private static final Color INPUT_NUMBER_COLOR = Color.BLACK;
    private static final Color SOLVED_NUMBER_COLOR = Color.YELLOW;


    private final JFrame frame;

    private final JPanel[][] gridPanels;
    private final JLabel[][] gridLabels;
    private int [][] inputNumbers;
    private int [][] outputNumbers = {
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

    private final JButton submitButton = new JButton("Sudoku lösen?");
    private boolean running;

    public SudokuSolverUI() {
        this.frame = new JFrame();

        this.frame.setTitle(WINDOW_TITLE);
        this.frame.setIconImage(new ImageIcon(IMAGE_PATH).getImage());
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setSize(WINDOW_WIDTH_RATIO*SIZE, WINDOW_HEIGHT_RATIO*SIZE);
        this.frame.setResizable(true);
        this.frame.getContentPane().setBackground(BACKGROUND_COLOR);
        this.frame.setLayout(null);

        this.gridPanels = new JPanel[GRID_SIZE][GRID_SIZE];
        this.gridLabels = new JLabel[GRID_SIZE][GRID_SIZE];
        this.inputNumbers = new int[GRID_SIZE][GRID_SIZE];
        // this.outputNumbers = new int[GRID_SIZE][GRID_SIZE];

        for (int y = 0; y < GRID_SIZE; y++) {
            for (int x = 0; x < GRID_SIZE; x++) {
                JPanel panel = new JPanel();
                this.gridPanels[y][x] = panel;
                panel.setBounds((BORDER_RATIO + SQUARE_RATIO*x)*SIZE, (BORDER_RATIO + (SQUARE_RATIO)*y)*SIZE, SQUARE_RATIO*SIZE, SQUARE_RATIO*SIZE);
                panel.setBackground(((x/3)+(y/3))%2 == 0 ? FIELD_COLOR_1 : FIELD_COLOR_2);
                panel.setBorder(LineBorder.createBlackLineBorder());
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

    private void refreshLabels() {
        for (int y = 0; y < GRID_SIZE; y++) {
            for (int x = 0; x < GRID_SIZE; x++) {
                JLabel label = gridLabels[y][x];
                int number = inputNumbers[y][x];

                if(number != 0) {
                    // TODO: SOLVING label.setForeground(SOLVED_NUMBER_COLOR);
                    label.setText(Integer.toString(number));
                }
            }
        }
    }

    public void userInput() {
        this.refreshLabels();

        JPanel panel = new JPanel();
        panel.setBounds(770, 770, 70, 70);
        panel.setBackground(Color.BLUE);

        this.frame.getRootPane().addMouseListener(this);

        submitButton.addActionListener(this);
        panel.add(submitButton);

        this.frame.add(panel);

        this.frame.setVisible(true);
        this.running = true;
        while (this.running){
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submitButton) {
            System.out.println("poopie");
            this.frame.dispose();
        }
        if (e.getSource().equals(frame.getCursor())) {
            System.out.println("CURZZZZZZZZZZZZZZZZOOOOOOOOOOORRRRRR");
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        int mouseX = e.getX();
        int mouseY = e.getY();
        int borderSize = BORDER_RATIO*SIZE;
        if (borderSize < mouseX && mouseX < borderSize + GRID_SIZE*SQUARE_RATIO*SIZE
        && borderSize < mouseY && mouseY < borderSize + GRID_SIZE*SQUARE_RATIO*SIZE) {
            int row = (e.getY()/SIZE - BORDER_RATIO) / SQUARE_RATIO;
            int col = (e.getX()/SIZE - BORDER_RATIO) / SQUARE_RATIO;
            System.out.println("x"+col+"y"+row);
            inputNumbers[row][col] = Integer.parseInt(JOptionPane.showInputDialog("enter number"));
            System.out.println(inputNumbers[row][col]);
            this.refreshLabels();
        }
        else {
            System.out.println("naaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaahhhh.");
        }
    }
}
