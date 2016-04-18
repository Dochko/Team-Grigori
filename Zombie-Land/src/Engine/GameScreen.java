package Engine;

import GameState.GameStateManager;
import Models.Bonus;
import Models.Enemy;
import Models.Player;
import Models.Projectiles;
import Utilities.FrameRateCounter;
import Utilities.HighScoreTemplate;
import Utilities.Sound;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;

public class GameScreen extends JFrame {
    public static int WIDTH = 1366;
    public static int HEIGHT = 768;

    private GamePanel panel;
    private Graphics2D g;
    private BufferedImage image;

    private GameStateManager gsm;
    private Point point;

    private Timer timer;
    private FrameRateCounter frameCounter;

    private int FPS = 0;
    private int averageFPS;

    public static Player player;
    public static ArrayList<Projectiles> projectiles;
    public static ArrayList<Projectiles> enemyProjectiles;
    public static ArrayList<Enemy> enemies;
    public static ArrayList<Bonus> bonuses;


    private boolean waveStart; // pause between waves
    private long waveStartTimer; // the starting time of the wave
    private long waveStartTimerDiff; // this starts the wave after the delay we set it
    private int waveDelay = 2000; // wave start delay
    private int waveNumber;
    private int defaultSpawnEnemySize = 10;
    private int endWaveNumber = 25;
    public static int deadEnemiesCounter;
    public static int gameScore = 0;
    public static int difficult;

    private boolean gamePause = false;

    private boolean gameEnded;

    //Health Bar
    private Rectangle healthBar = new Rectangle(WIDTH - 210, 10,200 ,20);

    //Sound
    private Sound game_music = new Sound("sound/Game/GameMusic.wav");
    private Sound victory_music = new Sound("sound/Game/Victory.wav");
    private Sound defeat_music = new Sound("sound/Game/Defeat.wav");
    private Sound player_run = new Sound("sound/Player/run-grass.wav");
    private Sound player_shoot =new Sound("sound/Guns/ak74-shoot.wav");
    private Sound zombie_eat = new Sound("sound/Zombies/zombie-eating.wav");

    // JFrame constructor
    public GameScreen() {
        initComponents();
    }

    private void initComponents() {
        this.panel = new GamePanel();
        this.setContentPane(this.panel);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setBounds(0, 0, WIDTH, HEIGHT);
        this.setLocationRelativeTo(null);
        // this.setUndecorated(true);
        setCursor(Toolkit.getDefaultToolkit().createCustomCursor(
                new ImageIcon("Resources/crosshair.png").getImage(), new Point(17 / 2, 17 / 2), "custom cursor"));
        this.pack();

        game_music.Loop();
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

                try {
                    gameUpdate();
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
                try {
                    gameRender();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                gameDraw();

                if (player.isDead() && player.getAnimator()) {

                    timer.stop();
                    try{
                        checkHighScore(gameScore);
                    }catch (IOException | ClassNotFoundException e){
                        System.out.println(e.getMessage());
                    }
                    enemies.clear();

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
            enemyProjectiles = new ArrayList<>();
            enemies = new ArrayList<>();
            bonuses = new ArrayList<>();

            waveStartTimer = 0;
            waveStartTimerDiff = 0;
            waveStart = true;
            waveNumber = 0;
            gameScore = 0;
            gameEnded = false;
        }

        // This update every object in the game.
        private void gameUpdate() throws IOException, ClassNotFoundException {
            // map update
            gsm.update();

            // new enemy wave logic
            if(waveNumber > endWaveNumber) {
                gameEnded = true;
            }

            if (!gameEnded) {
                if(waveStartTimer == 0 && deadEnemiesCounter == 0) {
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
            if(waveStart && (enemies.size() == 0 || deadEnemiesCounter == 0)) {
                createNewEnemies();
            }

            // player update
            player.update();

            double playerRotation = 0f;

            if (point != null && !player.isDead()) {
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

            // enemy bullets update
            for (int i = 0; i < enemyProjectiles.size(); i++) {
                boolean remove = enemyProjectiles.get(i).update();
                if (remove) {
                    enemyProjectiles.remove(i);
                    i--;
                }
            }

            // enemy update
            double enemyRotation = 0f;
            for (Enemy enemy1 : enemies) {
                enemy1.update();

                double deltaX = player.getX() - enemy1.getX();
                double deltaY = player.getY() - enemy1.getY();

                if(!enemy1.isDead()){
                    enemyRotation = -Math.atan2(deltaX, deltaY);
                    enemyRotation = Math.toDegrees(enemyRotation) + 180;

                    enemy1.setAngle(enemyRotation);
                    enemy1.setDx(deltaX);
                    enemy1.setDy(deltaY);
                }
            }

            // bonuses update
            bonuses.forEach(Bonus::update);

            // player bullet - enemy collision
            for (int i = 0; i < projectiles.size(); i++) {
                Projectiles bullet = projectiles.get(i);
                Rectangle playerBulletBorder = bullet.getBulletBorder();

                for (Enemy enemy : enemies) {
                    Rectangle enemyBorder = enemy.getEnemyBorder();

                    if (playerBulletBorder.intersects(enemyBorder) && !enemy.isDead() && player.getWeaponType() != 3) {
                        enemy.hit(bullet.getBulletDamage());
                        bullet.setTargetHit(true);
                        break;
                    } else if (playerBulletBorder.intersects(enemyBorder) && !enemy.isDead()) {
                        enemy.hit(bullet.getBulletDamage());
                    } else if(enemy.isDead() && !enemy.getDroppedBonus()) {
                        //bonus creation and spawning
                        int rnd = (int) ((Math.random() * 30) + 1);
                        if(rnd == 10 || rnd == 20 || rnd == 30) {
                            bonuses.add(new Bonus((int) enemy.getX(), (int) enemy.getY()));
                        }

                        enemy.setDroppedBonus(true);
                    }
                }
            }

            for (int i = 0; i < projectiles.size(); i++) {
                if(projectiles.get(i).getTargetHit()){
                    projectiles.remove(i);
                    i--;
                }
            }

            // enemy bullet - player collision
            for (int i = 0; i < enemyProjectiles.size(); i++) {
                Projectiles enemyBullet = enemyProjectiles.get(i);
                Rectangle bossBulletBorder = enemyBullet.getBulletBorder();
                Rectangle playerBorder = player.getPlayerBorder();

                for (Enemy boss : enemies) {
                    if (bossBulletBorder.intersects(playerBorder) && !player.isDead()) {
                        player.hit(boss.getDamage());
                        enemyProjectiles.remove(i);
                        i--;
                        break;
                    }
                }
            }

            // enemy-player collision
            for (Enemy enemy : enemies) {
                Rectangle enemyBorder = enemy.getEnemyBorder();
                Rectangle playerBorder = player.getPlayerBorder();

                if (enemyBorder.intersects(playerBorder) && !player.isDead() && !enemy.isDead() ) {
                    player.hit(enemy.getDamage());
                    healthBar.width = player.getHealth() * 2;

                    //Sound effects
                    zombie_eat.setVolumeDown(10f);
                    zombie_eat.Play();
                }
            }

            // player - bonus collision
            for (int i = 0; i < bonuses.size(); i++) {

                Rectangle bonusBorder = bonuses.get(i).getBonusBorder();
                Rectangle playerBorder = player.getPlayerBorder();

                if (playerBorder.intersects(bonusBorder)) {
                    switch (bonuses.get(i).getType()) {
                        case 0:
                            if (player.getHealth() != 100) {
                                player.addHealth(bonuses.get(i).getBonusGiven());
                                bonuses.remove(i);
                                i--;
                            }
                            break;
                        case 1:
                            player.addShotgunAmmo(bonuses.get(i).getBonusGiven());
                            bonuses.remove(i);
                            i--;
                            break;
                        case 2:
                            player.addGaussAmmo(bonuses.get(i).getBonusGiven());
                            bonuses.remove(i);
                            i--;
                            break;
                        default:
                            break;
                    }
                }
            }
        }

        // Checking the player high score and compare it with the others
        private void checkHighScore(int gameScore) throws IOException, ClassNotFoundException  {
            boolean check = true;
            boolean done = false;
            HighScoreTemplate newScore = new HighScoreTemplate("", waveNumber, gameScore);
            ArrayList<HighScoreTemplate> newInts = new ArrayList<>();
            File file = new File("Resources/HighScore/highScore.hs");
            ArrayList<HighScoreTemplate> ints = readFromFile(file);
            JOptionPane optionPane = new JOptionPane();
            optionPane.setBounds(500, 400, 200, 200);
            JDialog dialog = new JDialog();
            dialog.setTitle("High Score achieved");
            dialog.setVisible(true);
            dialog.dispose();
            if(ints != null) {
                check = false;
                for (HighScoreTemplate anInt : ints) {
                    if(newInts.size() == 10){
                        break;
                    }

                    if (gameScore >= anInt.getScore()) {
                        done = true;
                        String name = optionPane.showInputDialog(dialog, "What is your name", "Empty");
                        newScore.setName(name);
                        newInts.add(newScore);
                        if(newInts.size() < 10){
                            newInts.add(anInt);
                        }

                        check = true;
                        gameScore = -1;
                    } else {
                        newInts.add(anInt);
                    }
                }
            }

            if (check){
                if(newInts.size() == 0 || (newInts.size() < 10 && !done)){
                    String name = optionPane.showInputDialog(dialog, "What is your name", "Empty");
                    newScore.setName(name);
                    newInts.add(newScore);
                }
                file = new File("Resources/HighScore/highScore.hs");
                writeToFile(newInts, file);

            }
        }

        private void writeToFile(ArrayList info, File file) throws IOException {
            FileOutputStream fout;
            ObjectOutputStream oos;
            if(!file.exists()) {
                file.createNewFile();
            }
            fout = new FileOutputStream(file, false);
            oos = new ObjectOutputStream(fout);
            oos.writeObject(info);
            oos.close();

        }

        private ArrayList readFromFile(File file) throws IOException, ClassNotFoundException {
            FileInputStream fin;
            ObjectInputStream in;
            if(!file.exists()) {
                return null;
            }
            fin = new FileInputStream(file);
            in = new ObjectInputStream(fin);
            ArrayList info;
            info = (ArrayList) in.readObject();
            in.close();
            return info;
        }

        // This method load the images of the objects in the memory
        private void gameRender() throws IOException {
            // map draw
            gsm.draw(g);

            // enemy draw
            for (Enemy enemy : enemies) {
                enemy.draw(g);
            }

            // enemy bullet draw
            for (Projectiles enemyProjectile : enemyProjectiles) {
                enemyProjectile.draw(g);
            }

            // bonus draw
            for (Bonus bonus : bonuses) {
                bonus.draw(g);
            }

            // player draw
            player.draw(g);

            // player bullet draw
            for (Projectiles projectile : projectiles) {
                projectile.draw(g);
            }


            // player health draw
            g.setColor(new Color(255,0,0,127));
            g.fill(healthBar);
            g.setColor(Color.BLACK);
            g.draw(healthBar);

            g.setFont(new Font("Century Gothic", Font.PLAIN, 14));
            g.setColor(Color.WHITE);
            g.drawString("Health: " + player.getHealth(), GameScreen.WIDTH - 150, 25);

            // draw avg fps and other information
            averageFPS = frameCounter.getFrameRate();
            g.setFont(new Font("Century Gothic", Font.PLAIN, 12));
            g.setColor(Color.WHITE);
            g.drawString("FPS: " + averageFPS, 10, 10);
            g.drawString("Zombie counter: " + deadEnemiesCounter, 10, 20);
            g.drawString("Bullet counter: " + projectiles.size(), 10, 30);
            g.drawString("Score: " + gameScore, 10, 45);
            g.drawString("Shotgun ammo: " + player.getShotgunAmmo(), 10, 60);
            g.drawString("Gauss ammo: " + player.getGaussAmmo(), 10, 70);

            // player died
            if(player.isDead()) {
                //sound control
                game_music.Close();
                player_shoot.Close();
                player_run.Close();
                zombie_eat.Close();
                defeat_music.setVolumeUp(4f);
                defeat_music.Play();

                g.setFont(new Font("Century Gothic", Font.BOLD, 36));
                String str = "G A M E    O V E R   ! ! !";
                int length = (int) g.getFontMetrics().getStringBounds(str, g).getWidth();
                g.drawString(str, GameScreen.WIDTH / 2 - length / 2, GameScreen.HEIGHT / 2);

                g.setFont(new Font("Century Gothic", Font.PLAIN, 26));
                str = "press ESC to go back to Menu.";
                length = (int) g.getFontMetrics().getStringBounds(str, g).getWidth();
                g.drawString(str, GameScreen.WIDTH / 2 - length / 2, (GameScreen.HEIGHT / 2) + 50);

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
                //sound control
                game_music.Close();
                victory_music.PlayOnce();

                g.setFont(new Font("Century Gothic", Font.PLAIN, 28));
                String str = "YOU WON AGAINST THE ZOMBIE HORDE !!! CONGRATS !!!";
                int length = (int) g.getFontMetrics().getStringBounds(str, g).getWidth();
                g.drawString(str, GameScreen.WIDTH / 2 - length / 2, GameScreen.HEIGHT / 2);

                g.setFont(new Font("Century Gothic", Font.PLAIN, 26));
                str = "press ESC to go back to Menu.";
                length = (int) g.getFontMetrics().getStringBounds(str, g).getWidth();
                g.drawString(str, GameScreen.WIDTH / 2 - length / 2, (GameScreen.HEIGHT / 2) + 50);

                try{
                    checkHighScore(gameScore);
                }catch (IOException | ClassNotFoundException e){
                    System.out.println(e.getMessage());
                }
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
                e.printStackTrace();
            }
        }

        // The wave spawn method.
        private void createNewEnemies() {
            enemies.clear();
            deadEnemiesCounter = 0;
            if(waveNumber <= endWaveNumber) {
                if ((waveNumber % 5) == 1 && waveNumber > 1) {
                    // change background every 6 levels
                    int randomState = (int) (Math.random() * 4);
                    gsm.setState(randomState);
                    int numberOfBosses = (waveNumber / 5) * GameScreen.difficult;

                    deadEnemiesCounter = ((defaultSpawnEnemySize * waveNumber) / 2) * GameScreen.difficult;
                    for (int i = 0; i < numberOfBosses; i++) {
                        enemies.add(new Enemy(4));
                    }
                    for (int j = 0; j < deadEnemiesCounter; j++) {
                        enemies.add(new Enemy((int) ((Math.random() * 3) + 1)));
                    }
                    deadEnemiesCounter += numberOfBosses;
                } else {
                    deadEnemiesCounter = (defaultSpawnEnemySize * waveNumber) * GameScreen.difficult;
                    for (int i = 0; i < deadEnemiesCounter; i++) {
                        enemies.add(new Enemy((int) ((Math.random() * 3) + 1)));
                    }
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

            player_run.Play();

            if (key.getKeyCode() == KeyEvent.VK_SPACE) {
                if(gamePause) {
                    timer.start();
                    gamePause = false;
                }else{
                    timer.stop();
                    gamePause = true;
                }
            }


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

            if (key.getKeyCode() == KeyEvent.VK_1) {
                player.setWeaponType(1);
            }

            if (key.getKeyCode() == KeyEvent.VK_2) {
                player.setWeaponType(2);
            }

            if (key.getKeyCode() == KeyEvent.VK_3) {
                player.setWeaponType(3);
            }

            if (key.getKeyCode() == KeyEvent.VK_ESCAPE) {
                game_music.Close();
                setVisible(false);
                timer.stop();
                dispose();
                StartScreen menu = new StartScreen();
                menu.setVisible(true);
            }
        }

        @Override
        public void keyReleased(KeyEvent key) {
            player_run.Stop();
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
            if (mouseButton.getButton() == MouseEvent.BUTTON1 && !player.isDead()){
                player.setFiring(true);
                player_shoot.setVolumeDown(15f);
                player_shoot.ShootLoop();
            }
        }

        @Override
        public void mouseReleased(MouseEvent mouseButton) {
            if (mouseButton.getButton() == MouseEvent.BUTTON1){
                player.setFiring(false);
                player_shoot.Stop();
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