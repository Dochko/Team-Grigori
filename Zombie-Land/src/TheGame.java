import Utilities.BackgroundImageComponent;
import Utilities.ImageHandler;
import Utilities.RotatePane;
import Utilities.Sound;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.Set;

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
            private final Set<Integer> pressed = new HashSet<>();

            @Override
            public synchronized void keyPressed(KeyEvent e) {
                pressed.add((int) e.getKeyChar());
                if (pressed.size() > 1) {
                    if(pressed.contains(KeyEvent.VK_A) && pressed.contains(KeyEvent.VK_W)){
                        System.out.println("A + W");
                        x -= 2;
                        y -= 2;
                        MoveMe.setBounds(x, y, 100, 25);
                        revalidate();
                    }else if(pressed.contains(KeyEvent.VK_D) && pressed.contains(KeyEvent.VK_W)){
                        System.out.println("D + W");
                        x += 2;
                        y -= 2;
                        MoveMe.setBounds(x, y, 100, 25);
                        revalidate();
                    }else if(pressed.contains(KeyEvent.VK_D) && pressed.contains(KeyEvent.VK_S)){
                        System.out.println("D + S");
                        x += 2;
                        y += 2;
                        MoveMe.setBounds(x, y, 100, 25);
                        revalidate();
                    }
                    else if(pressed.contains(KeyEvent.VK_A) && pressed.contains(KeyEvent.VK_S)){
                        System.out.println("A + S");
                        x -= 2;
                        y += 2;
                        MoveMe.setBounds(x, y, 100, 25);
                        revalidate();
                    }
                }else{
                    switch (e.getKeyCode()){
                        case KeyEvent.VK_ESCAPE:
                            dispose();
                            break;
                        case KeyEvent.VK_W:
                            y -= 2;
                            MoveMe.setBounds(x, y, 100, 25);
                            revalidate();
                            break;
                        case KeyEvent.VK_A:
                            x -= 2;
                            MoveMe.setBounds(x, y, 100, 25);
                            revalidate();
                            break;
                        case KeyEvent.VK_S:
                            y += 2;
                            MoveMe.setBounds(x, y, 100, 25);
                            revalidate();
                            break;
                        case KeyEvent.VK_D:
                            x += 2;
                            MoveMe.setBounds(x, y, 100, 25);
                            revalidate();
                            break;
                        default:
                            break;
                    }
                }
            }

            @Override
            public synchronized void keyReleased(KeyEvent e) {
                pressed.remove((int) e.getKeyChar());
            }

            @Override
            public void keyTyped(KeyEvent e) {
            }
        });

    }
}
