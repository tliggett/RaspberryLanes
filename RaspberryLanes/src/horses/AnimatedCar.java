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
import java.awt.event.ActionEvent;
import javax.swing.Timer;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import java.awt.Canvas;

class AnimatedCar extends Canvas
{
	private int x;
	private int y;
	private Timer timer;
	private final static int SLEEP = 10;  //bigger # = slower animation	
	BufferedImage img = null;
	HorseList stable;
	public AnimatedCar(HorseList racers)
	{
		setSize(664, 500);
		setVisible(true);
		setBackground(Color.blue);
		setImage();
		stable = racers;
		ActionListener paintCaller = new ActionListener(){
			public void actionPerformed(ActionEvent event)
			{
				repaint();  //each time timer fires it will call paint	
			}
		};
		timer = new Timer(SLEEP, paintCaller);
		
		
		
			
			
	}
	public void updateStable(HorseList update){
		stable = update;
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
			window.drawImage(img, 0, 0, 723, 500, null);
			window.setColor(Color.white);
			window.fillRect(1100, 100, 50, 700);	
			for(int i = 0; i <stable.racers.size(); i++){
				window.drawImage(stable.racers.get(i).graphic.image, 0, (i*30) + 140, 30, 30, null);
				}
			
			  }

		
		
	}	
	public void setImage(){
		
		 
		    try {
				 img = ImageIO.read(new File("src/horses/Track.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
}
