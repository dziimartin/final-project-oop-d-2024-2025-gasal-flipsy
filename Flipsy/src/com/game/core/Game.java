package com.game.core;

public class Game {
    private int currentLevel;         // Level permainan saat ini
    private int score;                // Skor pemain
    private int mistakes;             // Jumlah kesalahan
    private final GameTimer timer;    // Timer permainan
    private Runnable onCardMatch;     // Callback untuk kartu cocok
    private Runnable onLevelComplete; // Callback untuk level selesai

    private final int[] levelCardCounts = {16, 24, 32}; // Jumlah kartu untuk setiap level

    public Game() {
        this.currentLevel = 1;
        this.score = 0;
        this.mistakes = 0;
        this.timer = new GameTimer();
    }

    // Memulai permainan baru
    public void startGame() {
        currentLevel = 1;
        score = 0;
        mistakes = 0;
        timer.start();
    }

    // Melanjutkan ke level berikutnya
    public void nextLevel() {
        if (currentLevel < levelCardCounts.length) {
            currentLevel++;
            mistakes = 0; // Reset kesalahan
            if (onLevelComplete != null) {
                onLevelComplete.run(); // Jalankan callback level selesai
            }
        }
    }

    // Menambahkan skor ketika kartu cocok
    public void increaseScore(int points) {
        score += points;
        if (onCardMatch != null) {
            onCardMatch.run(); // Jalankan callback kartu cocok
        }

        // Jika semua kartu sudah cocok di level saat ini, naikkan level
        if (isLevelComplete()) {
            nextLevel();
        }
    }

    // Cek apakah level selesai
    public boolean isLevelComplete() {
        return score / 100 == levelCardCounts[currentLevel - 1] / 2; // Setiap pasangan memberikan 100 poin
    }

    // Get jumlah kartu untuk level saat ini
    public int getCardCountForCurrentLevel() {
        return levelCardCounts[currentLevel - 1];
    }

    // Setter untuk callback
    public void setOnCardMatch(Runnable onCardMatch) {
        this.onCardMatch = onCardMatch;
    }

    public void setOnLevelComplete(Runnable onLevelComplete) {
        this.onLevelComplete = onLevelComplete;
    }

    // Getters untuk atribut
    public int getCurrentLevel() {
        return currentLevel;
    }

    public int getScore() {
        return score;
    }

    public int getMistakes() {
        return mistakes;
    }

    public GameTimer getTimer() {
        return timer;
    }
}
