package horses;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class Player {
	public double cash;
	public String name;
	public Player(String name){
		cash = 100;
	}
	
	public Player() throws NumberFormatException, FileNotFoundException{
		ArrayList<ArrayList<String>> player = ReadFile.readfile("src/horses/Player.txt");
		String[] fromFile = player.get(0).get(0).split(",");
		name = fromFile[0];
		cash = Double.parseDouble(fromFile[1]);
	}
	public void advanceWeek(int earnings){
		cash+= earnings; 
		cash+= 100;
	}
	public int placeBet(int bet){
		if(bet > cash){
			bet = (int) cash;
		}
		cash-= bet;
		return bet;
	}
	public void saveToFile() throws FileNotFoundException, UnsupportedEncodingException{
		PrintWriter writer = new PrintWriter("src/horses/Player.txt", "UTF-8");
		writer.println(this.toFile());
		writer.close();
	}
	public String toFile(){
		return name + "," + cash;
	}
	public String toString(){
		return name + " :: " + cash;
	}
}
