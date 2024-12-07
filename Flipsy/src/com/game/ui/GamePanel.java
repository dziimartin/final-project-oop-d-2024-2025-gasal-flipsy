//test
package com.game.ui;

import com.game.core.Board;
import com.game.core.GameTimer;

import javax.swing.*;
import java.awt.*;

public class GamePanel implements Panel {
    private JPanel boardPanel;
    private JLabel timerLabel;
    private GameTimer gameTimer;
    private Thread timerUpdaterThread;
    private final Board board;

    public GamePanel() {
        this.gameTimer = new GameTimer();
        this.board = new Board();
    }

    @Override
    public JPanel createPanel() {
        // Panel utama dengan layout vertikal
        JPanel gamePanel = new JPanel();
        gamePanel.setLayout(new BoxLayout(gamePanel, BoxLayout.Y_AXIS));

        // Panel atas untuk timer
        JPanel topPanel = new JPanel();
        timerLabel = new JLabel("Time: 00:00");
        topPanel.add(timerLabel);
        gamePanel.add(topPanel);

        // Panel grid untuk kartu
        boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(4, 4, 5, 5));
        boardPanel.setPreferredSize(new Dimension(450, 600));
        boardPanel.setMaximumSize(new Dimension(450, 600));
        gamePanel.add(boardPanel);

        // Pusatkan grid
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 20));
        centerPanel.add(boardPanel);
        gamePanel.add(centerPanel);

        board.initializeCards(boardPanel);
        return gamePanel;
    }

    public void startGame() {
        board.initializeCards(boardPanel);
        gameTimer.start();
        startTimerUpdater();
    }

    private void startTimerUpdater() {
        timerUpdaterThread = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                SwingUtilities.invokeLater(() -> {
                    int minutes = gameTimer.getTimeInSeconds() / 60;
                    int seconds = gameTimer.getTimeInSeconds() % 60;
                    timerLabel.setText(String.format("Time: %02d:%02d", minutes, seconds));
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
}
