package cz.ugv;

import uk.co.caprica.vlcj.factory.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.component.EmbeddedMediaPlayerComponent;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;

import static cz.ugv.Komunikace.Communication;

public class Main {

    public static JFrame fr = new JFrame("Ovládání");
    public static EmbeddedMediaPlayerComponent video = new EmbeddedMediaPlayerComponent();
    public static MediaPlayerFactory mediaPlayerFactory = new MediaPlayerFactory();

    public static String ipina = "";
    public static String port = "";
    public static JButton potvrzeni = new JButton("Potvrdit");
    public static JTextField vlozenaipadresa = new JTextField("Zde vložte adresu");
    public static JTextField vlozenyport = new JTextField("Zde vložte port");

    public static void main(String[] args) {

        fr.setBounds(0, 0, 1200, 600);
        fr.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        fr.setLayout(null);
        fr.getContentPane().setBackground(Color.gray);
        fr.setResizable(true);

        video.setBounds(0, 0, fr.getWidth(), fr.getHeight());
        fr.add(video);

        vlozenaipadresa.setBounds(400, 200, 300, 50);
        vlozenaipadresa.setVisible(true);
        fr.add(vlozenaipadresa);

        vlozenyport.setBounds(400, 260, 300, 50);
        vlozenyport.setVisible(true);
        fr.add(vlozenyport);

        potvrzeni.setBounds(400, 320, 300, 50);
        potvrzeni.setVisible(true);
        potvrzeni.addActionListener(Main::Potvrzeni);
        fr.add(potvrzeni);


        fr.addKeyListener(new KeyListener() {
                              @Override
                              public void keyTyped(KeyEvent e) {
                                  System.out.println("Key Typed: " + e.getKeyChar());
                                  try {
                                      Poslat(String.valueOf(e.getKeyChar()));
                                  } catch (SocketException ex) {
                                      throw new RuntimeException(ex);
                                  } catch (UnknownHostException ex) {
                                      throw new RuntimeException(ex);
                                  }
                              }

                              @Override
                              public void keyPressed(KeyEvent e) {
                                  System.out.println("Key Pressed: " + e.getKeyCode());
                              }

                              @Override
                              public void keyReleased(KeyEvent e) {
                                  System.out.println("Key Released: " + e.getKeyCode());
                              }
                          }
        );
        fr.setVisible(true);
        fr.update(fr.getGraphics());
    }

    public static void Poslat(String stisknuto)throws SocketException, UnknownHostException {
        switch (stisknuto){
            case "w":
                Communication(ipina, Integer.parseInt(port), "w");
                break;
            case "a":
                Communication(ipina, Integer.parseInt(port), "a");
                break;
            case "s":
                Communication(ipina, Integer.parseInt(port), "s");
                break;
            case "d":
                Communication(ipina, Integer.parseInt(port), "d");
                break;
        }
    }

    public static void Potvrzeni(ActionEvent e) {
        ipina = vlozenaipadresa.getText();
        port = vlozenyport.getText();

        vlozenaipadresa.setVisible(false);
        vlozenyport.setVisible(false);
        potvrzeni.setVisible(false);

        video.getMediaPlayer().playMedia("/Users/user/Desktop/plocha/Plasy-2020/Dron/DJI_0096.MP4");
        fr.requestFocus();
    }

}
