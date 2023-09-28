package org.mrprogre.utils;

import org.mrprogre.gui.Gui;

import javax.swing.*;
import java.awt.*;

public class Icons {
    public static final ImageIcon LOGO_ICON = new ImageIcon(Toolkit.getDefaultToolkit()
            .createImage(Gui.class.getResource("/icons/logo.png")));
    public static final ImageIcon EXIT_BUTTON_ICON = new ImageIcon(Toolkit.getDefaultToolkit()
            .createImage(Gui.class.getResource("/icons/exit.png")));
    public static final ImageIcon WHEN_MOUSE_ON_EXIT_BUTTON_ICON = new ImageIcon(Toolkit.getDefaultToolkit()
            .createImage(Gui.class.getResource("/icons/exit2.png")));
}
