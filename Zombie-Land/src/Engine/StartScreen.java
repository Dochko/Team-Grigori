package Engine;

import Utilities.BackgroundImageComponent;
import Utilities.ImageHandler;
import Utilities.Sound;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;


public class StartScreen extends JFrame {
    private int WIDTH = 970;
    private int HEIGHT = 545;

    private String windowTitle = "Zombie - Land";
    private String windowIconPath = "Resources/IconSmall_Zombie.gif";
    private String backgroundImgPath = "Resources/startscreenBackground.jpg";
    private String cursorImgPath = "Resources/zombieHandCursor.gif";
    private String introMusicPath = "sound/Intro.wav";

    private Sound intro;

    private JPanel gameStartPanel;
    private JLabel gameTitle;
    private JButton gameStartButton;
    private JButton highScoreButton;
    private JButton helpButton;
    private JButton exitButton;

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
        /*Object choice = JOptionPane.showInputDialog(null,
                "Select Difficulty:", "input",
                JOptionPane.INFORMATION_MESSAGE, null,
                values, values[0]);*/
        if((String)choice == null) {
            choice = "";
        }
        switch ((String)choice){
            case "Normal":
                intro.Stop();
                this.dispose();
                GameScreen window = new GameScreen();
                window.setIconImage(new ImageIcon(windowIconPath).getImage());
                window.setTitle(windowTitle);
                window.setVisible(true);
                break;
            case "Medium":
                //TODO: Medium difficulty
                break;
            case "Hard":
                //TODO: Hard difficulty
                break;
            default:
                break;
        }
    }

    private void highScoreScreen(ActionEvent evt) {
        //this.setVisible(false);
        // TODO: high score screen
        //dispose();
    }

    private void helpScreen(ActionEvent evt) {
        //this.setVisible(false);
        //TODO: help screen
        //dispose();
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
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(Color.black);
                button.setForeground(new Color(0, 120, 0));
            }
        });
    }
}