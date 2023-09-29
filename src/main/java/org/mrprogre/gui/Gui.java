package org.mrprogre.gui;

import org.mrprogre.utils.Common;
import org.mrprogre.utils.GuiSize;
import org.mrprogre.utils.MusicPlayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.mrprogre.utils.Icons.*;

public class Gui extends JFrame {
    private static String size;
    private static int numbersCount;
    private static int mistakesCount;
    private static int frameY;
    private static int frameHeight;
    private static int frameWidth;
    private static int labelWidth;
    private static int counter;
    private int currentNumber;
    private String find = "      Find ";
    private final AtomicBoolean isHard;
    private final AtomicBoolean isEnglish;

    public Gui() {
        List<String> configsFromFile = Common.getConfigsFromFile();
        size = configsFromFile.get(0);
        isHard = new AtomicBoolean(Boolean.parseBoolean(configsFromFile.get(1)));
        isEnglish = new AtomicBoolean(Boolean.parseBoolean(configsFromFile.get(2)));
        size = configsFromFile.get(0);
        counter = 0;
        currentNumber = 1;
        mistakesCount = 0;

        setIconImage(LOGO_ICON.getImage());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER));
        getContentPane().setBackground(new Color(0, 0, 0));
        this.setUndecorated(true);

        String fontName = "Segoe UI";
        Font labelFont = new Font(fontName, Font.BOLD, 22);
        Font buttonFont = new Font(fontName, Font.PLAIN, 15);

        switch (size) {
            case "baby":
                isHard.set(false);
                find = "     ";
                labelFont = new Font(fontName, Font.BOLD, 32);
                buttonFont = new Font(fontName, Font.BOLD, 28);
                frameY = 400;
                frameHeight = 270;
                frameWidth = 600;
                labelWidth = 510;
                numbersCount = 12;
                break;
            case "small":
                frameY = 400;
                frameWidth = 800;
                labelWidth = 685;
                frameHeight = 200;
                numbersCount = 24;
                break;
            case "middle":
                frameY = 280;
                frameWidth = 800;
                labelWidth = 685;
                frameHeight = 396;
                numbersCount = 60;
                break;
            case "large":
                frameY = 190;
                frameWidth = 800;
                labelWidth = 685;
                frameHeight = 722;
                numbersCount = 120;
                break;
        }

        setBounds(320, frameY, frameWidth, frameHeight);
        setResizable(false);

        JMenuBar menuBar = new JMenuBar();
        menuBar.add(createFileMenu());
        menuBar.add(createSizeMenu());
        menuBar.add(Box.createHorizontalGlue());

        JButton exitBtn = new JButton(EXIT_BUTTON_ICON);
        exitBtn.setFocusable(false);
        exitBtn.setBorderPainted(false);
        exitBtn.addActionListener(x -> System.exit(0));
        menuBar.add(exitBtn);
        animation(exitBtn, EXIT_BUTTON_ICON, WHEN_MOUSE_ON_EXIT_BUTTON_ICON);

        setJMenuBar(menuBar);

        // TOP
        JLabel label = new JLabel(find + currentNumber);
        label.setFont(labelFont);
        label.setForeground(Color.RED);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setPreferredSize(new Dimension(labelWidth, 30));
        getContentPane().add(label);

        // Hard on/off
        JCheckBox isHardCheckbox = new JCheckBox("Hard mode");
        isHardCheckbox.setSelected(isHard.get());
        if (size.equals("baby")) isHardCheckbox.setVisible(false);
        isHardCheckbox.setFocusable(false);
        isHardCheckbox.addItemListener(e -> {
                    isHard.set(!isHard.get());
                    saveState(size);
                }
        );
        getContentPane().add(isHardCheckbox);

        // English numbers voice on/off
        JCheckBox isEng = new JCheckBox("Eng");
        isEng.setSelected(isEnglish.get());
        isEng.setVisible(size.equals("baby"));
        isEng.setFocusable(false);
        isEng.addItemListener(e -> {
                    isEnglish.set(!isEnglish.get());
                    saveState(size);
                }
        );

        getContentPane().add(isEng);

        ArrayList<Integer> listOfNumbers = new ArrayList<>();
        for (int i = 1; i <= numbersCount; i++) {
            listOfNumbers.add(i);
        }
        Collections.shuffle(listOfNumbers);

        long startTime = System.currentTimeMillis();
        for (Integer n : listOfNumbers) {
            JButton jButton = new JButton(String.valueOf(n));
            jButton.setFont(buttonFont);
            jButton.setFocusPainted(false);
            if (size.equals("baby")) jButton.setPreferredSize(new Dimension(90, 90));
            else jButton.setPreferredSize(new Dimension(60, 60));

            jButton.setName(String.valueOf(listOfNumbers.get(n - 1)));
            getContentPane().add(jButton);

            jButton.addActionListener(x -> {
                int buttonText = -1;
                try {
                    if (jButton.getText() != null) buttonText = Integer.parseInt(jButton.getText());
                } catch (Exception ignored) {
                }

                if (currentNumber == buttonText) {
                    if (isHard.get()) {
                        jButton.setVisible(false);
                    }

                    if (size.equals("baby")) {
                        jButton.setIcon(animatedGifs[currentNumber - 1]);
                        String soundPath;

                        if (isEnglish.get()) {
                            soundPath = "/wav/eng/" + currentNumber + ".mp3";
                        } else {
                            soundPath = "/wav/rus/" + currentNumber + ".mp3";
                        }

                        new Thread(() -> new MusicPlayer().play(soundPath)).start();
                    } else {
                        jButton.setIcon(GIF);
                    }

                    jButton.setText("");
                    counter++;
                    currentNumber++;

                    label.setText(find + currentNumber);
                    if (counter == numbersCount) {
                        label.setText("");
                        dispose();
                        String message = String.format("Completed in %.2f seconds and %d mistakes were made",
                                (double) (System.currentTimeMillis() - startTime) / 1000,
                                mistakesCount);

                        int result = JOptionPane.showConfirmDialog(this,
                                "Start again?",
                                message,
                                JOptionPane.YES_NO_OPTION,
                                JOptionPane.INFORMATION_MESSAGE, GIF);

                        if (result == JOptionPane.YES_OPTION) startGame();
                    }
                } else {
                    mistakesCount++;
                }
            });
        }

        setVisible(true);

    }

    private void startGame() {
        dispose();
        Common.createGui();
    }

    private static void exit() {
        System.exit(0);
    }

    public static void animation(JButton exitBtn, ImageIcon off, ImageIcon on) {
        exitBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                exitBtn.setIcon(on);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                exitBtn.setIcon(off);
            }
        });
    }

    private JMenu createFileMenu() {
        JMenu file = new JMenu("File");

        JMenuItem restart = new JMenuItem("Restart");
        KeyStroke ctrlR = KeyStroke.getKeyStroke(KeyEvent.VK_R, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask());
        restart.setAccelerator(ctrlR);
        restart.addActionListener(x -> {
            dispose();
            startGame();
        });

        JMenuItem exit = new JMenuItem("Exit");
        KeyStroke ctrlQ = KeyStroke.getKeyStroke(KeyEvent.VK_Q, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask());
        exit.setAccelerator(ctrlQ);
        exit.addActionListener(x -> exit());

        file.add(restart);
        file.addSeparator();
        file.add(exit);

        return file;
    }

    private JMenu createSizeMenu() {
        JMenu viewMenu = new JMenu("Size");
        String babySize = GuiSize.BABY.getSize();
        String minSize = GuiSize.SMALL.getSize();
        String midSize = GuiSize.MIDDLE.getSize();
        String maxSize = GuiSize.LARGE.getSize();

        JCheckBoxMenuItem baby = new JCheckBoxMenuItem(babySize);
        baby.addActionListener(e -> {
            saveState("baby");
            dispose();
            Common.createGui();
        });

        JCheckBoxMenuItem small = new JCheckBoxMenuItem(minSize);
        small.addActionListener(e -> {
            saveState("small");
            dispose();
            Common.createGui();
        });

        JCheckBoxMenuItem middle = new JCheckBoxMenuItem(midSize);
        middle.addActionListener(e -> {
            saveState("middle");
            dispose();
            Common.createGui();
        });

        JCheckBoxMenuItem large = new JCheckBoxMenuItem(maxSize);
        large.addActionListener(e -> {
            saveState("large");
            dispose();
            Common.createGui();
        });

        ButtonGroup bg = new ButtonGroup();
        bg.add(baby);
        bg.add(small);
        bg.add(middle);
        bg.add(large);

        if (size.equals(minSize)) {
            bg.setSelected(small.getModel(), true);
        } else if (size.equals(midSize)) {
            bg.setSelected(middle.getModel(), true);
        } else if (size.equals(maxSize)) {
            bg.setSelected(large.getModel(), true);
        } else if (size.equals(babySize)) {
            bg.setSelected(baby.getModel(), true);
        }

        viewMenu.add(baby);
        viewMenu.add(small);
        viewMenu.add(middle);
        viewMenu.add(large);

        return viewMenu;
    }

    private void saveState(String sizeValue) {
        try {
            Files.newBufferedWriter(Paths.get(Common.CONFIG_PATH), StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            Common.showAlert(e.getMessage());
        }

        Common.writeConfig("size", sizeValue);
        Common.writeConfig("is-hard", String.valueOf(isHard.get()));
        Common.writeConfig("is-eng", String.valueOf(isEnglish.get()));
    }

}
