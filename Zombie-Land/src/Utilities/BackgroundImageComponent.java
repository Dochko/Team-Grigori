package Utilities;

import javax.swing.*;
import java.awt.*;

public class BackgroundImageComponent extends JPanel
{
    private Image image;

    public BackgroundImageComponent(Image image)
    {
        this.image = image;
    }

    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        int w = getWidth();
        int h = getHeight();
        int imageWidth = image.getWidth(null);
        int imageHeight = image.getHeight(null);
        int x = (w - imageWidth)/2;          // center the image  
        int y = (h - imageHeight)/2;         // in its container  
        g.drawImage(image, x, y, this);
    }

    public Dimension getPreferredSize()
    {
        return new Dimension(image.getWidth(null), image.getHeight(null));
    }
}