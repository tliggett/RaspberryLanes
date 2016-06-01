package horses;

import java.util.ArrayList;

public class BetList {
	public ArrayList<Bet> bets;
	public BetList(){
		bets = new ArrayList<Bet>();
	}
	public int reward(HorseList stable){
		int ret = 0;
		for(Bet bet : bets){
			bet.checkWinner(stable);
			ret += bet.reward();
		}
		bets.removeAll(bets);
		return ret;
	}
	
	public String toString(){
		String ret = "";
		for(Bet bet : bets){
			ret +=  "" + bet;
		}
		return ret;
	}
}
class Bet {
	String horseName;
	double value;
	double odds;
	boolean winner;
	public Bet(String horse, double v, HorseList stable){
		horseName = horse;
		value = v;
		odds = calcOdds(stable);
		winner = false;
		
		
	}
	public void checkWinner(HorseList stable){
	if(stable.racers.get(0).name.equals(horseName)){
		winner = true;
	}
	}
	public double calcOdds(HorseList stable){
		int index = 0;
		for(int i = 0; i < stable.racers.size(); i++){
			if(stable.racers.get(i).name.equals(horseName)){
				index = i;
			}}
		
		double odd = (stable.racers.get(index).calcScore())/(stable.sumScore());
		int rnd =(int)(odd*100);
		odd = rnd;
		odd/= 100;
		if(odd <= 0){
			return 0.01;
		}
		return odd;
		
	}
	
	public int reward(){
		if(winner){
			return (int)(value/odds);
		}
	return 0;
	}
	
	public String oddString(){
	return null;
	}
	public String toString(){
		return "$" + value + " " + horseName + " " + odds + "\n";
	}
}