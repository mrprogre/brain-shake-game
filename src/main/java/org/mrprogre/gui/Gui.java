package org.mrprogre.gui;

import org.mrprogre.utils.Common;
import org.mrprogre.utils.GuiSize;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.mrprogre.utils.Icons.*;

public class Gui extends JFrame {
    private static String size;
    private static int numbersCount = 60;
    private static int frameHeight = 384;
    private static int counter;
    private int currentNumber;
    private final String find = " Find ";
    private final AtomicBoolean isHard = new AtomicBoolean(false);

    public Gui(String guiSize) {
        size = guiSize;
        counter = 0;
        currentNumber = 1;

        setIconImage(LOGO_ICON.getImage());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER));
        this.setUndecorated(true);

        switch (guiSize) {
            case "small":
                frameHeight = 188;
                numbersCount = 24;
                break;
            case "large":
                frameHeight = 710;
                numbersCount = 120;
                break;
        }

        setBounds(600, 160, 800, frameHeight);
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
        label.setFont(new Font("Tahoma", Font.PLAIN, 16));
        label.setPreferredSize(new Dimension(685, 13));
        getContentPane().add(label);

        // Hard on/off
        JCheckBox isHardCheckbox = new JCheckBox("Hard mode");
        isHardCheckbox.setFocusable(false);
        isHardCheckbox.addItemListener(e -> {
            isHard.set(!isHard.get());
            System.out.println(isHard.get());
        });
        getContentPane().add(isHardCheckbox);

        ArrayList<Integer> listOfNumbers = new ArrayList<>();
        for (int i = 1; i <= numbersCount; i++) {
            listOfNumbers.add(i);
        }
        Collections.shuffle(listOfNumbers);

        long startTime = System.currentTimeMillis();
        for (Integer n : listOfNumbers) {
            JButton jButton = new JButton(String.valueOf(n));
            jButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
            jButton.setFocusPainted(false);
            jButton.setPreferredSize(new Dimension(60, 60));

            jButton.setName(String.valueOf(listOfNumbers.get(n - 1)));
            getContentPane().add(jButton);

            jButton.addActionListener(x -> {
                if (currentNumber == Integer.parseInt(jButton.getText())) {
                    if (isHard.get()) {
                        jButton.setVisible(false);
                    } else {
                        //jButton.setEnabled(false);
                        jButton.setIcon(GIF);
                    }

                    jButton.setText(" ");
                    counter++;
                    currentNumber++;

                    label.setText(find + currentNumber);
                    if (counter == numbersCount) {
                        label.setText("");
                        dispose();
                        String message = String.format("Completed in %.2f seconds",
                                (double) (System.currentTimeMillis() - startTime) / 1000);

                        int result = JOptionPane.showConfirmDialog(this,
                                "Start again?",
                                message,
                                JOptionPane.YES_NO_OPTION,
                                JOptionPane.INFORMATION_MESSAGE, GIF);

                        if (result == JOptionPane.YES_OPTION) startGame();
                    }
                }
            });
        }

        setVisible(true);
    }

    private void startGame() {
        dispose();
        Common.createGui(size);
    }

    private JMenu createFileMenu() {
        JMenu file = new JMenu("File");

        JMenuItem restart = new JMenuItem("Restart");
        KeyStroke ctrlR = KeyStroke.getKeyStroke(KeyEvent.VK_R, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask());
        restart.setAccelerator(ctrlR);
        restart.addActionListener(x -> {
            // Remove the frame
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

    private JMenu createSizeMenu() {
        JMenu viewMenu = new JMenu("Size");
        String minSize = GuiSize.SMALL.getSize();
        String midSize = GuiSize.MIDDLE.getSize();
        String maxSize = GuiSize.LARGE.getSize();

        JCheckBoxMenuItem small = new JCheckBoxMenuItem(minSize);
        small.addActionListener(e -> {
            dispose();
            Common.createGui("small");
        });
        JCheckBoxMenuItem middle = new JCheckBoxMenuItem(midSize);
        middle.addActionListener(e -> {
            dispose();
            Common.createGui("middle");
        });
        JCheckBoxMenuItem large = new JCheckBoxMenuItem(maxSize);
        large.addActionListener(e -> {
            dispose();
            Common.createGui("large");
        });

        ButtonGroup bg = new ButtonGroup();
        bg.add(small);
        bg.add(middle);
        bg.add(large);

        if (size.equals(minSize)) {
            bg.setSelected(small.getModel(), true);
        } else if (size.equals(midSize)) {
            bg.setSelected(middle.getModel(), true);
        } else if (size.equals(maxSize)) {
            bg.setSelected(large.getModel(), true);
        }

        viewMenu.add(small);
        viewMenu.add(middle);
        viewMenu.add(large);

        return viewMenu;
    }


}
