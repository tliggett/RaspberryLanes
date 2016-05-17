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
