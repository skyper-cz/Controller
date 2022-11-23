import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.Border;
import com.sun.jna.platform.WindowUtils;
import uk.co.caprica.vlcj.media.Media;
import uk.co.caprica.vlcj.media.MediaEventAdapter;
import uk.co.caprica.vlcj.media.MediaParsedStatus;
import uk.co.caprica.vlcj.media.TrackInfo;
import uk.co.caprica.vlcj.player.base.ChapterDescription;
import uk.co.caprica.vlcj.player.base.Equalizer;
import uk.co.caprica.vlcj.player.base.Logo;
import uk.co.caprica.vlcj.player.base.LogoPosition;
import uk.co.caprica.vlcj.player.base.Marquee;
import uk.co.caprica.vlcj.player.base.MarqueePosition;
import uk.co.caprica.vlcj.player.base.MediaPlayer;
import uk.co.caprica.vlcj.player.base.MediaPlayerEventAdapter;
import uk.co.caprica.vlcj.player.base.TitleDescription;
import uk.co.caprica.vlcj.player.base.TrackDescription;
import uk.co.caprica.vlcj.player.component.AudioPlayerComponent;
import uk.co.caprica.vlcj.player.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.player.embedded.fullscreen.adaptive.AdaptiveFullScreenStrategy;

public class App extends JFrame {
    private static final long serialVersionUID = 1L;
    private static final String TITLE = "My First Media Player";
    private static final String VIDEO_PATH = "D:\\Downloads\\sunset-beach.mp4";
    private static final String AUDIO_PATH = "D:\\Downloads\\time-to-feel-good.mp3";
    private static final String LOGO_PATH = "D:\\Downloads\\logo.png";
    private final EmbeddedMediaPlayerComponent mediaPlayerComponent;
    private final AudioPlayerComponent audioPlayerComponent;
    private final Marquee marquee;
    private final Logo logo;
    private final Equalizer equalizer;
    private final Overlay overlay;
    private boolean overlayEnabled = false;
    private JButton playButton;
    private JButton pauseButton;
    private JButton rewindButton;
    private JButton skipButton;
    private JButton playAudioButton;
    private JButton pauseAudioButton;
    private JToggleButton toggleButton;
    private JButton equalizerButton;
    private JToggleButton overlayButton;

    public App(String title) {
        super(title);
        mediaPlayerComponent = new EmbeddedMediaPlayerComponent() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                System.out.println("Mouse Clicked. (" + e.getX() + "," + e.getY() + ")");
            }
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                super.mouseWheelMoved(e);
                System.out.println("Mouse wheel moved. " + e.getScrollAmount());
            }
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                System.out.println("Key pressed. " + e.getKeyCode());
            }
            @Override
            public void playing(MediaPlayer mediaPlayer) {
                super.playing(mediaPlayer);
                System.out.println("Media Playback started.");
            }
            @Override
            public void finished(MediaPlayer mediaPlayer) {
                super.playing(mediaPlayer);
                System.out.println("Media Playback finished.");
            }
            @Override
            public void error(MediaPlayer mediaPlayer) {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        System.out.println("Failed to load Media.");
                    }
                });
            }
        };
        audioPlayerComponent = new AudioPlayerComponent();
        audioPlayerComponent.mediaPlayer().events().addMediaPlayerEventListener(new MediaPlayerEventAdapter() {
            @Override
            public void finished(MediaPlayer mediaPlayer) {
                System.out.println("Audio Playback Finished.");
            }
            @Override
            public void error(MediaPlayer mediaPlayer) {
                System.out.println("Failed to load Audio.");
            }
        });
        marquee = Marquee.marquee()
                .text("TutorialsPoint")
                .size(40)
                .colour(Color.WHITE)
                .position(MarqueePosition.BOTTOM_RIGHT)
                .opacity(0.5f)
                .enable();
        mediaPlayerComponent.mediaPlayer().marquee().set(marquee);
        logo = Logo.logo()
                .file(LOGO_PATH)
                .position(LogoPosition.TOP_LEFT)
                .opacity(0.3f)
                .enable();
        mediaPlayerComponent.mediaPlayer().logo().set(logo);
        mediaPlayerComponent.mediaPlayer().events().addMediaEventListener(new MediaEventAdapter() {
            @Override
            public void mediaParsedChanged(Media media,
                                           MediaParsedStatus newStatus) {
                if(newStatus == MediaParsedStatus.DONE) {
                    MediaPlayer mediaPlayer = mediaPlayerComponent.mediaPlayer();

                    List<TrackDescription> videoTracks = mediaPlayer.video().trackDescriptions();
                    System.out.println(videoTracks.get(1));

                    List<TitleDescription> titles = mediaPlayer.titles().titleDescriptions();
                    if(!titles.isEmpty()) {
                        System.out.println(titles.get(0));
                    }
                    List<ChapterDescription> chapters = mediaPlayer.chapters().descriptions();
                    if(!chapters.isEmpty()) {
                        System.out.println(chapters.get(0));
                    }
                    List<? extends TrackInfo> trackInfo = mediaPlayer.media().info().tracks();
                    if(!trackInfo.isEmpty()) {
                        System.out.println(trackInfo.get(0));
                    }
                }
            }
        });
        mediaPlayerComponent.mediaPlayer().fullScreen().strategy(new AdaptiveFullScreenStrategy(this));
        List<String> presetNames = mediaPlayerComponent.mediaPlayerFactory().equalizer().presets();
        System.out.println(presetNames);
        equalizer =  mediaPlayerComponent.mediaPlayerFactory().equalizer().newEqualizer("Dance");
        overlay = new Overlay(this);
        mediaPlayerComponent.mediaPlayer().overlay().set(overlay);
    }
    public void initialize() {
        this.setBounds(100, 100, 600, 400);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                mediaPlayerComponent.release();
                System.exit(0);
            }
        });
        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(mediaPlayerComponent, BorderLayout.CENTER);

        //Create a video border
        Border videoBorder = BorderFactory.createTitledBorder("Video Controls");

        //Create an audio border
        Border audioBorder = BorderFactory.createTitledBorder("Audio Controls");

        //Create others border
        Border othersBorder = BorderFactory.createTitledBorder("Others");

        JPanel videoControlsPane = new JPanel();
        videoControlsPane.setBorder(videoBorder);
        playButton = new JButton("Play");
        videoControlsPane.add(playButton);
        pauseButton = new JButton("Pause");
        videoControlsPane.add(pauseButton);
        rewindButton = new JButton("Rewind");
        videoControlsPane.add(rewindButton);
        skipButton = new JButton("Skip");
        videoControlsPane.add(skipButton);

        JPanel audioControlsPane = new JPanel();
        audioControlsPane.setBorder(audioBorder);
        playAudioButton = new JButton("Play");
        audioControlsPane.add(playAudioButton);
        pauseAudioButton = new JButton("Pause");
        audioControlsPane.add(pauseAudioButton);

        JPanel othersPane = new JPanel();
        othersPane.setBorder(othersBorder);
        toggleButton = new JToggleButton("Toggle Full Screen");
        equalizerButton = new JButton("Equalizer");
        overlayButton = new JToggleButton("Toggle Overlay");
        othersPane.add(toggleButton);
        othersPane.add(equalizerButton);
        othersPane.add(overlayButton);

        JPanel controlsPane = new JPanel();
        controlsPane.add(videoControlsPane);
        controlsPane.add(audioControlsPane);
        controlsPane.add(othersPane);
        controlsPane.setPreferredSize(new Dimension(400, 120));
        contentPane.add(controlsPane, BorderLayout.SOUTH);

        playButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mediaPlayerComponent.mediaPlayer().controls().play();
            }
        });
        pauseButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mediaPlayerComponent.mediaPlayer().controls().pause();
            }
        });
        rewindButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mediaPlayerComponent.mediaPlayer().controls().skipTime(-14000);
            }
        });
        skipButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mediaPlayerComponent.mediaPlayer().controls().skipTime(4000);
            }
        });
        playAudioButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                audioPlayerComponent.mediaPlayer().controls().play();
            }
        });
        pauseAudioButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                audioPlayerComponent.mediaPlayer().controls().pause();
            }
        });
        toggleButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mediaPlayerComponent.mediaPlayer().fullScreen().toggle();
            }
        });
        equalizerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mediaPlayerComponent.mediaPlayer().audio().setEqualizer(equalizer);
                System.out.println("Using equalizer: " + equalizer);
            }
        });
        overlayButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                overlayEnabled = !overlayEnabled;
                mediaPlayerComponent.mediaPlayer().overlay().enable(overlayEnabled);
            }
        });
        this.setContentPane(contentPane);
        this.setVisible(true);
    }
    public void loadVideo(String path) {
        mediaPlayerComponent.mediaPlayer().media().startPaused(path);
        mediaPlayerComponent.mediaPlayer().media().parsing().parse();
    }
    public void loadAudio(String path) {
        audioPlayerComponent.mediaPlayer().media().startPaused(path);
    }
    public static void main( String[] args ){
        try {
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
        }
        catch (Exception e) {
            System.out.println(e);
        }
        App application = new App(TITLE);
        application.initialize();
        application.setVisible(true);
        application.loadVideo(VIDEO_PATH);
        application.loadAudio(AUDIO_PATH);
    }
}
class Overlay extends Window {
    public Overlay(Window owner) {
        super(owner, WindowUtils.getAlphaCompatibleGraphicsConfiguration());
        setBackground(new Color(0, 0, 0, 0));
    }
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        GradientPaint gp = new GradientPaint(
                180.0f, 280.0f,
                new Color(255, 255, 255, 255),
                250.0f,
                380.0f,
                new Color(255, 255, 0, 0)
        );
        g2.setPaint(gp);
        g2.setFont(new Font("Serif", Font.PLAIN,32));
        g2.drawString("TutorialsPoint",200, 200);
    }
}
