import Utilities.BackgroundImageComponent;
import Utilities.ImageHandler;
import Utilities.RotatePane;
import Utilities.Sound;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by Yani on 4.4.2016 г..
 */
public class TheGame extends JFrame{

    private JPanel jp;
    private int x;
    private int y;

    public TheGame(){
        initComponents();
        setIconImage(new ImageIcon("images/IconSmall_Zombie.gif").getImage());
        setTitle("Zombie - Land");
        gameAction();
    }

    private void initComponents(){
        setBounds(0, 0, 970, 545);
        setResizable(false);
        setLocationRelativeTo(null);
        ImageHandler ih = new ImageHandler(this);
        Image img = ih.loadImage("images/grass.jpg");
        jp = new BackgroundImageComponent(img);
        jp.setLayout(null);

        setContentPane(jp);
        setCursor(Toolkit.getDefaultToolkit().createCustomCursor(
                new ImageIcon("images/cur263.gif").getImage(), new Point(13, 13), "custom cursor"));

        Sound intro = new Sound("./sound/Intro.wav");
        intro.Loop();




        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void gameAction(){
        x = 250;
        y = 250;
        JButton MoveMe = new JButton();
        MoveMe.setBorderPainted(false);
        MoveMe.setBackground(new Color(0, 0, 0));
        MoveMe.setFont(new Font("MV Boli", Font.PLAIN, 24));
        MoveMe.setForeground(new Color(0, 120, 0));
        MoveMe.setFocusable(false);
        MoveMe.setText("HERO");
        MoveMe.setBounds(x, y, 100, 25);
        jp.add(MoveMe);
        addKeyListener(new KeyListener() {
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
                    dispose();
                }
                if(e.getKeyCode() == KeyEvent.VK_W){
                    y -= 2;
                    MoveMe.setBounds(x, y, 100, 25);
                    revalidate();
                }
                if(e.getKeyCode() == KeyEvent.VK_A){
                    x -= 2;
                    MoveMe.setBounds(x, y, 100, 25);
                    revalidate();
                }
                if(e.getKeyCode() == KeyEvent.VK_S){
                    y += 2;
                    MoveMe.setBounds(x, y, 100, 25);
                    revalidate();
                }
                if(e.getKeyCode() == KeyEvent.VK_D){
                    x += 2;
                    MoveMe.setBounds(x, y, 100, 25);
                    revalidate();
                }
            }

            public void keyReleased(KeyEvent e) {
                System.out.println("2test2");
            }

            public void keyTyped(KeyEvent e) {
            }
        });

    }
}
