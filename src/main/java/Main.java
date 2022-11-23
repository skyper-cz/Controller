import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Main {

    public static JFrame fr = new JFrame("Ovládání");
    public static boolean stisknuto = false;
    public static boolean[] kery = new boolean[]{false, false, false, false, false, false, false, false};

    public static void main(String[] args) {
        fr.setBounds(500, 500, 1630, 600);
        fr.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        fr.setLayout(null);
        fr.getContentPane().setBackground(Color.gray);
        fr.setVisible(true);
        fr.setResizable(false);

        fr.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();

                if (keyCode == KeyEvent.VK_Q) {
                    leftForward();
                }
                else if (keyCode == KeyEvent.VK_W) {
                    forward();
                }
                else if (keyCode == KeyEvent.VK_E) {
                    rightForward();
                }
                else if (keyCode == KeyEvent.VK_A) {
                    left();
                }
                else if (keyCode == KeyEvent.VK_S) {
                    back();
                }
                else if (keyCode == KeyEvent.VK_D) {
                    right();
                }

                if (keyCode == KeyEvent.VK_UP) {
                    System.out.println("Up Arrrow-Key is pressed!");
                }
                else if (keyCode == KeyEvent.VK_DOWN) {
                    System.out.println("Down Arrrow-Key is pressed!");
                }
                else if (keyCode == KeyEvent.VK_LEFT) {
                    System.out.println("Left Arrrow-Key is pressed!");
                }
                else if (keyCode == KeyEvent.VK_RIGHT) {
                    System.out.println("Right Arrrow-Key is pressed!");
                }
            }
        });

        fr.update(fr.getGraphics());
    }

    public static void leftForward(){

    }
    public static void forward(){

    }
    public static void rightForward(){

    }
    public static void left(){

    }
    public static void right(){

    }
    public static void back(){

    }
}
