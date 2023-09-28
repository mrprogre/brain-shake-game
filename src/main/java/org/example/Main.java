package org.example;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        createGui();
    }

    public static void createGui() {
        Gui gui = new Gui();
        Runnable runnable = () -> {
            FrameDragListener frameDragListener = new FrameDragListener(gui);
            gui.addMouseListener(frameDragListener);
            gui.addMouseMotionListener(frameDragListener);
        };
        SwingUtilities.invokeLater(runnable);
    }
}