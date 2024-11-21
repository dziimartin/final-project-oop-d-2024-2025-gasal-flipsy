package com.game.ui;

import com.game.core.GameTimer;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class GamePanel implements Panel {
    private GameTimer gameTimer; 
    private JLabel timerLabel; 
    private Thread timerUpdaterThread; // Thread untuk update GUI
    private boolean running; // Flag untuk menghentikan thread

    public GamePanel() {
        this.gameTimer = new GameTimer(); 
    }

    @Override
    public JPanel createPanel() {
        // Panel utama dengan BorderLayout
        JPanel gamePanel = new JPanel();
        gamePanel.setLayout(new BorderLayout());

        // Panel atas untuk timer
        JPanel topPanel = new JPanel();
        timerLabel = new JLabel("Time: 00:00"); 
        topPanel.add(timerLabel);
        gamePanel.add(topPanel, BorderLayout.NORTH);

        JPanel cardGrid = new JPanel();
        cardGrid.setLayout(new GridLayout(4, 4, 10, 10)); // 4x4 grid layout dengan gap 10px

        // Add placeholder cards (as buttons)
        for (int i = 1; i <= 16; i++) {
            JButton cardButton = new JButton("?");
            cardButton.setFont(new Font("Arial", Font.BOLD, 20));
            cardButton.addActionListener(this::cardButtonClicked);
            cardGrid.add(cardButton);
        }

        gamePanel.add(cardGrid, BorderLayout.CENTER);

        return gamePanel;
    }

    public void startGame() {
        gameTimer.start();  
        startTimerUpdater(); 
    }

    public void startTimerUpdater() {
        // Menginisialisasi flag dan memulai thread updater
        running = true;
        timerUpdaterThread = new Thread(() -> {
            while (running && !Thread.currentThread().isInterrupted()) { 
                SwingUtilities.invokeLater(() -> {
                    // Update label timer di Swing thread
                    int minutes = gameTimer.getTimeInSeconds() / 60;
                    int seconds = gameTimer.getTimeInSeconds() % 60;
                    String formattedTime = String.format("%02d:%02d", minutes, seconds);
                    timerLabel.setText("Time: " + formattedTime); 
                });
                try {
                    Thread.sleep(1000); 
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        });
        timerUpdaterThread.start();
    }

    private void cardButtonClicked(ActionEvent e) {
        // Handle card button click event here
    }
}
