package com.game.ui;

import com.game.core.GameTimer;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class GamePanel implements Panel {
    private GameTimer gameTimer; // Timer untuk permainan
    private JLabel timerLabel; // Label untuk menampilkan waktu
    private Thread timerUpdaterThread; // Thread untuk update GUI
    private boolean running; // Flag untuk menghentikan thread

    public GamePanel() {
        this.gameTimer = new GameTimer(); // Inisialisasi GameTimer
    }

    @Override
    public JPanel createPanel() {
        // Panel utama dengan BorderLayout
        JPanel gamePanel = new JPanel();
        gamePanel.setLayout(new BorderLayout());

        // Panel atas untuk timer
        JPanel topPanel = new JPanel();
        timerLabel = new JLabel("Time: 00:00"); // Inisialisasi timer dengan format 00:00
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
        gameTimer.start();  // Start the timer in GameTimer
        startTimerUpdater(); // Start the timer update thread
    }

    public void stopGame() {
        stopTimer();
        JOptionPane.showMessageDialog(null, "Permainan Selesai! Waktu akhir: " + getFinalTime() + " detik");
    }

    private void startTimerUpdater() {
        // Menginisialisasi flag dan memulai thread updater
        running = true;
        timerUpdaterThread = new Thread(() -> {
            while (running && !Thread.currentThread().isInterrupted()) { // Menambahkan pengecekan interrupt
                SwingUtilities.invokeLater(() -> {
                    // Update label timer di Swing thread
                    int minutes = gameTimer.getTimeInSeconds() / 60;
                    int seconds = gameTimer.getTimeInSeconds() % 60;
                    String formattedTime = String.format("%02d:%02d", minutes, seconds);
                    timerLabel.setText("Time: " + formattedTime); // Update label dengan format mm:ss
                });
                try {
                    Thread.sleep(1000); // Update setiap detik
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        });
        timerUpdaterThread.start();
    }

    public void stopTimer() {
        if (gameTimer != null) {
            gameTimer.stop();  // Stop the game timer
        }
        running = false; // Set running flag to false
        if (timerUpdaterThread != null) {
            timerUpdaterThread.interrupt();  // Interrupt the timer updater thread
        }
    }

    public int getFinalTime() {
        return gameTimer.getTimeInSeconds(); // Return final time in seconds
    }

    private void cardButtonClicked(ActionEvent e) {
        // Handle card button click event here
    }
}
