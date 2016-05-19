package horses;

public class Bet {
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
