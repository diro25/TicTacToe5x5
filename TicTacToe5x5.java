import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TicTacToe5x5 extends JFrame {
    private JButton[][] buttons;
    private String[][] board;
    private String currentPlayer;
    private boolean gameActive;
    private JLabel turnLabel;
    private final int BOARD_SIZE = 5;
    private final int WIN_CONDITION = 4;

    public TicTacToe5x5() {
        // Initialize variables
        currentPlayer = "X";
        board = new String[BOARD_SIZE][BOARD_SIZE];
        buttons = new JButton[BOARD_SIZE][BOARD_SIZE];
        gameActive = true;

        // Setup frame
        setTitle("5x5 Tic Tac Toe");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());

        // Top panel for title and turn
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setBackground(new Color(44, 62, 80));

        JLabel titleLabel = new JLabel("5x5 Tic Tac Toe (4 in a row wins!)");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        turnLabel = new JLabel("Player X's Turn");
        turnLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        turnLabel.setForeground(Color.WHITE);
        turnLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        topPanel.add(Box.createVerticalStrut(10));
        topPanel.add(titleLabel);
        topPanel.add(Box.createVerticalStrut(10));
        topPanel.add(turnLabel);
        topPanel.add(Box.createVerticalStrut(10));

        add(topPanel, BorderLayout.NORTH);

        // Center panel for game board
        JPanel boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(BOARD_SIZE, BOARD_SIZE, 5, 5));
        boardPanel.setBackground(new Color(44, 62, 80));
        boardPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Initialize board and buttons
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                board[i][j] = "";
                buttons[i][j] = new JButton();
                buttons[i][j].setFont(new Font("Arial", Font.BOLD, 20));
                buttons[i][j].setBackground(new Color(236, 240, 241));
                buttons[i][j].setPreferredSize(new Dimension(80, 80));
                final int row = i;
                final int col = j;
                buttons[i][j].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        makeMove(row, col);
                    }
                });
                boardPanel.add(buttons[i][j]);
            }
        }

        add(boardPanel, BorderLayout.CENTER);

        // Bottom panel for reset button
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(new Color(44, 62, 80));

        JButton resetButton = new JButton("New Game");
        resetButton.setFont(new Font("Arial", Font.BOLD, 14));
        resetButton.setBackground(new Color(39, 174, 96));
        resetButton.setForeground(Color.WHITE);
        resetButton.setFocusPainted(false);
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetGame();
            }
        });

        bottomPanel.add(resetButton);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(bottomPanel, BorderLayout.SOUTH);

        // Frame settings
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void makeMove(int row, int col) {
        if (!gameActive)
            return;

        if (board[row][col].equals("")) {
            // Make the move
            board[row][col] = currentPlayer;
            buttons[row][col].setText(currentPlayer);

            // Set colors for X and O
            if (currentPlayer.equals("X")) {
                buttons[row][col].setForeground(new Color(231, 76, 60));
            } else {
                buttons[row][col].setForeground(new Color(52, 152, 219));
            }

            // Check for win
            if (checkWin(row, col)) {
                gameActive = false;
                JOptionPane.showMessageDialog(this, "Player " + currentPlayer + " wins! 🎉");
                turnLabel.setText("Player " + currentPlayer + " wins!");
                highlightWinningCells(row, col);
                return;
            }

            // Check for draw
            if (checkDraw()) {
                gameActive = false;
                JOptionPane.showMessageDialog(this, "It's a draw! 🤝");
                turnLabel.setText("Game Draw!");
                return;
            }

            // Switch players
            currentPlayer = currentPlayer.equals("X") ? "O" : "X";
            turnLabel.setText("Player " + currentPlayer + "'s Turn");
        }
    }

    private boolean checkWin(int row, int col) {
        String player = board[row][col];

        // Check horizontal
        int count = 1;
        // Right
        for (int c = col + 1; c < BOARD_SIZE; c++) {
            if (board[row][c].equals(player))
                count++;
            else
                break;
        }
        // Left
        for (int c = col - 1; c >= 0; c--) {
            if (board[row][c].equals(player))
                count++;
            else
                break;
        }
        if (count >= WIN_CONDITION)
            return true;

        // Check vertical
        count = 1;
        // Down
        for (int r = row + 1; r < BOARD_SIZE; r++) {
            if (board[r][col].equals(player))
                count++;
            else
                break;
        }
        // Up
        for (int r = row - 1; r >= 0; r--) {
            if (board[r][col].equals(player))
                count++;
            else
                break;
        }
        if (count >= WIN_CONDITION)
            return true;

        // Check diagonal (\)
        count = 1;
        // Down-right
        int r = row + 1, c = col + 1;
        while (r < BOARD_SIZE && c < BOARD_SIZE) {
            if (board[r][c].equals(player)) {
                count++;
                r++;
                c++;
            } else
                break;
        }
        // Up-left
        r = row - 1;
        c = col - 1;
        while (r >= 0 && c >= 0) {
            if (board[r][c].equals(player)) {
                count++;
                r--;
                c--;
            } else
                break;
        }
        if (count >= WIN_CONDITION)
            return true;

        // Check diagonal (/)
        count = 1;
        // Down-left
        r = row + 1;
        c = col - 1;
        while (r < BOARD_SIZE && c >= 0) {
            if (board[r][c].equals(player)) {
                count++;
                r++;
                c--;
            } else
                break;
        }
        // Up-right
        r = row - 1;
        c = col + 1;
        while (r >= 0 && c < BOARD_SIZE) {
            if (board[r][c].equals(player)) {
                count++;
                r--;
                c++;
            } else
                break;
        }
        if (count >= WIN_CONDITION)
            return true;

        return false;
    }

    private void highlightWinningCells(int row, int col) {
        String player = board[row][col];

        // Check all directions and highlight
        int[][] directions = { { 0, 1 }, { 1, 0 }, { 1, 1 }, { 1, -1 } };

        for (int[] dir : directions) {
            java.util.ArrayList<Point> cells = new java.util.ArrayList<>();
            cells.add(new Point(row, col));

            // Positive direction
            for (int step = 1; step < WIN_CONDITION; step++) {
                int r = row + dir[0] * step;
                int c = col + dir[1] * step;
                if (r >= 0 && r < BOARD_SIZE && c >= 0 && c < BOARD_SIZE && board[r][c].equals(player)) {
                    cells.add(new Point(r, c));
                } else
                    break;
            }

            // Negative direction
            for (int step = 1; step < WIN_CONDITION; step++) {
                int r = row - dir[0] * step;
                int c = col - dir[1] * step;
                if (r >= 0 && r < BOARD_SIZE && c >= 0 && c < BOARD_SIZE && board[r][c].equals(player)) {
                    cells.add(new Point(r, c));
                } else
                    break;
            }

            if (cells.size() >= WIN_CONDITION) {
                for (Point p : cells) {
                    buttons[p.x][p.y].setBackground(new Color(241, 196, 15));
                }
                break;
            }
        }
    }

    private boolean checkDraw() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (board[i][j].equals("")) {
                    return false;
                }
            }
        }
        return true;
    }

    private void resetGame() {
        currentPlayer = "X";
        gameActive = true;

        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                board[i][j] = "";
                buttons[i][j].setText("");
                buttons[i][j].setBackground(new Color(236, 240, 241));
            }
        }

        turnLabel.setText("Player X's Turn");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TicTacToe5x5();
            }
        });
    }
}