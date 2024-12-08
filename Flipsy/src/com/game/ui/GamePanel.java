package com.game.ui;

import com.game.core.Board;
import com.game.core.Card;
import com.game.core.Game;
import com.game.core.GameTimer;
import java.awt.*;
import javax.swing.*;

public class GamePanel implements Panel {
    private JPanel boardPanel;
    private JLabel timerLabel;
    private JLabel scoreLabel;    // Label untuk menampilkan skor
    private JLabel levelLabel;    // Label untuk menampilkan level
    private GameTimer gameTimer;
    private Thread timerUpdaterThread;
    private final Board board;
    private final Game game;

    public GamePanel() {
        this.gameTimer = new GameTimer();
        this.board = new Board();
        this.game = new Game();
    }

    @Override
    public JPanel createPanel() {
        JPanel gamePanel = new JPanel();
        gamePanel.setLayout(new BoxLayout(gamePanel, BoxLayout.Y_AXIS));

        // Panel atas untuk informasi permainan
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(1, 3, 10, 0)); 
        timerLabel = new JLabel("Time: 00:00");
        scoreLabel = new JLabel("Score: 0");
        levelLabel = new JLabel("Level: 1");

        topPanel.add(timerLabel);
        topPanel.add(scoreLabel);
        topPanel.add(levelLabel);
        gamePanel.add(topPanel);

        // Panel grid untuk kartu
        boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(4, 4, 5, 5));
        boardPanel.setPreferredSize(new Dimension(450, 600));
        gamePanel.add(boardPanel);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 20));
        centerPanel.add(boardPanel);
        gamePanel.add(centerPanel);

        board.initializeCards(boardPanel);
        return gamePanel;
    }

    public void startGame() {
        game.startGame();
        board.initializeCards(boardPanel);
        updateGameStatus();
        gameTimer.start();
        startTimerUpdater();
    }

    private void updateGameStatus() {
        scoreLabel.setText("Score: " + game.getScore());
        levelLabel.setText("Level: " + game.getCurrentLevel());
    }

    private void startTimerUpdater() {
        timerUpdaterThread = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                SwingUtilities.invokeLater(() -> {
                    int minutes = gameTimer.getTimeInSeconds() / 60;
                    int seconds = gameTimer.getTimeInSeconds() % 60;
                    timerLabel.setText(String.format("Time: %02d:%02d", minutes, seconds));
                    updateGameStatus();
                });

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        timerUpdaterThread.start();
    }

    private void handleCardMatch(Card firstSelected, Card secondSelected) {
        if (firstSelected.isMatched() && secondSelected.isMatched()) {
            game.increaseScore(10); // Tambah skor setiap kali kartu cocok
            updateGameStatus();
        }
    }

    // Memastikan pemantauan kartu
    public void handleCardClick(Card card) {
        // Panggil logika kartu cocok di sini
        if (card.isMatched()) {
            handleCardMatch(card, card); // Kirim kartu yang cocok
        }
    }
}
