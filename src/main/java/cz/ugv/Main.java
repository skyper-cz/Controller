package cz.ugv;

import uk.co.caprica.vlcj.factory.MediaPlayerFactory;
import uk.co.caprica.vlcj.factory.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.component.EmbeddedMediaPlayerComponent;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

import static cz.ugv.Komunikace.Communication;

public class Main {

    public static JFrame fr = new JFrame("Ovládání");
    public static JPanel hlavni = new JPanel();
    public static JPanel otocna = new JPanel();
    public static String ipina = "";
    public static String port = "";
    public static JButton potvrzeni = new JButton("Potvrdit");
    public static JTextField vlozenaipadresa = new JTextField("Zde vložte adresu");
    public static JTextField vlozenyport = new JTextField("Zde vložte port");

    //public static EmbeddedMediaPlayerComponent mediaPlayerComponent = new EmbeddedMediaPlayerComponent();
    public static void main(String[] args) {

        fr.setBounds(0, 0, 1210, 600);
        fr.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        fr.setLayout(null);
        fr.getContentPane().setBackground(Color.gray);
        fr.setVisible(true);
        fr.setResizable(true);

       /** fr.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                mediaPlayerComponent.release();
                System.exit(0);
            }
        });
        **/

        hlavni.setBounds(0, 0, 600, 600);
        hlavni.setVisible(false);
        fr.add(hlavni);

        //mediaPlayerComponent = new EmbeddedMediaPlayerComponent();
        //fr.setContentPane(mediaPlayerComponent);
        //mediaPlayerComponent.mediaPlayer().media().play("https://www.youtube.com/watch?v=JK_iRULWtDc");

        otocna.setBounds(610, 0, 600, 600);
        otocna.setVisible(false);
        fr.add(otocna);

        vlozenaipadresa.setBounds(400,200, 300, 50);
        vlozenaipadresa.setVisible(true);
        fr.add(vlozenaipadresa);

        vlozenyport.setBounds(400,260, 300, 50);
        vlozenyport.setVisible(true);
        fr.add(vlozenyport);

        potvrzeni.setBounds(400,320, 300, 50);
        potvrzeni.setVisible(true);
        potvrzeni.addActionListener(Main::Potvrzeni);
        fr.add(potvrzeni);


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
        Communication(ipina + ":" + port + "/rizeni", "W");
    }
    public static void left() throws IOException {
        Communication(ipina + ":" + port + "/rizeni", "A");
    }
    public static void right() throws IOException {
        Communication(ipina + ":" + port + "/rizeni", "D");
    }
    public static void back() throws IOException {
        Communication(ipina + ":" + port + "/rizeni", "S");
    }

    public static void nahoru() throws IOException {
        Communication(ipina + ":" + port + "/kamera", "W");
    }
    public static void dolu() throws IOException {
        Communication(ipina + ":" + port + "/kamera", "S");
    }
    public static void doleva() throws IOException {
        Communication(ipina + ":" + port + "/kamera", "A");
    }
    public static void doprava() throws IOException {
        Communication(ipina + ":" + port + "/kamera", "D");
    }

    public static void Potvrzeni(ActionEvent e){
        ipina = String.valueOf(vlozenaipadresa);
        port = String.valueOf(vlozenyport);

        vlozenaipadresa.setVisible(false);
        vlozenyport.setVisible(false);
        potvrzeni.setVisible(false);

        hlavni.setVisible(true);
        otocna.setVisible(true);
    }

}
