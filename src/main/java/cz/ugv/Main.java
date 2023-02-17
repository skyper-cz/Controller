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
    public static EmbeddedMediaPlayerComponent mediaPlayerComponent = new EmbeddedMediaPlayerComponent();
    public static MediaPlayerFactory mediaPlayerFactory = new MediaPlayerFactory();

    public static String ipina = "";
    public static String port = "";
    public static String portvidea = "";
    public static JButton potvrzeni = new JButton("Potvrdit");
    public static JTextField vlozenaipadresa = new JTextField("Zde vložte adresu");
    public static JTextField vlozenyport = new JTextField("Zde vložte port");
    public static JTextField vlozenyportvidea = new JTextField("Zde vložte port videa");

    public static void main(String[] args) {

        fr.setBounds(300, 150, 800, 600);
        fr.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        fr.setLayout(null);
        fr.getContentPane().setBackground(Color.gray);
        fr.setResizable(true);

        vlozenaipadresa.setBounds(250, 190, 300, 50);
        vlozenaipadresa.setVisible(true);
        fr.add(vlozenaipadresa);

        vlozenyport.setBounds(250, 250, 300, 50);
        vlozenyport.setVisible(true);
        fr.add(vlozenyport);

        vlozenyportvidea.setBounds(250, 310, 300, 50);
        vlozenyportvidea.setVisible(true);
        fr.add(vlozenyportvidea);

        potvrzeni.setBounds(250, 370, 300, 50);
        potvrzeni.setVisible(true);
        potvrzeni.addActionListener(Main::Potvrzeni);
        fr.add(potvrzeni);


        fr.addKeyListener(new KeyListener() {
                              @Override
                              public void keyTyped(KeyEvent e) {
                                  System.out.println("Key Typed: " + e.getKeyChar());
                                  try {
                                      Poslat(String.valueOf(e.getKeyChar()));
                                  } catch (SocketException | UnknownHostException ex) {
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

    public static void Poslat(String stisknuto) throws SocketException, UnknownHostException {
        Communication(ipina, Integer.parseInt(port), stisknuto);
    }

    public static void Potvrzeni(ActionEvent e) {
        ipina = vlozenaipadresa.getText();
        port = vlozenyport.getText();
        portvidea = vlozenyportvidea.getText();

        vlozenaipadresa.setVisible(false);
        vlozenyport.setVisible(false);
        vlozenyportvidea.setVisible(false);
        potvrzeni.setVisible(false);

        mediaPlayerComponent.setBounds(0, 0, fr.getWidth(), fr.getHeight());
        fr.add(mediaPlayerComponent);

        fr.requestFocus();

        String adresa = "https://" + ipina + ":" + portvidea;

        mediaPlayerComponent.mediaPlayer().media().play(adresa);
    }

}
