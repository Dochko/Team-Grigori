package Engine;

import Utilities.BackgroundImageComponent;
import Utilities.HighScoreTemplate;
import Utilities.ImageHandler;
import Utilities.Sound;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.util.ArrayList;


public class StartScreen extends JFrame {
    private int WIDTH = 970;
    private int HEIGHT = 545;

    private String windowTitle = "Zombie - Land";
    private String windowIconPath = "Resources/IconSmall_Zombie.gif";
    private String backgroundImgPath = "Resources/startscreenBackground.jpg";
    private String cursorImgPath = "Resources/zombieHandCursor.gif";
    private String introMusicPath = "sound/Game/Intro.wav";

    private Sound intro;
    private Sound hover_sound;

    private JPanel gameStartPanel;
    private JLabel gameTitle;
    private JButton gameStartButton;
    private JButton highScoreButton;
    private JButton helpButton;
    private JButton exitButton;
    private JPanel helpPane;
    private JPanel highScorePane;
    JLabel highScoreLabel;

    public StartScreen() {
        initComponents();
        setIconImage(new ImageIcon(windowIconPath).getImage());
        setTitle(windowTitle);
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        setBounds(0, 0, WIDTH, HEIGHT);
        setResizable(false);
        setLocationRelativeTo(null);
        // setUndecorated(true);
        ImageHandler ih = new ImageHandler(this);
        Image img = ih.loadImage(backgroundImgPath);
        gameStartPanel = new BackgroundImageComponent(img);
        setContentPane(gameStartPanel);
        setCursor(Toolkit.getDefaultToolkit().createCustomCursor(
                new ImageIcon(cursorImgPath).getImage(), new Point(13, 13), "custom cursor"));

        intro = new Sound(introMusicPath);
        intro.Loop();

        gameTitle = new JLabel();
        gameStartButton = new JButton();
        highScoreButton = new JButton();
        helpButton = new JButton();
        exitButton = new JButton();

        gameStartButton.setBackground(new Color(0, 0, 0));
        gameStartButton.setFont(new Font("MV Boli", Font.PLAIN, 24));
        gameStartButton.setForeground(new Color(0, 120, 0));
        gameStartButton.setFocusable(false);
        gameStartButton.setBorderPainted(false);
        gameStartButton.setText("Start");
        gameStartButton.addActionListener(this::newGameActionPerformed);
        HoverEvent(gameStartButton);

        highScoreButton.setBackground(new Color(0, 0, 0));
        highScoreButton.setFont(new Font("MV Boli", Font.PLAIN, 24));
        highScoreButton.setForeground(new Color(0, 120, 0));
        highScoreButton.setFocusable(false);
        highScoreButton.setBorderPainted(false);
        highScoreButton.setText("High Score");
        highScoreButton.addActionListener(this::highScoreScreen);
        HoverEvent(highScoreButton);

        helpButton.setBackground(new Color(0, 0, 0));
        helpButton.setFont(new Font("MV Boli", Font.PLAIN, 24));
        helpButton.setForeground(new Color(0, 120, 0));
        helpButton.setFocusable(false);
        helpButton.setBorderPainted(false);
        helpButton.setText("Help");
        helpButton.addActionListener(this::helpScreen);
        HoverEvent(helpButton);

        exitButton.setBackground(new Color(0, 0, 0));
        exitButton.setFont(new Font("MV Boli", Font.PLAIN, 24));
        exitButton.setForeground(new Color(0, 120, 0));
        exitButton.setFocusable(false);
        exitButton.setBorderPainted(false);
        exitButton.setText("Exit");
        exitButton.addActionListener(this::exitActionPerformed);
        HoverEvent(exitButton);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);

        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(190, 190, 190)
                                                .addComponent(gameTitle, GroupLayout.PREFERRED_SIZE, 500, GroupLayout.PREFERRED_SIZE))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(600, 600, 600)
                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                        .addComponent(helpButton, GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                                                        .addComponent(gameStartButton, GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                                                        .addComponent(exitButton, GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                                                        .addComponent(highScoreButton, GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE))))
                                .addContainerGap(193, Short.MAX_VALUE))
        );

        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(87, 87, 87)
                                .addComponent(gameTitle, GroupLayout.PREFERRED_SIZE, 66, GroupLayout.PREFERRED_SIZE)
                                .addGap(74, 74, 74)
                                .addComponent(gameStartButton, GroupLayout.PREFERRED_SIZE, 51, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(highScoreButton, GroupLayout.PREFERRED_SIZE, 51, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(helpButton, GroupLayout.PREFERRED_SIZE, 51, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
                                .addComponent(exitButton, GroupLayout.PREFERRED_SIZE, 51, GroupLayout.PREFERRED_SIZE)
                                .addGap(50, 50, 50))
        );

        pack();
    }

    private void newGameActionPerformed(ActionEvent evt) {
        this.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(
                new ImageIcon(cursorImgPath).getImage(), new Point(13, 13), "custom cursor"));

        Object[] values = {"Normal", "Medium", "Hard"};
        JOptionPane pane = new JOptionPane("Select Difficulty:",
                JOptionPane.QUESTION_MESSAGE,
                JOptionPane.OK_CANCEL_OPTION, null,
                values, values[0]);
        JDialog dialog = pane.createDialog("Difficulty Settings");
        dialog.setIconImage(new ImageIcon(windowIconPath).getImage());
        dialog.setCursor(this.getCursor());
        dialog.setVisible(true);
        Object choice = pane.getValue();
        if(choice == null) {
            choice = "";
        }

        GameScreen window = new GameScreen();
        switch ((String)choice){
            case "Normal":
                GameScreen.difficult = 1;
                break;
            case "Medium":
                //TODO: Medium difficulty
                GameScreen.difficult = 2;
                break;
            case "Hard":
                //TODO: Hard difficulty
                GameScreen.difficult = 3;
                break;
            default:
                break;
        }

        if (!choice.equals("")) {
            intro.Stop();
            this.dispose();
            window.setIconImage(new ImageIcon(windowIconPath).getImage());
            window.setTitle(windowTitle);
            window.setVisible(true);
        }
    }

    private void highScoreScreen(ActionEvent evt){
        String helpBackgroundPath = "Resources/emptyScreen.jpg";
        ImageHandler ih = new ImageHandler(this);
        Image img = ih.loadImage(helpBackgroundPath);
        highScorePane = new BackgroundImageComponent(img);;
        JButton highScoreOk =  new JButton();
        JButton highScoreReset =  new JButton();
        highScoreLabel = new JLabel();
        highScoreLabel.setBounds(200, 0, 600, 400);
        highScoreLabel.setFont(new Font("MV Boli", Font.PLAIN, 14));
        highScoreLabel.setForeground(new Color(0, 120, 0));
        try{
            highScoreLabel.setText(HighScoreText());
        }catch (IOException | ClassNotFoundException e ){

        }
        highScoreReset.setBackground(new Color(0, 0, 0));
        highScoreReset.setFont(new Font("MV Boli", Font.PLAIN, 24));
        highScoreReset.setForeground(new Color(0, 120, 0));
        highScoreReset.setFocusable(false);
        highScoreReset.setBorderPainted(false);
        highScoreReset.setText("Reset");
        highScoreReset.setBounds(630, 380, 150, 50);
        highScoreReset.addActionListener(this::highScoreResetActionPerformed);
        highScoreOk.setBackground(new Color(0, 0, 0));
        highScoreOk.setFont(new Font("MV Boli", Font.PLAIN, 24));
        highScoreOk.setForeground(new Color(0, 120, 0));
        highScoreOk.setFocusable(false);
        highScoreOk.setBorderPainted(false);
        highScoreOk.setText("Back");
        highScoreOk.setBounds(630, 450, 150, 50);
        highScoreOk.addActionListener(this::highScoreOkActionPerformed);
        HoverEvent(highScoreReset);
        HoverEvent(highScoreOk);
        highScorePane.add(highScoreReset);
        highScorePane.add(highScoreLabel);
        highScorePane.add(highScoreOk);
        highScorePane.setLayout(null);
        highScorePane.setVisible(true);
        gameStartPanel.setVisible(false);
        setContentPane(highScorePane);
    }

    private String HighScoreText() throws IOException, ClassNotFoundException {
        FileInputStream fin;
        ObjectInputStream in;
        File file = new File("Resources/HighScore/highScore.hs");
        int i = 1;
        String high = "";
        if(file.exists()) {
            fin = new FileInputStream(file);
            in = new ObjectInputStream(fin);
            ArrayList<HighScoreTemplate> ints;
            ints = (ArrayList<HighScoreTemplate>) in.readObject();
            in.close();
            for (HighScoreTemplate anInt : ints) {
                high += i + ". " + anInt.getName() + ", " +
                        anInt.getWave() + " wave, " +
                        anInt.getScore() + " Points<br>";
                i++;
            }
        }
        for (int j = i ; j < 11; j++) {
            high += j + ". <br>";
        }
        String highScoreText;
        highScoreText = "<html>Welcome to the Z O M B I E - L A N D <br><br>" +
                "&#9High Scores:<br>" + high;
        return highScoreText;
    }

    private void highScoreResetActionPerformed(ActionEvent actionEvent) {
        File file = new File("Resources/HighScore/highScore.hs");
        file.delete();
        try{
            highScoreLabel.setText(HighScoreText());
        }catch (IOException | ClassNotFoundException e ){
        }
        setContentPane(highScorePane);
    }

    private void highScoreOkActionPerformed(ActionEvent actionEvent) {
        highScorePane.setVisible(false);
        gameStartPanel.setVisible(true);
        setContentPane(gameStartPanel);
    }

    private void helpScreen(ActionEvent evt) {
        String helpBackgroundPath = "Resources/emptyScreen.jpg";
        ImageHandler ih = new ImageHandler(this);
        Image img = ih.loadImage(helpBackgroundPath);
        helpPane = new BackgroundImageComponent(img);;
        JButton helpOk =  new JButton();
        JLabel helpLabel = new JLabel();
        helpLabel.setBounds(200, 10, 600, 400);
        helpLabel.setFont(new Font("MV Boli", Font.PLAIN, 14));
        helpLabel.setForeground(new Color(0, 120, 0));
        helpLabel.setText(helpText());
        helpOk.setBackground(new Color(0, 0, 0));
        helpOk.setFont(new Font("MV Boli", Font.PLAIN, 24));
        helpOk.setForeground(new Color(0, 120, 0));
        helpOk.setFocusable(true);
        helpOk.setBorderPainted(false);
        helpOk.setText("Back");
        helpOk.setBounds(630, 450, 150, 50);
        helpOk.addActionListener(this::helpOkActionPerformed);
        HoverEvent(helpOk);
        helpPane.add(helpLabel);
        helpPane.add(helpOk);
        helpPane.setLayout(null);
        helpPane.setVisible(true);
        gameStartPanel.setVisible(false);
        setContentPane(helpPane);
    }

    private String helpText() {
        String helpText;
        helpText = "<html>Welcome to the Z O M B I E - L A N D <br><br>" +
                "&#9What you are going to play:<br>" +
                "You are merciless hunter who has ended in a dieing World <br>" +
                "filled with deadly disease and turns everyone that had <br>" +
                "contact with it to a mutated creature, which is hungry for flesh.<br>" +
                "Your task is to eliminate all threats and survive the Apocalypse.<br><br>" +
                "&#9Button configuration:<br>" +
                "1.\"W, S, D, A\" - Move commands.<br>" +
                "2.\"Mouse Left Click\" - Fire.<br>" +
                "3.\"1, 2 or 3\" - Change weapons.<br>" +
                "<br>Good Luck in your endeavour.</html>";
        return helpText;
    }

    private void helpOkActionPerformed(ActionEvent actionEvent) {
        helpPane.setVisible(false);
        gameStartPanel.setVisible(true);
        setContentPane(gameStartPanel);
    }

    private void exitActionPerformed(ActionEvent evt) {
        System.exit(0);
    }

    /**
     * Hovered:
     *      background color -> green
     *      foreground color -> black
     *
     * Hovered out:
     *      background color -> black
     *      foreground color -> green
     *
     * @param button
     */
    private void HoverEvent(javax.swing.JButton button){
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(0, 120, 0));
                button.setForeground(Color.black);
                hover_sound = new Sound("sound/Game/hover.wav");
                hover_sound.Play();
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(Color.black);
                button.setForeground(new Color(0, 120, 0));
            }
        });
    }
}