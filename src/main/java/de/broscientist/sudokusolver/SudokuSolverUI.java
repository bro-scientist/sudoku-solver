package de.broscientist.sudokusolver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SudokuSolverUI extends JFrame implements ActionListener
{
    private static final int SIZE = 8;

    private static final String WINDOW_TITLE = "Sudoku Solver";
    private static final String IMAGE_PATH = "sudoku.png";

    private static final Font NUMBER_FONT = new Font("Trebuchet MS", Font.PLAIN,6*SIZE);

    private static final Color BACKGROUND_COLOR = new Color(10968413);
    private static final Color FIELD_COLOR_1 = Color.DARK_GRAY;
    private static final Color FIELD_COLOR_2 = Color.LIGHT_GRAY;
    private static final Color INPUT_NUMBER_COLOR = Color.BLACK;
    private static final Color SOLVED_NUMBER_COLOR = Color.YELLOW;

    private JPanel[][] gridPanels;
    private JLabel[][] gridLabels;
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
        this.setTitle(WINDOW_TITLE);
        this.setIconImage(new ImageIcon(IMAGE_PATH).getImage());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(104*SIZE, 106*SIZE);
        this.setResizable(true);
        this.getContentPane().setBackground(BACKGROUND_COLOR);
        this.setLayout(null);

        this.gridPanels = new JPanel[9][9];
        this.gridLabels = new JLabel[9][9];

        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 9; x++) {
                JPanel panel = new JPanel();
                this.gridPanels[y][x] = panel;
                panel.setBounds(6*SIZE + 10*SIZE*x, 6*SIZE + 10*SIZE*y, 9*SIZE, 9*SIZE);
                Color color = ((x / 3) + (y / 3)) % 2 == 0 ? FIELD_COLOR_1 : FIELD_COLOR_2;
                panel.setBackground(color);

                this.add(panel);

                JLabel label = new JLabel(null, null, JLabel.CENTER);
                this.gridLabels[y][x] =  label;
                label.setForeground(INPUT_NUMBER_COLOR);
                label.setFont(NUMBER_FONT);
                label.setVisible(true);
                panel.add(label);
            }
        }
    }

    public void userInput() {
        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 9; x++) {
                JLabel label = gridLabels[y][x];
                int number = outputNumbers[y][x];

                if(number == 0) label.setForeground(SOLVED_NUMBER_COLOR);
                label.setText(Integer.toString(number));
            }
        }

        JPanel panel = new JPanel();
        panel.setBounds(770, 770, 70, 70);
        panel.setBackground(Color.BLUE);

        this.getRootPane().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                System.out.println(e.getX() + "," + e.getY());
            }
        });

        submitButton.addActionListener(this);
        panel.add(submitButton);

        this.add(panel);

        // panel.setBorder(new BevelBorder(BevelBorder.RAISED));

        this.setVisible(true);
        this.running = true;
        while (this.running){
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submitButton) {
            System.out.println("poopie");
            this.dispose();
            this.running = false;
        }
        if (e.getSource().equals(getCursor())) {
            System.out.println("CURZZZZZZZZZZZZZZZZOOOOOOOOOOORRRRRR");
        }
    }
}
