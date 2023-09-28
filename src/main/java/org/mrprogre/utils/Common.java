package org.mrprogre.utils;

import com.formdev.flatlaf.intellijthemes.FlatCobalt2IJTheme;
import lombok.experimental.UtilityClass;
import org.mrprogre.gui.Gui;

import javax.swing.*;

@UtilityClass
public class Common {

    public static void createGui(String guiSize) {
        // Theme
        UIManager.put("Component.arc", 10);
        UIManager.put("Button.arc", 8);
        FlatCobalt2IJTheme.setup();

        // Create frame
        Gui gui = new Gui(guiSize);

        // Movable
        Runnable runnable = () -> {
            FrameDragListener frameDragListener = new FrameDragListener(gui);
            gui.addMouseListener(frameDragListener);
            gui.addMouseMotionListener(frameDragListener);
        };
        SwingUtilities.invokeLater(runnable);
    }

}
