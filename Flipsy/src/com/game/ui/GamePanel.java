package com.game.ui;

import javax.swing.*;
import java.awt.*;

public class GamePanel implements Panel {
    @Override
    public JPanel createPanel() {
        JPanel gamePanel = new JPanel();
        gamePanel.setLayout(new GridLayout(4, 4, 10, 10)); // 4x4 grid layout with gaps

        // Add placeholder cards (as buttons)
        for (int i = 1; i <= 16; i++) {
            JButton cardButton = new JButton("?");
            cardButton.setFont(new Font("Arial", Font.BOLD, 20));
            gamePanel.add(cardButton);
        }

        return gamePanel;
    }
}
