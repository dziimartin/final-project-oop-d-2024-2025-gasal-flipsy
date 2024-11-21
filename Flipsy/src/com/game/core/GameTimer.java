package com.game.core;

import java.util.Timer;
import java.util.TimerTask;

public class GameTimer {
    private int timeInSeconds;
    private Timer timer;
    private TimerListener listener; // Listener untuk update waktu
    private boolean isPaused; // Status pause

    public GameTimer() {
        this.timeInSeconds = 0;
        this.isPaused = false;
    }

    public void start() {
        if (timer != null) {
            stop();
        }

        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                timeInSeconds++;
                System.out.println("Time: " + timeInSeconds + " seconds");
                notifyListener(); // Beri tahu listener
            }
        }, 1000, 1000);
    }

    public void stop() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    public void reset() {
        stop();
        timeInSeconds = 0;
        isPaused = false;
    }

    public void pause() {
        if (timer != null) {
            isPaused = true;
            stop();
        }
    }

    public void resume() {
        if (isPaused) {
            isPaused = false;
            start();
        }
    }

    public int getTimeInSeconds() {
        return timeInSeconds;
    }

    public String getFormattedTime() {
        int minutes = timeInSeconds / 60;
        int seconds = timeInSeconds % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }
    

    public void setTimerListener(TimerListener listener) {
        this.listener = listener;
    }

    private void notifyListener() {
        if (listener != null) {
            listener.onTimeUpdate(getFormattedTime());
        }
    }

    public interface TimerListener {
        void onTimeUpdate(String formattedTime);
    }
}
