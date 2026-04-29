import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TicTacToe extends JFrame implements ActionListener {

  private JButton[] buttons = new JButton[9];
  private JButton resetButton;

  private JLabel title;
  private JLabel statusLabel;
  private JLabel scoreLabel;

  private boolean playerX = true;
  private int moves = 0;

  private int scoreX = 0;
  private int scoreO = 0;

  public TicTacToe() {

    setTitle("Tic Tac Toe Game");
    setSize(400, 500);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLayout(new BorderLayout());

    // TOP PANEL
    JPanel topPanel = new JPanel(new GridLayout(3, 1));

    title = new JLabel("TIC TAC TOE", JLabel.CENTER);
    title.setFont(new Font("Arial", Font.BOLD, 24));

    statusLabel = new JLabel("X Turn", JLabel.CENTER);
    statusLabel.setFont(new Font("Arial", Font.PLAIN, 18));

    scoreLabel = new JLabel("X: 0   |   O: 0", JLabel.CENTER);
    scoreLabel.setFont(new Font("Arial", Font.PLAIN, 16));

    topPanel.add(title);
    topPanel.add(statusLabel);
    topPanel.add(scoreLabel);

    add(topPanel, BorderLayout.NORTH);

    // BOARD
    JPanel boardPanel = new JPanel();
    boardPanel.setLayout(new GridLayout(3, 3));

    for (int i = 0; i < 9; i++) {
      buttons[i] = new JButton("");
      buttons[i].setFont(new Font("Arial", Font.BOLD, 40));
      buttons[i].addActionListener(this);
      boardPanel.add(buttons[i]);
    }

    add(boardPanel, BorderLayout.CENTER);

    // RESET BUTTON
    resetButton = new JButton("Reset Game");
    resetButton.addActionListener(this);
    add(resetButton, BorderLayout.SOUTH);

    setVisible(true);
  }

  @Override
  public void actionPerformed(ActionEvent e) {

    if (e.getSource() == resetButton) {
      resetBoard();
      return;
    }

    JButton clicked = (JButton) e.getSource();

    if (!clicked.getText().equals(""))
      return;

    // X and O styling
    if (playerX) {
      clicked.setText("X");
      clicked.setForeground(Color.BLUE);
      statusLabel.setText("O Turn");
    } else {
      clicked.setText("O");
      clicked.setForeground(Color.RED);
      statusLabel.setText("X Turn");
    }

    moves++;
    playerX = !playerX;

    checkWinner();
  }

  private void checkWinner() {

    int[][] win = {
        { 0, 1, 2 }, { 3, 4, 5 }, { 6, 7, 8 },
        { 0, 3, 6 }, { 1, 4, 7 }, { 2, 5, 8 },
        { 0, 4, 8 }, { 2, 4, 6 }
    };

    for (int[] w : win) {

      String a = buttons[w[0]].getText();
      String b = buttons[w[1]].getText();
      String c = buttons[w[2]].getText();

      if (!a.equals("") && a.equals(b) && b.equals(c)) {

        highlightWin(w);

        Toolkit.getDefaultToolkit().beep();

        JOptionPane.showMessageDialog(this, "Player " + a + " Wins!");

        updateScore(a);

        resetBoard();
        return;
      }
    }

    // DRAW
    if (moves == 9) {
      Toolkit.getDefaultToolkit().beep();
      JOptionPane.showMessageDialog(this, "It's a Draw!");
      resetBoard();
    }
  }

  private void highlightWin(int[] w) {
    for (int i : w) {
      buttons[i].setBackground(Color.GREEN);
    }
  }

  private void updateScore(String winner) {
    if (winner.equals("X"))
      scoreX++;
    else
      scoreO++;

    scoreLabel.setText("X: " + scoreX + "   |   O: " + scoreO);
  }

  private void resetBoard() {
    for (JButton b : buttons) {
      b.setText("");
      b.setBackground(null);
      b.setForeground(Color.BLACK);
    }

    playerX = true;
    moves = 0;
    statusLabel.setText("X Turn");
  }

  public static void main(String[] args) {
    new TicTacToe();
  }
}