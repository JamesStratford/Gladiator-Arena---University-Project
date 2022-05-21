/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rpgGame.GUI;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 *
 * @author jestr
 */
public class ImagePanel extends JPanel
{
    private BufferedImage image;

    public void setImage(BufferedImage image)
    {
        this.image = image;
        Graphics g = image.createGraphics();
        g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), null);
        g.dispose();
        this.repaint();
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, this);
    }
}
