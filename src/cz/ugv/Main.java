package cz.ugv;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.web.WebView;
import javafx.embed.swing.JFXPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;

import static cz.ugv.Komunikace.Communication;

public class Main {

    public static JFrame fr = new JFrame("Ovládání");
    public static String ipina = "";
    public static String port = "";
    public static String portvidea = "";
    public static boolean cesta = false;
    public static JLabel rezimSpusteni = new JLabel("Chcete otevřít v externě?");
    public static JLabel varovani = new JLabel("VAROVÁNÍ! v současné verzi nelze video spustit interně");
    public static JButton rezim = new JButton("NE");
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

        rezimSpusteni.setBounds(590, 10, 200, 50);
        rezimSpusteni.setVisible(true);
        fr.add(rezimSpusteni);

        varovani.setBounds(440, 50, 500, 50);
        varovani.setVisible(true);
        fr.add(varovani);

        rezim.setBounds(745, 10, 50, 50);
        rezim.setForeground(Color.RED);
        rezim.setVisible(true);
        rezim.addActionListener(Main::Rezim);
        fr.add(rezim);


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

    public static void Rezim(ActionEvent e) {
        cesta = !cesta;

        if (cesta) {
            rezim.setForeground(Color.GREEN);
            rezim.setText("ANO");
        } else {
            rezim.setForeground(Color.RED);
            rezim.setText("NE");
        }
        fr.update(fr.getGraphics());
    }

    public static void Potvrzeni(ActionEvent e) {
        ipina = vlozenaipadresa.getText();
        port = vlozenyport.getText();
        portvidea = vlozenyportvidea.getText();

        vlozenaipadresa.setVisible(false);
        vlozenyport.setVisible(false);
        vlozenyportvidea.setVisible(false);
        potvrzeni.setVisible(false);
        rezimSpusteni.setVisible(false);
        rezim.setVisible(false);

        String adresa = "http://" + ipina + ":" + portvidea;

        if (!cesta) {

            // initialize JavaFX toolkit
            Platform.startup(() -> {
            });

            // create a JFXPanel and add it to th JFrame
            JFXPanel webviewPanel = new JFXPanel();
            fr.add(webviewPanel);

            // create a WebView and set it as the scene for the JFXPanel
            WebView webview = new WebView();
            webviewPanel.setScene(new Scene(webview));

            // load the adresa into the WebView
            webview.getEngine().load(adresa);

            // set focus back to the JFrame
        } else {
            String osName = System.getProperty("os.name").toLowerCase();
            if (osName.contains("win")) {
                // Windows
                try {
                    String vlcPath = "C:\\Program Files\\VideoLAN\\VLC\\vlc.exe"; // Replace with your VLC installation path
                    String command = vlcPath + " " + adresa;
                    Process p = Runtime.getRuntime().exec(command);
                } catch (IOException ee) {
                    ee.printStackTrace();
                }
            } else if (osName.contains("mac")) {
                // Mac OS X
                try {
                    String vlcPath = "/Applications/VLC.app/Contents/MacOS/VLC"; // Replace with your VLC installation path
                    String command = vlcPath + " " + adresa;
                    Process p = Runtime.getRuntime().exec(command);
                } catch (IOException ee) {
                    ee.printStackTrace();
                }
            } else if (osName.contains("nix") || osName.contains("nux") || osName.contains("aix")) {
                // Linux/Unix
                try {
                    String command = "vlc " + adresa;
                    Process p = Runtime.getRuntime().exec(command);
                } catch (IOException ee) {
                    ee.printStackTrace();
                }
            } else {
                System.out.println("Unsupported operating system: " + osName);
            }
            fr.setSize(150,25);
        }
        fr.requestFocus();
    }
}
