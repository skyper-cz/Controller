package cz.ugv;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;

import static cz.ugv.Komunikace.Communication;

public class Main {

    public static JFrame fr = new JFrame("Ovládání");
    public static JPanel hlavni = new JPanel();
    public static JPanel otocna = new JPanel();

    public static String cesta = "http://10.20.12.111:80";
    public static boolean stisknuto = false;
    public static boolean[] kery = new boolean[]{false, false, false, false, false, false, false, false};

    public static void main(String[] args) {
        fr.setBounds(0, 0, 1210, 600);
        fr.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        fr.setLayout(null);
        fr.getContentPane().setBackground(Color.gray);
        fr.setVisible(true);
        fr.setResizable(true);

        hlavni.setBounds(0, 0, 600, 600);
        hlavni.setVisible(true);
        fr.add(hlavni);

        otocna.setBounds(610, 0, 600, 600);
        otocna.setVisible(true);
        fr.add(otocna);
        fr.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();


                if (keyCode == KeyEvent.VK_W) {
                    try {
                        forward();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
                else if (keyCode == KeyEvent.VK_A) {
                    try {
                        left();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
                else if (keyCode == KeyEvent.VK_S) {
                    try {
                        back();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
                else if (keyCode == KeyEvent.VK_D) {
                    try {
                        right();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }

                if (keyCode == KeyEvent.VK_UP) {
                    try {
                        nahoru();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
                else if (keyCode == KeyEvent.VK_DOWN) {
                    try {
                        dolu();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
                else if (keyCode == KeyEvent.VK_LEFT) {
                    try {
                        doleva();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
                else if (keyCode == KeyEvent.VK_RIGHT) {
                    try {
                        doprava();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });

        fr.update(fr.getGraphics());
    }
    public static void forward() throws IOException {
        Communication(cesta + "/rizeni", "W");
    }
    public static void left() throws IOException {
        Communication(cesta + "/rizeni", "A");
    }
    public static void right() throws IOException {
        Communication(cesta + "/rizeni", "D");
    }
    public static void back() throws IOException {
        Communication(cesta + "/rizeni", "S");
    }

    public static void nahoru() throws IOException {
        Communication(cesta + "/kamera", "W");
    }
    public static void dolu() throws IOException {
        Communication(cesta + "/kamera", "S");
    }
    public static void doleva() throws IOException {
        Communication(cesta + "/kamera", "A");
    }
    public static void doprava() throws IOException {
        Communication(cesta + "/kamera", "D");
    }
}
