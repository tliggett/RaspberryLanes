package horses;

//© A+ Computer Science  -  www.apluscompsci.com
//Name - 
//Date -
//Class -
//Lab  -

import java.awt.Graphics;
import java.awt.Graphics2D;
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

import java.awt.AlphaComposite;
import java.awt.Canvas;

class Epic extends Canvas
{
	private int x;
	private int y;
	int i;
	private Timer timer;
	private final static int SLEEP = 30;  //bigger # = slower animation	
	BufferedImage img = null;
	float alpha = 1f;
	boolean xMas = false;
	
	public Epic()
	{
		setSize(1440, 860);
		setVisible(true);
		setBackground(random());
		
		

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
			int it = (int) (Math.random() * 85) + 5;
			for(int i = 0; i<1440; i+= it){
				for(int j = 0; j<860; j+=it){
					window.setColor(random());
					window.fillRect(i, j, it, it);
				}
			}
			
			  }

		
		
	}	
	public Color random(){
		int one =(int) (Math.random()*256); 
		int two =(int) (Math.random()*256); 
		int three =(int) (Math.random()*256); 
		
		return new Color(one,two,three);
		
		 
		   
	}
	public void changeImage(String filename){
		
		 
	    try {
			 img = ImageIO.read(new File(filename));
		} catch (IOException e) {
			e.printStackTrace();
		}
}
}
