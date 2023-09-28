package org.mrprogre.gui;

import org.mrprogre.utils.Common;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;

import static org.mrprogre.utils.Icons.*;

public class Gui extends JFrame {
    private static int numbersCount;
    private static int counter;
    private int currentNumber;

    public Gui() {
        counter = 0;
        currentNumber = 1;

        try {
            String s = JOptionPane.showInputDialog("Enter the number of blocks (max 120)");
            if (s == null || s.equals("0") || Integer.parseInt(s) > 120) {
                numbersCount = 120;
            } else {
                numbersCount = Integer.parseInt(s);
            }
        } catch (Exception e) {
            numbersCount = 120;
        }

        setName("Tune your brain in the morning friend. From 1 to " + numbersCount); // не видно
        setIconImage(LOGO_ICON.getImage());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER));
        this.setUndecorated(true);
        setResizable(false);

        JMenuBar menuBar = new JMenuBar();
        menuBar.add(createFileMenu());
        menuBar.add(Box.createHorizontalGlue());

        JButton exitBtn = new JButton(EXIT_BUTTON_ICON);
        exitBtn.setFocusable(false);
        exitBtn.setBorderPainted(false);
        exitBtn.addActionListener(x -> System.exit(0));
        menuBar.add(exitBtn);
        animation(exitBtn, EXIT_BUTTON_ICON, WHEN_MOUSE_ON_EXIT_BUTTON_ICON);

        setJMenuBar(menuBar);

        ArrayList<Integer> listOfNumbers = new ArrayList<>();
        for (int i = 1; i <= numbersCount; i++) {
            listOfNumbers.add(i);
        }
        Collections.shuffle(listOfNumbers);

        long startTime = System.currentTimeMillis();
        for (Integer n : listOfNumbers) {
            JButton jButton = new JButton(String.valueOf(n));
            jButton.setFocusPainted(false);
            jButton.setPreferredSize(new Dimension(60, 60)); // 20 * 20

            jButton.setName(String.valueOf(listOfNumbers.get(n - 1)));
            getContentPane().add(jButton);

            jButton.addActionListener(x -> {
                if (currentNumber == Integer.parseInt(jButton.getText())) {
                    jButton.setEnabled(false);
                    jButton.setText(" ");
                    counter++;
                    currentNumber++;

                    if (counter == numbersCount) {
                        long wastedTime = (System.currentTimeMillis() - startTime) / 1000;
                        String message = String.format("Completed in %d seconds", wastedTime);

                        int result = JOptionPane.showConfirmDialog(this,
                                "Start again?",
                                message,
                                JOptionPane.YES_NO_OPTION,
                                JOptionPane.INFORMATION_MESSAGE);

                        if (result == JOptionPane.YES_OPTION) startGame();
                    }
                }
            });
        }

        setBounds(600, 160, 800, 700);

        setVisible(true);
    }

    private void startGame() {
        Common.createGui();
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

}
