package com.fabiyjose.practicse5colors;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 *
 * @author Fabián y José María
 */
public class Canvas extends JPanel {
    
    private BufferedImage imagen = null;
    private BufferedImage logo = null;
    private String pathImages = System.getProperty("user.dir") + "\\images\\";

    private boolean red = true;
    private boolean blue = true;
    private boolean green = true;
    
    private int xLogo;
    private int yLogo;
    //positionLogo(0,1,2,3) -> TopLeft, TopRight, BottomLeft, BottomRight
    
    public Canvas() throws MalformedURLException, IOException{        
        
        try {
            // 525 x 525
            imagen = ImageIO.read(new File(pathImages + "macawP5.jpg"));
            
            // 100 x 85
            logo = ImageIO.read(new File(pathImages + "logoP5.png"));
            
            this.setPreferredSize(new Dimension(imagen.getWidth(), imagen.getHeight()));
        } catch (IOException ex) {
            Logger.getLogger(Canvas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    @Override
    public void paintComponent (Graphics g) {
        if(imagen == null || logo == null) return;
        super.paintComponent(g);
        g.drawImage(seleccionarComponentes(imagen, red, green, blue), 0, 0, null);
        g.drawImage(logo, xLogo, yLogo, null);
    }
    
    
    public void paintIt(boolean r_c, boolean g_c, boolean b_c){
        this.red = r_c;
        this.green = g_c;
        this.blue = b_c;
        repaint();
    }
    
    public void setPositionLogo(int positionLogo){
        switch (positionLogo) {
            case 0:
                xLogo = 0;
                yLogo = 0;
                break;
            case 1:
                xLogo = imagen.getWidth() - logo.getWidth();
                yLogo = 0;
                break;
            case 2:
                xLogo = 0;
                yLogo = imagen.getHeight() - logo.getHeight();
                break;
            case 3:
                xLogo = imagen.getWidth() - logo.getWidth();
                yLogo = imagen.getHeight() - logo.getHeight();
                break;
            default:
                break;
        }
        repaint();
    }
    
    
    // Código proporcionado por el profesorado
    private static BufferedImage clonarImagen(BufferedImage bi) {
        ColorModel cm = bi.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = bi.copyData(null);
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }
    
    // Código proporcionado por el profesorado
    public static BufferedImage seleccionarComponentes(BufferedImage img, boolean c_red, boolean c_green, boolean c_blue){
        int mask = 0x000000;
        if (c_red) mask = mask | 0xFF0000;
        if (c_green) mask = mask | 0x00FF00;
        if (c_blue) mask = mask | 0x0000FF;
        
        BufferedImage img_copy = clonarImagen(img);
        for (int j=0;j<img.getHeight();j++){
            for (int i=0;i<img.getWidth();i++){
                img_copy.setRGB(i, j, mask & img.getRGB(i, j));
            }
        }
        return img_copy;
    }
    
}