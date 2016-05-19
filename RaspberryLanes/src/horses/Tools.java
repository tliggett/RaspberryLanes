package horses;

public class Tools {
	public static String timeToString(double numTime){

		String timeString = "";
		
		if(numTime != 999){
		double seconds = numTime%60;
		seconds = (int)(seconds*100);
		seconds/= 100;
		String sec = "";
		
		if(seconds<10){
			sec = "0" + seconds;
		}else{
			sec = "" + seconds;
		}
		
		String min = "" + (int)(numTime/60);
		timeString = min + ":" + sec;
		}else{
			timeString = "DNF";
		}
		return timeString;
		
	}
	public static String numToPlace(int num){
		int number = num;
		int div = num%10;
		String suffix = "";
		if(div == 1 && div != 11){
			suffix = "st";
		}else if(div == 2 && div !=12){
			suffix = "nd";
		}else if(div == 3 && div != 13){
			suffix = "rd";
		}else{
			suffix = "th";
		}
		return "" + number + suffix;
		
		
		
	}
}
