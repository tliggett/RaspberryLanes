package horses;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class ImagePanel extends JPanel{

    public BufferedImage image;

    public ImagePanel(String filename) {
       try {                
          image = ImageIO.read(new File(filename));
       } catch (IOException ex) {
            // handle exception...
       }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for(int i = 0; i < image.getHeight(); i++){
			for(int j = 0; j<image.getWidth(); j++){
				
				if(image.getRGB(i,j) == -16777095){
					int rgb = image.getRGB(i, j);
			        rgb = rgb & 0x00FFFFFF;
					image.setRGB(i,j, rgb);	
				
				}
			}
			
		}
        g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), null); // see javadoc for more info on the parameters            
    }

}
