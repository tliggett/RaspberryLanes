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
}
