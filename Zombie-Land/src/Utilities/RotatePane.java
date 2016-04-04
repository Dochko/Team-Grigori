package Utilities;

import javax.swing.*;
import java.awt.*;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class RotatePane extends JPanel {

    private ImageHandler ih;
    private Image img;
    private Point mousePoint;

    /**
     * Creates new form RotatePane
     */
    public RotatePane(String imagePath) {

        ih = new ImageHandler(this);
        img = ih.loadImage(imagePath);

        addMouseMotionListener(new MouseAdapter() {

            @Override
            public void mouseMoved(MouseEvent e) {

                mousePoint = e.getPoint();

                repaint();

            }

        });

    }

    @Override
    public Dimension getPreferredSize() {

        return new Dimension(img.getWidth(null), img.getHeight(null));

    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g.create();

        double rotation = 0f;

        int width = 969; //getWidth() - 1;
        int height = 544; //getHeight() - 1;

        if (mousePoint != null) {

            int x = width / 2;
            int y = height / 2;

            int deltaX = mousePoint.x - x;
            int deltaY = mousePoint.y - y;

            rotation = -Math.atan2(deltaX, deltaY);
            rotation = Math.toDegrees(rotation) + 180;

        }

        int x  = (width - img.getWidth(null)) / 2;
        int y  = (height - img.getHeight(null)) / 2;

        g2d.rotate(Math.toRadians(rotation), width / 2, height / 2);
        g2d.drawImage(img, x, y, this);

       /* x = width / 2;
        y = height / 2;
        g2d.setStroke(new BasicStroke(3));
        g2d.setColor(Color.RED);
        g2d.drawLine(x, y, x, y - height / 4);*/
        g2d.dispose();


    }
}