package horses;

//© A+ Computer Science  -  www.apluscompsci.com
//Name - 
//Date -
//Class -
//Lab  -

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Window;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.Timer;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import java.awt.Canvas;

class PaintMenu extends Canvas
{
	private int x;
	private int y;
	int i;
	int phase;
	boolean isNew;
	String name;
	int map;
	private Timer timer;
	private final static int SLEEP = 30;  //bigger # = slower animation	
	BufferedImage img = null;

	public PaintMenu()
	{
		setSize(450, 400);
		setVisible(true);
		setBackground(Color.blue);
		setImage("src/data/mixed.png");
		i = 0;
		phase = 0;
		ActionListener paintCaller = new ActionListener(){
			public void actionPerformed(ActionEvent event)
			{
				
				i++;	
				if(i==100){
					setImage("src/data/studio.png");
				}if(i == 200){
					setImage("src/data/LogoN.png");
					
				}if(i == 300){
					phase = 1;
					setImage("src/data/Track.png");
					
				}
		
				repaint(); 
				//each time timer fires it will call paint	
				
				
			}
		};
		timer = new Timer(SLEEP, paintCaller);
		timer.start();
		
		
			
			
	}


	public boolean isActive(){
		if(timer.isRunning()){
			return true;
		}else{
			return false;
		}
	}
	public void startTimer(){
		timer.start();
	}
	
	public void update(Graphics window)
	{
	   paint(window);	
	}
	
	public void paint( Graphics window )
	{
		
		 {
			 window.setColor(Color.white);
			 window.fillRect(0,0,getWidth(),getHeight());	
			
			if(phase == 0){
				window.drawImage(img, 0, 0, 450,400, null);
			}else if(phase ==1){
				window.setColor(Color.white);
				window.drawImage(img, 0, 0, 450,400, null);
				
			}
			
			
			
			
			
			
			  }

		
		
	}	
	public void setImage(String filename){
		
		 
		    try {
				 img = ImageIO.read(new File(filename));
			} catch (IOException e) {
				e.printStackTrace();
			}
	}

}
