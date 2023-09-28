package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Gui extends JFrame {
    private static final int NUMBERS_COUNT = 3; // 120
    public static final ImageIcon LOGO_ICON = new ImageIcon(Toolkit.getDefaultToolkit()
            .createImage(Gui.class.getResource("/icons/logo.png")));
    private static int counter;
    private int currentNumber;

    public Gui() {
        counter = 0;
        currentNumber = 1;

        setName("Tune your brain in the morning friend. From 1 to " + NUMBERS_COUNT); // не видно
        setIconImage(LOGO_ICON.getImage());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER));
        this.setUndecorated(true);
        setResizable(false);

        JMenuBar menuBar = new JMenuBar();
        menuBar.add(createFileMenu());
        menuBar.add(Box.createHorizontalGlue());
        setJMenuBar(menuBar);

        ArrayList<Integer> listOfNumbers = new ArrayList<>();
        for (int i = 1; i <= NUMBERS_COUNT; i++) {
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
                    int randomR = new Random().nextInt(255);
                    int randomG = new Random().nextInt(255);
                    int randomB = new Random().nextInt(255);
                    jButton.setBackground(new Color(randomR, randomG, randomB));
                    jButton.setText(" ");
                    counter++;
                    currentNumber++;

                    if (counter == NUMBERS_COUNT) {
                        long wastedTime = (System.currentTimeMillis() - startTime) / 1000;
                        String message = String.format("Completed in %d seconds!", wastedTime);
                        JOptionPane.showMessageDialog(this, message);
                    }
                }
            });
        }

        setBounds(600, 160, 800, 700);

        setVisible(true);
    }

    private void startGame() {
        Main.createGui();
    }

    private JMenu createFileMenu() {
        JMenu file = new JMenu("File");

        JMenuItem restart = new JMenuItem("Restart");
        KeyStroke ctrlR = KeyStroke.getKeyStroke(KeyEvent.VK_R, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask());
        restart.setAccelerator(ctrlR);
        restart.addActionListener(x -> startGame());

        JMenuItem exit = new JMenuItem("Exit");
        KeyStroke ctrlQ = KeyStroke.getKeyStroke(KeyEvent.VK_Q, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask());
        exit.setAccelerator(ctrlQ);
        exit.addActionListener(x -> System.exit(0));

        file.add(restart);
        file.addSeparator();
        file.add(exit);

        return file;
    }

}
