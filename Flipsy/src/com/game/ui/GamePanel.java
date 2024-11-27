package com.game.ui;

import com.game.core.Card;
import com.game.core.GameTimer;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

public class GamePanel implements Panel {
    private JPanel boardPanel;
    private JLabel timerLabel;
    private GameTimer gameTimer;
    private Thread timerUpdaterThread;
    private ArrayList<Card> cards;
    private Card firstSelected;
    private Card secondSelected;
    private boolean isProcessing;

    public GamePanel() {
        this.gameTimer = new GameTimer();
        this.cards = new ArrayList<>();
        this.isProcessing = false;
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
        boardPanel.setLayout(new GridLayout(4, 4, 5, 5)); // 4x4 grid
        gamePanel.add(boardPanel);

        initializeCards();
        return gamePanel;
    }

    public void startGame() {
        initializeCards();
        gameTimer.start();
        startTimerUpdater();
    }

    private void initializeCards() {
        cards.clear();
        boardPanel.removeAll();
        
        // Load gambar kartu (gambar depan)
        ArrayList<ImageIcon> images = loadCardImages();
        images.addAll(images);  // Duplikasi gambar untuk pasangan kartu
        Collections.shuffle(images);
        
        // Hanya tampilkan 16 kartu, grid 4x4
        images = new ArrayList<>(images.subList(0, 16)); // Ambil 16 kartu pertama
        
        // Gambar belakang kartu (gunakan pendekatan dinamis)
        ImageIcon backImage = new ImageIcon(getClass().getClassLoader().getResource("img/Backcard.jpg"));
        if (backImage == null) {
            System.err.println("Back card image is missing. Cannot initialize the game.");
            return;
        }
        
        // Buat kartu
        for (ImageIcon image : images) {
            Card card = new Card(image, backImage);  // Card menerima frontImage (gambar depan) dan backImage (gambar belakang)
            card.setPreferredSize(new Dimension(100, 100)); // Ukuran tetap untuk kartu
            card.addActionListener(e -> handleCardClick(card));
            cards.add(card);
            boardPanel.add(card);
        }
        
        boardPanel.revalidate();
        boardPanel.repaint();
    }    
    
    private ArrayList<ImageIcon> loadCardImages() {
        ArrayList<ImageIcon> images = new ArrayList<>();
        String[] imageNames = {
            "the-emperor.png", "the-hierophant.png", "the-chariot.png",
            "strength.png", "the-hermit.png", "wheel-of-fortune.png",
            "justice.png", "death.png", "temperance.png", "devil.png",
            "the-tower.png", "the-star.png", "the-moon.png",
            "the-sun.png", "judgement.png", "the-world.png"
        };
    
        for (String name : imageNames) {
            ImageIcon image = new ImageIcon(getClass().getClassLoader().getResource("img/" + name));
            if (image != null) {
                images.add(image);
            } else {
                System.err.println("Image not found: img/" + name);
            }
        }
        return images;
    }    

    private void handleCardClick(Card card) {
        if (isProcessing || card.isFlipped() || card.isMatched()) return;

        card.flip(); // Tampilkan gambar depan

        if (firstSelected == null) {
            firstSelected = card;
        } else if (secondSelected == null) {
            secondSelected = card;
            checkMatch();
        }
    }

    private void checkMatch() {
        isProcessing = true;
    
        if (firstSelected.getFrontImage().equals(secondSelected.getFrontImage())) {
            // Jika kartu cocok
            firstSelected.setMatched();
            secondSelected.setMatched();
            resetSelection(); // Reset pilihan setelah mencocokkan
        } else {
            // Jika kartu tidak cocok, beri sedikit waktu sebelum membalik
            Timer timer = new Timer(500, e -> {
                firstSelected.flip();
                secondSelected.flip();
                resetSelection();
            });
            timer.setRepeats(false);
            timer.start();
        }
    }
    
    private void resetSelection() {
        firstSelected = null;
        secondSelected = null;
        isProcessing = false;
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
