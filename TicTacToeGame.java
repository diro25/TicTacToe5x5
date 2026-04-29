import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TicTacToeGame extends JFrame implements ActionListener, MouseListener {

    // Game components
    private JButton[][] buttons = new JButton[3][3];
    private JPanel boardPanel;
    private JPanel topPanel;
    private JLabel statusLabel;
    private JLabel playerXScoreLabel;
    private JLabel playerOScoreLabel;
    private JButton resetButton;
    private JButton clearScoresButton;

    // Game state
    private char currentPlayer; // 'X' or 'O'
    private boolean gameActive;
    private int playerXWins = 0;
    private int playerOWins = 0;
    private int ties = 0;

    // Colors for hover effects
    private Color defaultButtonColor = new Color(240, 240, 240);
    private Color hoverColor = new Color(200, 220, 255);
    private Color xColor = new Color(0, 0, 200); // Blue for X
    private Color oColor = new Color(200, 0, 0); // Red for O

    // Constructor
    public TicTacToeGame() {
        // Set up the main frame
        setTitle("Tic-Tac-Toe Game with Mouse Events");
        setSize(600, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        // Initialize game state
        currentPlayer = 'X';
        gameActive = true;

        // Create top panel with status and scores
        createTopPanel();

        // Create game board panel
        createBoardPanel();

        // Add panels to frame
        add(topPanel, BorderLayout.NORTH);
        add(boardPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    private void createTopPanel() {
        topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(2, 1, 5, 5));
        topPanel.setBackground(new Color(50, 50, 100));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Status label panel
        JPanel statusPanel = new JPanel(new BorderLayout());
        statusPanel.setBackground(new Color(50, 50, 100));

        statusLabel = new JLabel("Player X's Turn", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 20));
        statusLabel.setForeground(Color.WHITE);
        statusLabel.setOpaque(true);
        statusLabel.setBackground(new Color(50, 50, 100));

        statusPanel.add(statusLabel, BorderLayout.CENTER);

        // Score panel
        JPanel scorePanel = new JPanel(new GridLayout(1, 3, 10, 0));
        scorePanel.setBackground(new Color(50, 50, 100));
        scorePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        playerXScoreLabel = new JLabel("Player X: 0", SwingConstants.CENTER);
        playerXScoreLabel.setFont(new Font("Arial", Font.BOLD, 14));
        playerXScoreLabel.setForeground(new Color(100, 200, 255));
        playerXScoreLabel.setOpaque(true);
        playerXScoreLabel.setBackground(new Color(30, 30, 80));

        JLabel tieLabel = new JLabel("Ties: 0", SwingConstants.CENTER);
        tieLabel.setFont(new Font("Arial", Font.BOLD, 14));
        tieLabel.setForeground(Color.YELLOW);
        tieLabel.setOpaque(true);
        tieLabel.setBackground(new Color(30, 30, 80));

        playerOScoreLabel = new JLabel("Player O: 0", SwingConstants.CENTER);
        playerOScoreLabel.setFont(new Font("Arial", Font.BOLD, 14));
        playerOScoreLabel.setForeground(new Color(255, 150, 150));
        playerOScoreLabel.setOpaque(true);
        playerOScoreLabel.setBackground(new Color(30, 30, 80));

        // Store tie label reference for updates
        JLabel finalTieLabel = tieLabel;

        scorePanel.add(playerXScoreLabel);
        scorePanel.add(tieLabel);
        scorePanel.add(playerOScoreLabel);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        buttonPanel.setBackground(new Color(50, 50, 100));

        resetButton = new JButton("New Game");
        resetButton.setFont(new Font("Arial", Font.BOLD, 14));
        resetButton.setBackground(new Color(255, 200, 100));
        resetButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        resetButton.addActionListener(this);

        clearScoresButton = new JButton("Reset Scores");
        clearScoresButton.setFont(new Font("Arial", Font.BOLD, 14));
        clearScoresButton.setBackground(new Color(255, 150, 150));
        clearScoresButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        clearScoresButton.addActionListener(this);

        buttonPanel.add(resetButton);
        buttonPanel.add(clearScoresButton);

        topPanel.add(statusPanel);
        topPanel.add(scorePanel);
        topPanel.add(buttonPanel);
    }

    private void createBoardPanel() {
        boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(3, 3, 8, 8));
        boardPanel.setBackground(Color.BLACK);
        boardPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Create 3x3 grid of buttons
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j] = new JButton("");
                buttons[i][j].setFont(new Font("Arial", Font.BOLD, 70));
                buttons[i][j].setFocusPainted(false);
                buttons[i][j].setBackground(defaultButtonColor);
                buttons[i][j].setCursor(new Cursor(Cursor.HAND_CURSOR));

                // Add both ActionListener and MouseListener
                buttons[i][j].addActionListener(this);
                buttons[i][j].addMouseListener(this);

                boardPanel.add(buttons[i][j]);
            }
        }
    }

    // ========== ACTION LISTENER METHODS ==========
    @Override
    public void actionPerformed(ActionEvent e) {
        // Handle reset button click
        if (e.getSource() == resetButton) {
            resetGame();
            return;
        }

        // Handle clear scores button
        if (e.getSource() == clearScoresButton) {
            resetScores();
            return;
        }

        // Handle board button clicks
        if (!gameActive) {
            JOptionPane.showMessageDialog(this, "Game is over! Click 'New Game' to play again.");
            return;
        }

        // Find which button was clicked
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (e.getSource() == buttons[i][j]) {
                    // Check if cell is empty
                    if (buttons[i][j].getText().equals("")) {
                        makeMove(i, j);
                    } else {
                        JOptionPane.showMessageDialog(this, "This cell is already taken!");
                        // Flash red to indicate invalid move
                        flashButton(buttons[i][j], Color.RED);
                    }
                    return;
                }
            }
        }
    }

    // ========== MOUSE LISTENER METHODS (Extra Feature) ==========
    @Override
    public void mouseEntered(MouseEvent e) {
        // Hover effect: change button color when mouse enters
        if (gameActive) {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (e.getSource() == buttons[i][j] && buttons[i][j].getText().equals("")) {
                        buttons[i][j].setBackground(hoverColor);
                        // Optional: change cursor
                        buttons[i][j].setCursor(new Cursor(Cursor.HAND_CURSOR));
                    }
                }
            }
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // Restore original color when mouse exits
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (e.getSource() == buttons[i][j]) {
                    if (buttons[i][j].getText().equals("")) {
                        buttons[i][j].setBackground(defaultButtonColor);
                    } else {
                        // Keep winning highlight if any
                        if (!buttons[i][j].getBackground().equals(new Color(100, 255, 100))) {
                            buttons[i][j].setBackground(defaultButtonColor);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // Right-click feature: show hint or info
        if (SwingUtilities.isRightMouseButton(e)) {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (e.getSource() == buttons[i][j]) {
                        if (buttons[i][j].getText().equals("")) {
                            // Show hint: which player's turn it is
                            JOptionPane.showMessageDialog(this,
                                    "Empty cell! Click to place " + currentPlayer,
                                    "Hint",
                                    JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            // Show who placed the mark
                            JOptionPane.showMessageDialog(this,
                                    "This cell contains " + buttons[i][j].getText(),
                                    "Cell Info",
                                    JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // Optional: add click animation effect
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (e.getSource() == buttons[i][j] && buttons[i][j].getText().equals("")) {
                    buttons[i][j].setBackground(new Color(180, 200, 255));
                }
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // Restore after click animation
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (e.getSource() == buttons[i][j] && buttons[i][j].getText().equals("")) {
                    buttons[i][j].setBackground(defaultButtonColor);
                }
            }
        }
    }

    // ========== GAME LOGIC METHODS ==========
    private void flashButton(JButton button, Color color) {
        Color originalColor = button.getBackground();
        button.setBackground(color);
        Timer timer = new Timer(200, e -> button.setBackground(originalColor));
        timer.setRepeats(false);
        timer.start();
    }

    private void makeMove(int row, int col) {
        // Place current player's mark
        buttons[row][col].setText(String.valueOf(currentPlayer));

        // Set color based on player
        if (currentPlayer == 'X') {
            buttons[row][col].setForeground(xColor);
        } else {
            buttons[row][col].setForeground(oColor);
        }
        buttons[row][col].setBackground(defaultButtonColor);

        // Check for win or tie
        if (checkWin()) {
            gameActive = false;
            if (currentPlayer == 'X') {
                playerXWins++;
                statusLabel.setText("Player X Wins!");
            } else {
                playerOWins++;
                statusLabel.setText("Player O Wins!");
            }
            statusLabel.setBackground(new Color(0, 150, 0));
            updateScoreDisplay();
            JOptionPane.showMessageDialog(this, "Player " + currentPlayer + " wins! 🎉");
            return;
        }

        if (checkTie()) {
            gameActive = false;
            ties++;
            statusLabel.setText("Game Tied!");
            statusLabel.setBackground(new Color(150, 100, 0));
            updateScoreDisplay();
            JOptionPane.showMessageDialog(this, "It's a tie! 🤝");
            return;
        }

        // Switch players
        currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
        statusLabel.setText("Player " + currentPlayer + "'s Turn");
        statusLabel.setBackground(new Color(50, 50, 100));
    }

    private boolean checkWin() {
        // Check rows, columns, and diagonals
        for (int i = 0; i < 3; i++) {
            // Check rows
            if (buttons[i][0].getText().equals(String.valueOf(currentPlayer)) &&
                    buttons[i][1].getText().equals(String.valueOf(currentPlayer)) &&
                    buttons[i][2].getText().equals(String.valueOf(currentPlayer))) {
                highlightWinningCells(i, 0, i, 1, i, 2);
                return true;
            }
            // Check columns
            if (buttons[0][i].getText().equals(String.valueOf(currentPlayer)) &&
                    buttons[1][i].getText().equals(String.valueOf(currentPlayer)) &&
                    buttons[2][i].getText().equals(String.valueOf(currentPlayer))) {
                highlightWinningCells(0, i, 1, i, 2, i);
                return true;
            }
        }

        // Check main diagonal
        if (buttons[0][0].getText().equals(String.valueOf(currentPlayer)) &&
                buttons[1][1].getText().equals(String.valueOf(currentPlayer)) &&
                buttons[2][2].getText().equals(String.valueOf(currentPlayer))) {
            highlightWinningCells(0, 0, 1, 1, 2, 2);
            return true;
        }

        // Check anti-diagonal
        if (buttons[0][2].getText().equals(String.valueOf(currentPlayer)) &&
                buttons[1][1].getText().equals(String.valueOf(currentPlayer)) &&
                buttons[2][0].getText().equals(String.valueOf(currentPlayer))) {
            highlightWinningCells(0, 2, 1, 1, 2, 0);
            return true;
        }

        return false;
    }

    private void highlightWinningCells(int... positions) {
        for (int i = 0; i < positions.length; i += 2) {
            int row = positions[i];
            int col = positions[i + 1];
            buttons[row][col].setBackground(new Color(100, 255, 100));
        }
    }

    private boolean checkTie() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (buttons[i][j].getText().equals("")) {
                    return false;
                }
            }
        }
        return true;
    }

    private void resetGame() {
        // Reset game state
        currentPlayer = 'X';
        gameActive = true;
        statusLabel.setText("Player X's Turn");
        statusLabel.setBackground(new Color(50, 50, 100));

        // Clear all buttons
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
                buttons[i][j].setBackground(defaultButtonColor);
                buttons[i][j].setForeground(Color.BLACK);
            }
        }
    }

    private void resetScores() {
        playerXWins = 0;
        playerOWins = 0;
        ties = 0;
        updateScoreDisplay();
        resetGame();
        JOptionPane.showMessageDialog(this, "Scores have been reset!");
    }

    private void updateScoreDisplay() {
        playerXScoreLabel.setText("Player X: " + playerXWins);
        playerOScoreLabel.setText("Player O: " + playerOWins);
        // Update tie label - find it from topPanel
        Component[] components = topPanel.getComponents();
        for (Component comp : components) {
            if (comp instanceof JPanel) {
                JPanel panel = (JPanel) comp;
                if (panel.getLayout() instanceof GridLayout && panel.getComponentCount() == 3) {
                    JLabel tieLabel = (JLabel) panel.getComponent(1);
                    tieLabel.setText("Ties: " + ties);
                }
            }
        }
    }

    // Main method to run the game
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TicTacToeGame();
            }
        });
    }
}