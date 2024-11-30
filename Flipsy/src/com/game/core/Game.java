package com.game.core;

public class Game {
    private int currentLevel;     // Level permainan saat ini
    private int score;            // Skor pemain
    private int mistakes;         // Jumlah kesalahan
    private final GameTimer timer;     // Timer permainan
    private Runnable onCardMatch; // Callback untuk kartu cocok

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

    // Menambahkan skor ketika kartu cocok
    public void increaseScore(int points) {
        score += points;
        if (onCardMatch != null) {
            onCardMatch.run(); // Jalankan callback
        }
    }

    // Mengatur listener untuk kartu cocok
    public void setOnCardMatchListener(Runnable listener) {
        this.onCardMatch = listener;
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
