import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;


public class MainFrame extends JFrame {

    private JButton exit;
    private JButton jButton1;
    private JLabel jLabel1;
    private JButton newGame;
    private JButton newGame1;

    private MainFrame() {
        initComponents();
        setIconImage(new ImageIcon("images/IconSmall_Zombie.gif").getImage());
        setTitle("Zombie - Land");
    }


    @SuppressWarnings("unchecked")
    private void initComponents() {
        setBounds(0, 0, 970, 545);
        setResizable(false);
        setLocationRelativeTo(null);
        ImageHandler ih = new ImageHandler(this);
        Image img = ih.loadImage("images/image.jpg");
        JPanel jp = new BackgroundImageComponent(img);
        setContentPane(jp);
        setCursor(Toolkit.getDefaultToolkit().createCustomCursor(
                new ImageIcon("images/cur263.gif").getImage(), new Point(13, 13), "custom cursor"));

        jLabel1 = new JLabel();
        exit = new JButton();
        newGame = new JButton();
        newGame1 = new JButton();
        jButton1 = new JButton();


        jLabel1.setFont(new Font("MV Boli", Font.BOLD, 36));
        jLabel1.setForeground(new Color(0, 120, 0));
        jLabel1.setText("Z O M B I E - L A N D");

        newGame.setBackground(new Color(0, 0, 0));
        newGame.setFont(new Font("MV Boli", Font.PLAIN, 24));
        newGame.setForeground(new Color(0, 120, 0));
        newGame.setFocusable(false);
        newGame.setBorderPainted(false);
        newGame.setText("Start");
        newGame.addActionListener(this::newGameActionPerformed);

        newGame1.setBackground(new Color(0, 0, 0));
        newGame1.setFont(new Font("MV Boli", Font.PLAIN, 24));
        newGame1.setForeground(new Color(0, 120, 0));
        newGame1.setFocusable(false);
        newGame1.setBorderPainted(false);
        newGame1.setText("High Score");
        newGame1.addActionListener(this::newGame1ActionPerformed);

        jButton1.setBackground(new Color(0, 0, 0));
        jButton1.setFont(new Font("MV Boli", Font.PLAIN, 24));
        jButton1.setForeground(new Color(0, 120, 0));
        jButton1.setFocusable(false);
        jButton1.setBorderPainted(false);
        jButton1.setText("Help");
        jButton1.addActionListener(this::jButton1ActionPerformed);

        exit.setBackground(new Color(0, 0, 0));
        exit.setFont(new Font("MV Boli", Font.PLAIN, 24));
        exit.setForeground(new Color(0, 120, 0));
        exit.setFocusable(false);
        exit.setBorderPainted(false);
        exit.setText("Exit");
        exit.addActionListener(this::exitActionPerformed);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);

        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(190, 190, 190)
                                                .addComponent(jLabel1, GroupLayout.PREFERRED_SIZE, 500, GroupLayout.PREFERRED_SIZE))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(600, 600, 600)
                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                        .addComponent(jButton1, GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                                                        .addComponent(newGame, GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                                                        .addComponent(exit, GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                                                        .addComponent(newGame1, GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE))))
                                .addContainerGap(193, Short.MAX_VALUE))
        );

        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(87, 87, 87)
                                .addComponent(jLabel1, GroupLayout.PREFERRED_SIZE, 66, GroupLayout.PREFERRED_SIZE)
                                .addGap(74, 74, 74)
                                .addComponent(newGame, GroupLayout.PREFERRED_SIZE, 51, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(newGame1, GroupLayout.PREFERRED_SIZE, 51, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton1, GroupLayout.PREFERRED_SIZE, 51, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
                                .addComponent(exit, GroupLayout.PREFERRED_SIZE, 51, GroupLayout.PREFERRED_SIZE)
                                .addGap(50, 50, 50))
        );

        pack();
    }

    private void newGameActionPerformed(ActionEvent evt) {
        Object[] values = {"Normal", "Medium", "Hard"};
        Object choice = JOptionPane.showInputDialog(null,
                "Select Difficulty:", "input",
                JOptionPane.INFORMATION_MESSAGE, null,
                values, values[0]);
        try{
            switch ((String)choice){
                case "Normal":
                    this.setVisible(false);
                    dispose();
                    break;
                case "Medium":
                    break;
                case "Hard":
                    break;
                default:
                    break;
            }
        }catch (NullPointerException ignored){}


    }

    private void newGame1ActionPerformed(ActionEvent evt) {
        //this.setVisible(false);

        //dispose();
    }

    private void jButton1ActionPerformed(ActionEvent evt) {
        //this.setVisible(false);

        //dispose();
    }

    private void exitActionPerformed(ActionEvent evt) {
        System.exit(0);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> new MainFrame().setVisible(true));
    }


}
