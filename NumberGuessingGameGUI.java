import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;

public class NumberGuessingGameGUI extends JFrame {
    private final int LOWER_BOUND = 1;
    private final int UPPER_BOUND = 100;
    private int MAX_ATTEMPTS = 7;

    private int targetNumber;
    private int attempts;
    private int roundsPlayed = 0;
    private int roundsWon = 0;
    private int timeLeft = 60;

    private JLabel instructionLabel, feedbackLabel, attemptsLabel, scoreLabel, timerLabel;
    private JTextField guessField;
    private JButton guessButton, playAgainButton, resetButton, exitButton;
    private JTextArea logArea;
    private JComboBox<String> difficultyBox;
    private Timer countdownTimer;

    public NumberGuessingGameGUI() {
        setTitle("Number Guessing Game");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(600, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Top Panel
        JPanel topPanel = new JPanel(new GridLayout(2, 1));
        instructionLabel = new JLabel("Guess a number between " + LOWER_BOUND + " and " + UPPER_BOUND + ":", SwingConstants.CENTER);
        instructionLabel.setFont(new Font("Arial", Font.BOLD, 18));

        String[] difficulties = {"Easy (10 attempts)", "Medium (7 attempts)", "Hard (5 attempts)"};
        difficultyBox = new JComboBox<>(difficulties);
        difficultyBox.setSelectedIndex(1);
        difficultyBox.setFont(new Font("Arial", Font.PLAIN, 14));
        difficultyBox.addActionListener(e -> {
            switch (difficultyBox.getSelectedIndex()) {
                case 0 -> MAX_ATTEMPTS = 10;
                case 1 -> MAX_ATTEMPTS = 7;
                case 2 -> MAX_ATTEMPTS = 5;
            }
            resetGame();
        });

        JPanel diffPanel = new JPanel();
        diffPanel.add(new JLabel("Select Difficulty: "));
        diffPanel.add(difficultyBox);

        topPanel.add(instructionLabel);
        topPanel.add(diffPanel);
        add(topPanel, BorderLayout.NORTH);

        // Center Panel
        JPanel centerPanel = new JPanel(new GridLayout(6, 1, 10, 10));
        guessField = new JTextField();
        guessField.setFont(new Font("Arial", Font.PLAIN, 18));

        guessButton = new JButton("Submit Guess");
        guessButton.setFont(new Font("Arial", Font.BOLD, 16));

        feedbackLabel = new JLabel("You have " + MAX_ATTEMPTS + " attempts.", SwingConstants.CENTER);
        attemptsLabel = new JLabel("Attempts used: 0", SwingConstants.CENTER);
        scoreLabel = new JLabel("Score: 0 win(s) out of 0 round(s)", SwingConstants.CENTER);
        timerLabel = new JLabel("Time left: 60s", SwingConstants.CENTER);

        centerPanel.add(guessField);
        centerPanel.add(guessButton);
        centerPanel.add(feedbackLabel);
        centerPanel.add(attemptsLabel);
        centerPanel.add(scoreLabel);
        centerPanel.add(timerLabel);
        add(centerPanel, BorderLayout.CENTER);

        // Bottom Panel
        JPanel bottomPanel = new JPanel(new BorderLayout(10, 10));
        JPanel buttonPanel = new JPanel();
        playAgainButton = new JButton("Play Again");
        playAgainButton.setEnabled(false);
        resetButton = new JButton("Reset Game");
        exitButton = new JButton("Exit");

        buttonPanel.add(playAgainButton);
        buttonPanel.add(resetButton);
        buttonPanel.add(exitButton);
        bottomPanel.add(buttonPanel, BorderLayout.NORTH);

        logArea = new JTextArea(8, 50);
        logArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(logArea);
        bottomPanel.add(scrollPane, BorderLayout.CENTER);

        add(bottomPanel, BorderLayout.SOUTH);

        // Button Actions
        guessButton.addActionListener(new GuessButtonListener());
        playAgainButton.addActionListener(e -> startNewRound());
        resetButton.addActionListener(e -> {
            roundsWon = 0;
            roundsPlayed = 0;
            resetGame();
        });
        exitButton.addActionListener(e -> System.exit(0));

        startNewRound();
    }

    private void startNewRound() {
        Random random = new Random();
        targetNumber = random.nextInt(UPPER_BOUND - LOWER_BOUND + 1) + LOWER_BOUND;
        attempts = 0;
        timeLeft = 60;

        guessField.setText("");
        guessField.setEnabled(true);
        guessButton.setEnabled(true);
        playAgainButton.setEnabled(false);

        feedbackLabel.setText("You have " + MAX_ATTEMPTS + " attempts.");
        attemptsLabel.setText("Attempts used: 0");
        timerLabel.setText("Time left: 60s");
        logArea.setText("New round began. Good luck!\n");

        if (countdownTimer != null && countdownTimer.isRunning()) {
            countdownTimer.stop();
        }

        countdownTimer = new Timer(1000, e -> {
            timeLeft--;
            timerLabel.setText("Time left: " + timeLeft + "s");
            if (timeLeft <= 0) {
                countdownTimer.stop();
                feedbackLabel.setText("Time's up! The correct number was: " + targetNumber);
                logArea.append("Time ran out. You lost this round.\n");
                endRound(false);
            }
        });
        countdownTimer.start();
    }

    private void resetGame() {
        scoreLabel.setText("Score: " + roundsWon + " win(s) out of " + roundsPlayed + " round(s)");
        startNewRound();
    }

    private void endRound(boolean won) {
        roundsPlayed++;
        if (won) roundsWon++;

        guessField.setEnabled(false);
        guessButton.setEnabled(false);
        playAgainButton.setEnabled(true);

        if (countdownTimer != null) countdownTimer.stop();

        scoreLabel.setText("Score: " + roundsWon + " win(s) out of " + roundsPlayed + " round(s)");
    }

    private class GuessButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String input = guessField.getText().trim();
            try {
                int guess = Integer.parseInt(input);
                attempts++;
                attemptsLabel.setText("Attempts used: " + attempts);

                if (guess == targetNumber) {
                    feedbackLabel.setText("Correct! Well done!");
                    logArea.append("Correct guess!\n");
                    endRound(true);
                } else if (guess < targetNumber) {
                    feedbackLabel.setText("too low!");
                    logArea.append("too low.\n");
                } else {
                    feedbackLabel.setText("Too high!");
                    logArea.append("too high.\n");
                }

                if (attempts >= MAX_ATTEMPTS && guess != targetNumber) {
                    feedbackLabel.setText("No more attempts! The correct number was: " + targetNumber);
                    logArea.append("You lost round.\n");
                    endRound(false);
                }
            } 
            catch (NumberFormatException ex) {
                feedbackLabel.setText("Enter a valid number.");
            }
        }
    }

    public static void main(String[] args) 
    {
        SwingUtilities.invokeLater(() -> 
        {
            NumberGuessingGameGUI game = new NumberGuessingGameGUI();
            game.setVisible(true);
        });
    }
}
