package horses;

public class Bet {
	String horseName;
	double value;
	double odds;
	boolean winner;
	public Bet(String horse, double v, double o){
		horseName = horse;
		value = v;
		odds = o;
		winner = false;
		
		
	}
	public void checkWinner(HorseList stable){
	if(stable.racers.get(0).name.equals(horseName)){
		winner = true;
	}
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
		return "$" + value + " on " + horseName + " :: " + odds + " odds.\n";
	}
}
