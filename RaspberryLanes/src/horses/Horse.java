package horses;

import java.awt.Color;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.imageio.ImageIO;

public class Horse implements Comparable<Horse> {
	int age;
	int maxAge;
	boolean canCompete;
	double speed;
	double acc;
	double stamina;
	double heart;
	ArrayList<Double> time;
	ArrayList<Integer> place;
	double[]furlongs = new double[10];
	String name;
	double score;
	Color color;
	ImagePanel graphic;
	Image image;
	double x;
	
	public Horse(String name) throws IOException{
		this.name = name;
		age = 0;
		maxAge = (int)(Math.random()*8 + 3);
		speed = (Math.random()*16) + 85;
		acc = (Math.random()*16) + 85;
		stamina = (Math.random()*16) + 85;
		heart = (Math.random()*16) + 85;
		canCompete = true;
		time = new ArrayList<Double>(); 
		place = new ArrayList<Integer>();
		color = randomColor();
		graphic = new ImagePanel("src/horses/inGame.png");
		
		double x = 0;
		colorGraphic();
		
	
	}
	public Horse(ArrayList<String> fromFile) throws IOException{
		name = fromFile.get(0);
		age = Integer.parseInt(fromFile.get(1));
		maxAge = Integer.parseInt(fromFile.get(2));
		canCompete =Boolean.parseBoolean(fromFile.get(3));
		speed = Double.parseDouble(fromFile.get(4));
		acc = Double.parseDouble(fromFile.get(5));
		stamina = Double.parseDouble(fromFile.get(6));
		heart = Double.parseDouble(fromFile.get(7));
		time = new ArrayList<Double>(); 
		place = new ArrayList<Integer>();
		color = new Color(Integer.parseInt(fromFile.get(8)),Integer.parseInt(fromFile.get(9)),Integer.parseInt(fromFile.get(10)) );
		graphic = new ImagePanel("src/horses/LogoN.png");
		for(int i = 11; i<fromFile.size(); i++){
			time.add(Double.parseDouble(fromFile.get(i)));
		}
		
		double x = 0;
		colorGraphic();	
	}
	public void colorGraphic(){
		Color darkNavy = new Color(0, 0, 120);
		Color track = new Color(255,208,115);
		for(int i = 0; i < graphic.image.getHeight(); i++){
			for(int j = 0; j<graphic.image.getWidth(); j++){
				if(graphic.image.getRGB(i,j) == -3407872){
					graphic.image.setRGB(i, j, color.getRGB());	
				}
				
				if(graphic.image.getRGB(i,j) == -16777095 || graphic.image.getRGB(i,j) == Color.white.getRGB()){
					graphic.image.setRGB(i,j, track.getRGB());	
				}
				
			}
		}
		graphic.repaint();
		
	}
	public double getPPS(){
		if(time.size() > 0){
			if(x<67){
				return 67/furlongs[0];
			}if(x<134){
				return 67/furlongs[1];
			}if(x<201){
				return 67/furlongs[2];
			}if(x<268){
				return 67/furlongs[3];
			}if(x<335){
				return 67/furlongs[4];
			}if(x<402){
				return 67/furlongs[5];
			}if(x<469){
				return 67/furlongs[6];
			}if(x<536){
				return 67/furlongs[7];
			}if(x<603){
				return 67/furlongs[8];
			}
				return 67/furlongs[9];
			}else{
			return 0;
		}
	}
	
	public double calcScore(){
		double ret = 0;
		if(time.size() == 0){
			return 1;
		}
		ret = 130-time.get(time.size()-1)-(age/2); 
		if(ret <= 0){
			ret = 0.01;
		}
		return ret;
	}
	
	public Color randomColor(){
		Color col = new Color(0);
		int rnd = (int)(Math.random()*17);
		switch(rnd){
		case 0: col = (Color.BLACK);
		break;
		case 1: col = Color.pink;
		break;
		case 2: col = Color.cyan;
		break;
		case 3: col = Color.darkGray;
		break;
		case 4: col = Color.MAGENTA;
		break;
		case 5: col = Color.GRAY;
		break;
		case 6: col = new Color(153, 51, 0);
		break;
		case 7: col = new Color(204, 51, 0);
		break;
		case 8: col = new Color(102, 26, 0);
		break;
		case 9: col = new Color(77, 19, 0);
		break;
		case 10: col = new Color(26, 6, 0);
		break;
		case 11: col = new Color(255, 168, 128);
		break;
		case 12: col = new Color(255, 255, 204);
		break;
		case 13: col = new Color(77, 255, 136);
		break;
		case 14: col = new Color(153, 0, 0);
		break;
		}
		
		
		return col;
	}
	
	public void doRace(){
		double time = 0;
		age ++;
		
		
		int injury =(int)(Math.random()*100);
		if(injury == 24){
			this.time.add(999.0);
			return;
		}
		
		double rnd = ((Math.random()*6));
		
		for(int i = 0; i<furlongs.length; i++){
			furlongs[i] = 21 - (speed/10) + rnd/10;
		}
		
		furlongs[0] += (10-(acc/10))*2;
		int sdet = (int)((stamina/10)-2);
		int dep = 1;
		for(int i = sdet; i< furlongs.length; i++){
			furlongs[i]+= dep;
			dep++;
		}
		
		furlongs[9] -= (heart/20);
		if(furlongs[9] < speed){
			furlongs[9] = 20.5 - (speed/10);
		}
		
		
		for(double furlong : furlongs){
			time += furlong;
		}
		
		time+=4;
		int ageDiff = maxAge-age;
		if(ageDiff!= 0){
			speed-= (10)/(ageDiff);
			acc-= (3)/(ageDiff);	
		}
		
		
		
		time = ((int)(time*100));
		time/= 100;
		Double add = time;
		this.time.add(add);
		return;
		
	}
	
	public double getTime(){
		return time.get(time.size()-1);
	}
	
	@Override
	public int compareTo(Horse h){
		if(h.getTime() > this.getTime()){
			return -1;
		}
		if(h.getTime() == this.getTime()){
			return 0;
		}
		return 1;
		
		
	}
	public String toFile(){
		String forRet = "";
		forRet += ""
			+ name + ","
			+ age + ","
			+ maxAge + ","
			+ canCompete + ","
			+ speed + ","
			+ acc + ","
			+ stamina + ","
			+ heart + ","
			+ color.getRed() + ","
			+color.getGreen() + ","
			+ color.getBlue() + ",";
		for(Double tim : time){
			forRet += tim + ",";
		}
			
		return forRet;
	}
	public String toString(){
		String time = "";
		
		if(this.getTime() == 999){
			time = "DNF";
		}else{
			time = Tools.timeToString(this.getTime());
		}
		
		return name + " " + time;
		
	}
	public String toPreview(){
		String forRet = "PREVIOUS TIMES\n";
		for(Double tim : time){
			forRet += Tools.timeToString(tim) + "\n";
		}
		return forRet;
	}
	
}