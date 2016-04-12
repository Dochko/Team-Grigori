package Engine;

import GameState.GameStateManager;
import Models.Enemy;
import Models.Player;
import Models.Projectiles;
import Utilities.FrameRateCounter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class GameScreen extends JFrame {
    public static int WIDTH = 970;
    public static int HEIGHT = 545;

    private GamePanel panel;
    private Graphics2D g;
    private BufferedImage image;

    private GameStateManager gsm;
    private Point point;

    private Timer timer;
    private FrameRateCounter frameCounter;

    private int FPS = 1000 / 60;
    private int averageFPS;

    public static Player player;
    public static ArrayList<Projectiles> projectiles;
    public static ArrayList<Enemy> enemies;

    private boolean waveStart; // pause between waves
    private long waveStartTimer; // the starting time of the wave
    private long waveStartTimerDiff; // this starts the wave after the delay we set it
    private int waveDelay = 2000; // wave start delay
    private int waveNumber;
    private int defaultSpawnEnemySize = 10;
    private int endWaveNumber = 3;

    private boolean gameEnded;

    // JFrame constructor
    public GameScreen() {
        initComponents();
    }

    private void initComponents() {
        this.panel = new GamePanel();
        this.setContentPane(this.panel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setBounds(0, 0, this.WIDTH, this.HEIGHT);
        this.setLocationRelativeTo(null);
        // this.setUndecorated(true);
        setCursor(Toolkit.getDefaultToolkit().createCustomCursor(
                new ImageIcon("Resources/crosshair.png").getImage(), new Point(17 / 2, 17 / 2), "custom cursor"));
        this.pack();
    }

    // The main panel where the game draws itself
    public class GamePanel extends JPanel {

        // GamePanel constructor
        public GamePanel() {
            super();
            frameCounter = new FrameRateCounter();
            this.addKeyListener(new KeyAdapter());
            this.addMouseMotionListener(new mouseMotionAdapter());
            this.addMouseListener(new mouseAdapter());
            setPreferredSize(new Dimension(GameScreen.WIDTH, GameScreen.HEIGHT));
            this.setFocusable(true);
            this.requestFocusInWindow();
        }

        public void addNotify() {
            super.addNotify();

            init();
            
            // This is the game loop.
            timer = new Timer(FPS, event -> {
                frameCounter.submitReading();

                gameUpdate();
                try {
                    gameRender();
                } catch (IOException e) {
                    // e.printStackTrace();
                }
                gameDraw();

                if (player.isDead()) {
                    timer.stop();
                }
            });

            timer.setRepeats(true);
            timer.start();
        }

        // The initialization of our game
        private void init() {
            image = new BufferedImage(GameScreen.WIDTH, GameScreen.HEIGHT, BufferedImage.TYPE_INT_RGB);
            g = (Graphics2D) image.getGraphics();
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

            gsm = new GameStateManager();
            player = new Player();
            projectiles = new ArrayList<>();
            enemies = new ArrayList<>();

            waveStartTimer = 0;
            waveStartTimerDiff = 0;
            waveStart = true;
            waveNumber = 0;

            gameEnded = false;
        }

        // This update every object in the game.
        private void gameUpdate() {
            // map update
            gsm.update();

            // new enemy wave logic
            if(waveNumber > endWaveNumber) {
                gameEnded = true;
            }

            if (!gameEnded) {
                if(waveStartTimer == 0 && enemies.size() == 0) {
                    waveNumber++;
                    waveStart = false;
                    waveStartTimer = System.nanoTime();
                } else {
                    waveStartTimerDiff = (System.nanoTime() - waveStartTimer) / 1000000;
                    if(waveStartTimerDiff > waveDelay) {
                        waveStart = true;
                        waveStartTimer = 0;
                        waveStartTimerDiff = 0;
                    }
                }
            }

            // create enemies
            if(waveStart && enemies.size() == 0) {
                createNewEnemies();
            }

            // player update
            player.update();

            double playerRotation = 0f;

            if (point != null) {
                double playerDeltaX = point.getX() - player.getX();
                double playerDeltaY = point.getY() - player.getY();

                playerRotation = -Math.atan2(playerDeltaX, playerDeltaY);
                playerRotation = Math.toDegrees(playerRotation) + 180;
            }

            player.setAngle(playerRotation);

            // bullet update
            for (int i = 0; i < projectiles.size(); i++) {
                boolean remove = projectiles.get(i).update();
                if (remove) {
                    projectiles.remove(i);
                    i--;
                }
            }

            // enemy update
            double enemyRotation = 0f;
            for (int i = 0; i < enemies.size(); i++) {
                enemies.get(i).update();

                double deltaX = player.getX() - enemies.get(i).getX();
                double deltaY = player.getY() - enemies.get(i).getY();

                enemyRotation = -Math.atan2(deltaX, deltaY);
                enemyRotation = Math.toDegrees(enemyRotation) + 180;

                enemies.get(i).setAngle(enemyRotation);
                enemies.get(i).setDx(deltaX);
                enemies.get(i).setDy(deltaY);
            }

            // bullet-enemy collision
            for (int i = 0; i < projectiles.size(); i++) {
                Projectiles bullet = projectiles.get(i);
                Rectangle bulletBorder = bullet.getBulletBorder();

                for (int j = 0; j < enemies.size(); j++) {
                    Enemy enemy = enemies.get(j);
                    Rectangle enemyBorder = enemy.getEnemyBorder();

                    if (bulletBorder.intersects(enemyBorder)) {
                        enemy.hit();
                        projectiles.remove(i);
                        i--;
                        break;
                    }
                }
            }

            // check dead enemies
            for (int i = 0; i < enemies.size(); i++) {
                if (enemies.get(i).isDead()) {
                    enemies.remove(i);
                    i--;
                }
            }

            // enemy-player collision
            for (int i = 0; i < enemies.size(); i++) {
                Enemy enemy = enemies.get(i);
                Rectangle enemyBorder = enemy.getEnemyBorder();
                Rectangle playerBorder = player.getPlayerBorder();

                if (enemyBorder.intersects(playerBorder)) {
                    player.hit();
                }
            }
        }

        // This method load the images of the objects in the memory
        private void gameRender() throws IOException {
            // map draw
            gsm.draw(g);

            // draw avg fps
            averageFPS = frameCounter.getFrameRate();
            g.setFont(new Font("Century Gothic", Font.PLAIN, 12));
            g.setColor(Color.WHITE);
            g.drawString("FPS: " + averageFPS, 10, 10);
            g.drawString("Zombie counter: " + enemies.size(), 10, 20);
            g.drawString("Bullet counter: " + projectiles.size(), 10, 30);

            // player draw
            player.draw(g);

            // player health draw
            g.setFont(new Font("Century Gothic", Font.PLAIN, 12));
            g.setColor(Color.WHITE);
            g.drawString("Health: " + player.getHealth(), GameScreen.WIDTH - 100, 10);

            // player died
            if(player.isDead()) {
                g.setFont(new Font("Century Gothic", Font.BOLD, 36));
                String str = "G A M E    O V E R   ! ! !";
                int length = (int) g.getFontMetrics().getStringBounds(str, g).getWidth();
                g.drawString(str, GameScreen.WIDTH / 2 - length / 2, GameScreen.HEIGHT / 2);
            }

            // bullet draw
            for (int i = 0; i < projectiles.size(); i++) {
                projectiles.get(i).draw(g);
            }

            // enemy draw
            for (int i = 0; i < enemies.size(); i++) {
                enemies.get(i).draw(g);
            }

            // draw wave number
            if(waveStartTimer != 0 && !gameEnded) {
                g.setFont(new Font("Century Gothic", Font.PLAIN, 28));
                String str = "- W A V E  " + waveNumber + " -";
                int length = (int) g.getFontMetrics().getStringBounds(str, g).getWidth();
                int alpha = (int) (255 * Math.sin(3.14 * waveStartTimerDiff / waveDelay));
                if(alpha > 255) {
                    alpha = 255;
                }
                g.setColor(new Color(255, 255, 255, alpha));
                g.drawString(str, GameScreen.WIDTH / 2 - length / 2, GameScreen.HEIGHT / 2);
            }

            // game ending message draw
            if (gameEnded) {
                g.setFont(new Font("Century Gothic", Font.PLAIN, 28));
                String str = "YOU WON AGAINST THE ZOMBIE HORDE !!! CONGRATS !!!";
                int length = (int) g.getFontMetrics().getStringBounds(str, g).getWidth();
                g.drawString(str, GameScreen.WIDTH / 2 - length / 2, GameScreen.HEIGHT / 2);
            }
        }

        // The brush of our game engine. This draws on the screen our objects and background.
        private void gameDraw() {
            Graphics g2 = getGraphics();
            try {
                g2.drawImage(image, 0, 0, null);
                g2.dispose();
            } catch (NullPointerException e) {
                // g2.dispose();
                // e.printStackTrace();
            }
        }

        // The wave spawn method.
        private void createNewEnemies() {
            enemies.clear();

            if(waveNumber <= endWaveNumber) {
                for(int i = 0; i < defaultSpawnEnemySize * waveNumber; i++) {
                    enemies.add(new Enemy(1));
                }
            }
        }
    }

    private class KeyAdapter implements KeyListener {
        @Override
        public void keyTyped(KeyEvent key) {
            // not needed
        }

        @Override
        public void keyPressed(KeyEvent key) {
            if (key.getKeyCode() == KeyEvent.VK_W) {
                player.setUp(true);
            }

            if (key.getKeyCode() == KeyEvent.VK_S) {
                player.setDown(true);
            }

            if (key.getKeyCode() == KeyEvent.VK_A) {
                player.setLeft(true);
            }

            if (key.getKeyCode() == KeyEvent.VK_D) {
                player.setRight(true);
            }

            if (key.getKeyCode() == KeyEvent.VK_ESCAPE) {
                setVisible(false);
                timer.stop();
                dispose();
                StartScreen menu = new StartScreen();
                menu.setVisible(true);
            }
        }

        @Override
        public void keyReleased(KeyEvent key) {
            if (key.getKeyCode() == KeyEvent.VK_W) {
                player.setUp(false);
            }

            if (key.getKeyCode() == KeyEvent.VK_S) {
                player.setDown(false);
            }

            if (key.getKeyCode() == KeyEvent.VK_A) {
                player.setLeft(false);
            }

            if (key.getKeyCode() == KeyEvent.VK_D) {
                player.setRight(false);
            }
        }
    }

    private class mouseAdapter implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent mouseButton) {
            // not needed
        }

        @Override
        public void mousePressed(MouseEvent mouseButton) {
            if (mouseButton.getButton() == mouseButton.BUTTON1){
                player.setFiring(true);
            }
        }

        @Override
        public void mouseReleased(MouseEvent mouseButton) {
            if (mouseButton.getButton() == mouseButton.BUTTON1){
                player.setFiring(false);
            }
        }

        @Override
        public void mouseEntered(MouseEvent mouseButton) {
            // not needed
        }

        @Override
        public void mouseExited(MouseEvent mouseButton) {
            // not needed
        }
    }

    private class mouseMotionAdapter implements MouseMotionListener {
        @Override
        public void mouseDragged(MouseEvent mouse) {
            point = mouse.getPoint();
        }

        @Override
        public void mouseMoved(MouseEvent mouse) {
            point = mouse.getPoint();
        }
    }
}


