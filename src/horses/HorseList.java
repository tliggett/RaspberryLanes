package horses;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;


public class HorseList {
	ArrayList<Horse> racers;
	
	
	public HorseList() throws NumberFormatException, FileNotFoundException{
		ArrayList<ArrayList<String>> horses =  ReadFile.readfile("src/horses/RaceHorses.txt");
		racers = new ArrayList<Horse>();
		for(int i = 0; i < horses.size(); i ++){
			String[] fr = horses.get(i).get(0).split(",");
			horses.get(i).remove(0);
			
			for(String r : fr){
				horses.get(i).add(r);
			}
			
		}	
		
		for(int i = 0; i < horses.size(); i++){
			racers.add(new Horse(horses.get(i)));
		}
		
	}
	
	public void saveToFile() throws FileNotFoundException, UnsupportedEncodingException{
		PrintWriter writer = new PrintWriter("src/horses/RaceHorses.txt", "UTF-8");
		for(Horse racer : racers){
			writer.println(racer.toFile());
		}
		writer.close();
	}
	
	public void doRace(){
		for(Horse horse : racers){
			horse.doRace();
		}

		Collections.sort(racers);
		
		
		
		
		}
		
	
	public String toPreview(){
		String timeString = "";
		for(Horse horse : racers){
			timeString += horse.toPreview() + "\n";
		}
		
		
		return timeString;
	}
	
	
	public String toString(){
		String timeString = "";
		int place = 0;
		for(int i = 0; i< racers.size();){
			place++;
			if(racers.get(i).age > racers.get(i).maxAge || racers.get(i).getTime() == 999 || racers.get(i).time.get(racers.get(i).time.size()-1) > 140){
			
				timeString += "" + place + racers.get(i) + "---Retiring"+ "\n";	
				racers.remove(i);
			}
			else{
				timeString+= "" + place +  racers.get(i) + "\n";
				racers.get(i).place.add(place);
				i++;
			}
			}
		
		
		
		return timeString;
	}
}
