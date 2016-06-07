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

class PaintRace extends Canvas
{
	private int x;
	private int y;
	int i;
	private Timer timer;
	private final static int SLEEP = 30;  //bigger # = slower animation	
	BufferedImage img = null;
	private HorseList stable;
	int gallop;
	boolean xMas = false;
	boolean chocolateRain = false;
	int[][]snow = new int[300][2];
	public PaintRace(HorseList racers, String map)
	{
		setSize(920, 575);
		setVisible(true);
		setBackground(Color.blue);
		
		switch(map){
		case "Chocolate": 
			setImage("src/data/chocolatemap.png");
			chocolateRain = true;
			break;
		case "Classic": 
			setImage("src/data/Track.png");
			break;
		case "Christmas": 
			setImage("src/data/xmas.png");
			xMas = true;
			break;
		case "Beach": 
			setImage("src/data/beach.png");
			break;
		}
		
		for(int[] flake : snow){
			flake[0] = (int) (Math.random() * 920);
			flake[1] = (int) (Math.random() * 575);
		}
		gallop = 0;
		stable = racers;
		ActionListener paintCaller = new ActionListener(){
			public void actionPerformed(ActionEvent event)
			{
				for(int i = 0; i <stable.racers.size(); i++){
					if(stable.racers.get(i).time.get(stable.racers.get(i).time.size()-1) != 999){
						stable.racers.get(i).x += stable.racers.get(i).getPPS();
						}
					
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
					} catch (Exception e) {
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
		
		
	}
	public void christmas(Boolean isMas){
		xMas = isMas;
	}
	public void chocolate(boolean isLate){
		chocolateRain = isLate;
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
			
			window.drawImage(img, 0, 0, getWidth(),getHeight(), null);
			
			/*for(int i = 0; i<img.getWidth(); i+=85){
				window.drawRect(i, 0, 1, 500);
			}*/
			//window.fillRect(888,130,1,900);
			if(gallop == 0){
				gallop = 1;
			}else{
				gallop = 0;
			}
			for(int i = 0; i <stable.racers.size(); i++){
				window.drawImage(stable.racers.get(i).graphic.image, (int)stable.racers.get(i).x, (i*36 + gallop) + 157, 38, 38, null);
				
			}
		
			
			
			int height = 3;
			int length = 3;
			int speed = 1;
			if(xMas || chocolateRain)
			{
				
				if(xMas){
					height = 3;
					length = 3;
					speed = 1;
					window.setColor(Color.white);
				}if(chocolateRain){
					height = 5;
					length = 1;
					speed = 6;
					window.setColor(Color.blue);
				}
				
				
				for(int[] snowflake : snow){
					
					window.fillRect(snowflake[0], snowflake[1], length, height);
					snowflake[1] += speed;
					if(snowflake[1] > 575){
						snowflake[1] = 0;
						snowflake[0] = (int)(Math.random() * 920);
					}
					
				}
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
