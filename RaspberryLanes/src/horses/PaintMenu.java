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
	private Timer timer;
	private final static int SLEEP = 30;  //bigger # = slower animation	
	BufferedImage img = null;

	boolean xMas = false;
	int[][]snow = new int[300][2];
	public PaintMenu()
	{
		setSize(920, 575);
		setVisible(true);
		setBackground(Color.blue);
		setImage("src/horses/mixedberry.png");
		for(int[] flake : snow){
			flake[0] = (int) (Math.random() * 920);
			flake[1] = (int) (Math.random() * 575);
		}

		ActionListener paintCaller = new ActionListener(){
			public void actionPerformed(ActionEvent event)
			{
				
					
				
		
				repaint();  //each time timer fires it will call paint	
				
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
			window.clearRect(0,0,getWidth(),getHeight());	
			setImage("src/horses/raspberry.png");
			window.drawImage(img, 500, 300, 440,440, null);
			setImage("src/horses/mixedberry.png");
			
			window.drawImage(img, 500, 300, 440,440, null);
			/*for(int i = 0; i<img.getWidth(); i+=85){
				window.drawRect(i, 0, 1, 500);
			}*/
			//window.fillRect(888,130,1,900);
		
			
			
			window.setColor(Color.white);
			
			
			  }

		
		
	}	
	public void setImage(String filename){
		
		 
		    try {
				 img = ImageIO.read(new File(filename));
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	public void changeImage(String filename){
		
		 
	    try {
			 img = ImageIO.read(new File(filename));
		} catch (IOException e) {
			e.printStackTrace();
		}
}
}
