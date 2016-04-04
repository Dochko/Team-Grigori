package Utilities;

import java.awt.*;
import java.awt.image.MemoryImageSource;
import java.awt.image.PixelGrabber;

public class ImageHandler
{
    private MediaTracker mt;
    private int count = -1;
    /**
     * Constructor to assign container reference for
     * MediaTracker object
     *
     * @param  c  Container object to assign
     */
    public ImageHandler(Container c)
    {
        mt = new MediaTracker(c);
    }
    /**
     * Loads image from file location
     *
     * @param  path  file location
     * @return  loaded image
     */
    public Image loadImage(String path)
    {
        Image img = Toolkit.getDefaultToolkit().getImage(path);
        count++;
        try
        {
            mt.addImage(img, count);
            mt.waitForID(count);
        }
        catch(InterruptedException ie)
        {
            System.out.println("Error: " + ie.getMessage());
            ie.printStackTrace();
        }
        return img;
    }
    /**
     * Replaces given rgb color with white and
     * returns modified image
     *
     * @param  img  Image to be modified
     * @param  r  red color index
     * @param  g  green color index
     * @param  b  blue color index
     * @return  Modified image
     */
    public Image replaceColorWithWhite(Image img, int r, int g, int b)
    {
        int height = img.getHeight(null);
        int width = img.getWidth(null);
        int pixels[] =  new int[height * width];
        PixelGrabber pg = new PixelGrabber(img, 0, 0, width, height, pixels, 0, width);
        try
        {
            pg.grabPixels();
        }
        catch(InterruptedException ie)
        {
            ie.printStackTrace();
        }
        for(int i = 0; i < pixels.length; i++)
        {
            int p = pixels[i];
            int red = 0xff & (p >> 16);
            int green = 0xff & (p >> 8);
            int blue = 0xff & p;
            if(red >= r - 5 && red <= r + 5 && green >= g - 5 && green <= g + 5 && blue >= b - 5 && blue <= b + 5)
                pixels[i] = (255 << 24) | (255 << 16) | (255 << 8) | 255 ;
        }
        MemoryImageSource source = new MemoryImageSource(width, height, pixels, 0, width);
        Image image = Toolkit.getDefaultToolkit().createImage(source);
        count++;
        try
        {
            mt.addImage(image, count);
            mt.waitForID(count);
        }
        catch(InterruptedException ie)
        {
            System.out.println("Error: " + ie.getMessage());
            ie.printStackTrace();
        }
        return image;
    }
    /**
     * Replaces given rgb color with transparent
     * pixel and returns modified image
     *
     * @param  img  Image to be modified
     * @param  r  red color index
     * @param  g  green color index
     * @param  b  blue color index
     * @return  Modified image
     */
    public Image makeColorTransparent(Image img, int r, int g, int b)
    {
        int height = img.getHeight(null);
        int width = img.getWidth(null);
        int pixels[] =  new int[height * width];
        PixelGrabber pg = new PixelGrabber(img, 0, 0, width, height, pixels, 0, width);
        try
        {
            pg.grabPixels();
        }
        catch(InterruptedException ie)
        {
            ie.printStackTrace();
        }
        for(int i = 0; i < pixels.length; i++)
        {
            int p = pixels[i];
            int red = 0xff & (p >> 16);
            int green = 0xff & (p >> 8);
            int blue = 0xff & p;
            if(red >= r - 5 && red <= r + 5 && green >= g - 5 && green <= g + 5 && blue >= b - 5 && blue <= b + 5)
                pixels[i] = (0 << 24) | (255 << 16) | (255 << 8) | 255 ;
        }
        MemoryImageSource source = new MemoryImageSource(width, height, pixels, 0, width);
        Image image = Toolkit.getDefaultToolkit().createImage(source);
        count++;
        try
        {
            mt.addImage(image, count);
            mt.waitForID(count);
        }
        catch(InterruptedException ie)
        {
            System.out.println("Error: " + ie.getMessage());
            ie.printStackTrace();
        }
        return image;
    }
    /**
     * Overlays given foreground image
     * incorporating transparency.
     *
     * @param  g  Graphics reference
     * @param  img  Foreground image
     * @param  x  x-coordinate
     * @param  y  y-coordinate
     */
    public void overlayImage(Graphics g, Image img, int x, int y)
    {
        int height = img.getHeight(null);
        int width = img.getWidth(null);
        int pixels[] =  new int[height * width];
        PixelGrabber pg = new PixelGrabber(img, 0, 0, width, height, pixels, 0, width);
        for(int i = 0; i < pixels.length; i++)
        {
            int p = pixels[i];
            int alpha = 0xff & (p >> 24);
            int red = 0xff & (p >> 16);
            int green = 0xff & (p >> 8);
            int blue = 0xff & p;
            if(alpha == 0) continue;
            Color c = new Color(red, green, blue);
            g.setColor(c);
            g.drawOval(x + i % width, y + i / height, 1, 1);
        }
    }
}
