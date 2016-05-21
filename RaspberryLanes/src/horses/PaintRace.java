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

class PaintRace extends Canvas
{
	private int x;
	private int y;
	int i;
	private Timer timer;
	private final static int SLEEP = 150;  //bigger # = slower animation	
	BufferedImage img = null;
	private HorseList stable;
	public PaintRace(HorseList racers)
	{
		setSize(720, 500);
		setVisible(true);
		setBackground(Color.blue);
		setImage();
		stable = racers;
		ActionListener paintCaller = new ActionListener(){
			public void actionPerformed(ActionEvent event)
			{
				for(int i = 0; i <stable.racers.size(); i++){
					stable.racers.get(i).x += stable.racers.get(i).getPPS();
				}
				i++;
				if(i>150){
					i = 0;
					timer.restart();
					timer.stop();
					for(int i = 0; i <stable.racers.size(); i++){
						stable.racers.get(i).x = 0;
					}
					try {
						RaspberryLanes.updateStuff();
					} catch (NullPointerException | IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					
				}
				repaint();  //each time timer fires it will call paint	
				
			}
		};
		timer = new Timer(SLEEP, paintCaller);
		
		
		
			
			
	}
	public void updateStable(HorseList update){
		stable = update;
		
		int i =1;
		i++;
		
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
			
			window.drawImage(img, 0, 0, 723, 500, null);
			/*for(int i = 0; i<img.getWidth(); i+=67){
				window.drawRect(i, 0, 1, 500);
			}*/
			window.setColor(Color.white);
			window.fillRect(700,136,5,314);
			for(int i = 0; i <stable.racers.size(); i++){
				window.drawImage(stable.racers.get(i).graphic.image, (int)stable.racers.get(i).x, (i*30) + 140, 30, 30, null);
				
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
