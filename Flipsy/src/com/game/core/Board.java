package com.game.core;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;


public class Board {
    private final ArrayList<Card> cards;
    private Card firstSelected;
    private Card secondSelected;
    private boolean isProcessing;
    private Game game; 

    public Board() {
        this.cards = new ArrayList<>();
        this.isProcessing = false;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public void initializeCards(JPanel boardPanel) {
        cards.clear();
        boardPanel.removeAll();

        // Load gambar kartu (gambar depan)
        ArrayList<ImageIcon> images = loadCardImages();
        images.addAll(images);  // Duplikasi gambar untuk pasangan kartu
        Collections.shuffle(images);

        // Hanya tampilkan 16 kartu, grid 4x4
        images = new ArrayList<>(images.subList(0, 16)); // Ambil 16 kartu pertama

        // Gambar belakang kartu
        ImageIcon backImage = new ImageIcon(getClass().getClassLoader().getResource("img/Backcard.jpg"));

        // Buat kartu
        for (ImageIcon image : images) {
            Card card = new Card(image, backImage); 
            card.setPreferredSize(new java.awt.Dimension(100, 150));
            card.addActionListener(e -> handleCardClick(card, boardPanel));
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
            "justice.png", "death.png"
        };

        for (String name : imageNames) {
            ImageIcon image = new ImageIcon(getClass().getClassLoader().getResource("img/" + name));
            if (image != null) {
                images.add(image);
            }
        }
        return images;
    }

    private void handleCardClick(Card card, JPanel boardPanel) {
        if (isProcessing || card.isFlipped() || card.isMatched()) return;

        card.flip(); // Tampilkan gambar depan

        if (firstSelected == null) {
            firstSelected = card;
        } else if (secondSelected == null) {
            secondSelected = card;
            checkMatch(boardPanel);
        }
    }

    private void checkMatch(JPanel boardPanel) {
        isProcessing = true;

        if (firstSelected.getFrontImage().equals(secondSelected.getFrontImage())) {
            // Jika kartu cocok
            firstSelected.setMatched();
            secondSelected.setMatched();
            if (game != null) {
                game.increaseScore(100); // Panggil skor setiap kali kartu cocok
            }
            
            resetSelection();
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
}
