package org.mrprogre.utils;

import com.formdev.flatlaf.intellijthemes.FlatCobalt2IJTheme;
import lombok.experimental.UtilityClass;
import org.mrprogre.gui.Gui;

import javax.swing.*;
import java.awt.*;

@UtilityClass
public class Common {

    public static void createGui() {
        // Theme
        UIManager.put("Component.arc", 10);
        UIManager.put("ProgressBar.arc", 6);
        UIManager.put("Button.arc", 8);
        UIManager.put("TextField.background", Color.GRAY);
        UIManager.put("TextField.foreground", Color.BLACK);
        FlatCobalt2IJTheme.setup();

        // Create frame
        Gui gui = new Gui();

        // Movable
        Runnable runnable = () -> {
            FrameDragListener frameDragListener = new FrameDragListener(gui);
            gui.addMouseListener(frameDragListener);
            gui.addMouseMotionListener(frameDragListener);
        };
        SwingUtilities.invokeLater(runnable);
    }

}
