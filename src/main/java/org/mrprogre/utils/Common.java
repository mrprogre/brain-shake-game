package org.mrprogre.utils;

import com.formdev.flatlaf.intellijthemes.FlatCobalt2IJTheme;
import lombok.experimental.UtilityClass;
import org.mrprogre.gui.Gui;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

@UtilityClass
public class Common {
    public static final String DIRECTORY_PATH = System.getProperty("user.home") +
            File.separator + "brain-shake" + File.separator;
    public static final String CONFIG_PATH = DIRECTORY_PATH + "config.txt";

    public static void createFiles() {
        File mainDirectory = new File(DIRECTORY_PATH);
        if (!mainDirectory.exists()) mainDirectory.mkdirs();

        File configExists = new File(CONFIG_PATH);
        if (!configExists.exists()) {
            copyFiles(Common.class.getResource("/config.txt"), CONFIG_PATH);
        }
    }

    public static void createGui() {
        // Theme
        UIManager.put("Component.arc", 10);
        UIManager.put("Button.arc", 8);
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

    public static List<String> getConfigsFromFile() {
        List<String> configs = new LinkedList<>();
        try {
            List<String> rows = Files.readAllLines(Paths.get(CONFIG_PATH));
            for (String s : rows) {
                if (s.startsWith("size="))
                    configs.add(s.replace("size=", ""));
                if (s.startsWith("is-hard="))
                    configs.add(s.replace("is-hard=", ""));
                if (s.startsWith("is-eng="))
                    configs.add(s.replace("is-eng=", ""));
                if (s.startsWith("x="))
                    configs.add(s.replace("x=", ""));
                if (s.startsWith("y="))
                    configs.add(s.replace("y=", ""));
            }
        } catch (IOException e) {
            showAlert(e.getMessage());
        }
        return configs;
    }

    public void copyFiles(URL p_file, String copy_to) {
        File copied = new File(copy_to);
        try (InputStream in = p_file.openStream();
             OutputStream out = new BufferedOutputStream(Files.newOutputStream(copied.toPath()))) {
            byte[] buffer = new byte[1024];
            int lengthRead;
            while ((lengthRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, lengthRead);
                out.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showAlert(String message) {
        JOptionPane.showMessageDialog(null, message, "Warning", JOptionPane.WARNING_MESSAGE);
    }

    public static void writeConfig(String key, String value) {
        try (OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(CONFIG_PATH, true), StandardCharsets.UTF_8)) {
            switch (key) {
                case "size": {
                    String text = "size=" + value + "\n";
                    writer.write(text);
                    writer.flush();
                    break;
                }
                case "is-hard": {
                    String text = "is-hard=" + value + "\n";
                    writer.write(text);
                    writer.flush();
                    break;
                }
                case "is-eng": {
                    String text = "is-eng=" + value + "\n";
                    writer.write(text);
                    writer.flush();
                    break;
                }
                case "x": {
                    String text = "x=" + value + "\n";
                    writer.write(text);
                    writer.flush();
                    break;
                }
                case "y": {
                    String text = "y=" + value + "\n";
                    writer.write(text);
                    writer.flush();
                    break;
                }
            }
        } catch (IOException e) {
            showAlert(e.getMessage());
        }
    }

    public static void openPage(String url) {
        if (url != null && !url.equals("no data found")) {
            url = url.replaceAll(("https://|http://"), "");

            URI uri = null;
            try {
                uri = new URI("https://" + url);
            } catch (URISyntaxException ex) {
                ex.printStackTrace();
            }
            Desktop desktop = Desktop.getDesktop();
            assert uri != null;
            try {
                desktop.browse(uri);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

}
