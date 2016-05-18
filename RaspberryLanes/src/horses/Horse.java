package horses;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;

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
	
	public Horse(String name){
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
		colorGraphic();
		
	
	}
	public Horse(ArrayList<String> fromFile){
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
		graphic = new ImagePanel("src/horses/inGame.png");
		colorGraphic();
		
	}
	public void colorGraphic(){
		
		for(int i = 0; i < graphic.image.getHeight(); i++){
			
			for(int j = 0; j<graphic.image.getWidth(); j++){
				if(graphic.image.getRGB(i,j) == -3407872){
					graphic.image.setRGB(i, j, color.getRGB());	
				}
				if(graphic.image.getRGB(i,j) == -1){
					graphic.image.setRGB(i,j, color.green.getRGB());	
				}
				
			}
		}
		graphic.repaint();
		
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
		int rnd = (int)(Math.random()*8);
		switch(rnd){
		case 0: col = (Color.BLACK);
		break;
		case 1: col = Color.blue;
		break;
		case 2: col = Color.cyan;
		break;
		case 3: col = Color.darkGray;
		break;
		case 4: col = Color.MAGENTA;
		break;
		case 5: col = Color.orange;
		break;
		case 6: col = Color.GRAY;
		break;
		case 7: col = Color.green;
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
		
		for(int i = 0; i<furlongs.length; i++){
			furlongs[i] = 21 - (speed/10);
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
		time+= ((int)(Math.random()*6));
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
			
		return forRet;
	}
	public String toString(){
		String time = "";
		
		if(this.getTime() == 999){
			time = "DNF";
		}else{
			time = Tools.timeToString(this.getTime());
		}
		
		return name + "; TIME :: " + time;
		
	}
	public String toPreview(){
		return name + "; Age :: " + age + ";";
	}
	
}