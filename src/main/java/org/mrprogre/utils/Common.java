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

    public static void showAlert(String message) {
        JOptionPane.showMessageDialog(null, message, "Warning", JOptionPane.WARNING_MESSAGE);
    }

    public static void showAlertHtml(String message) {
        JLabel label = new JLabel("<html>" + message + "</<html>");
        JOptionPane.showMessageDialog(null, label, "Warning", JOptionPane.WARNING_MESSAGE);
    }

    public static void showInfo(String message) {
        JOptionPane.showMessageDialog(null, message, "Info", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void showInfoHtml(String message) {
        JLabel label = new JLabel("<html>" + message + "</<html>");
        JOptionPane.showMessageDialog(null, label, "Info", JOptionPane.INFORMATION_MESSAGE);
    }

}
