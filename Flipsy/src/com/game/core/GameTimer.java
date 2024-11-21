package com.game.core;

import java.util.Timer;
import java.util.TimerTask;

public class GameTimer {
    private int timeInSeconds; 
    private Timer timer;       

    public GameTimer() {
        this.timeInSeconds = 0; 
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
    }

    public int getTimeInSeconds() {
        return timeInSeconds;
    }
}