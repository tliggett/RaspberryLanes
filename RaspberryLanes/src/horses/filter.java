package horses;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class filter {
	static ArrayList<ArrayList<String>> fromFile;
	static ArrayList<String> unique;
	
	public static void main(String[] args) throws NumberFormatException, FileNotFoundException, UnsupportedEncodingException {
		fromFile = ReadFile.readfile("src/horses/RGB.txt");
		unique = new ArrayList<String>();
		for(ArrayList word : fromFile){
			if(isUnique(word.get(0).toString())){
				unique.add(word.get(0).toString());
			}
		}
		PrintWriter writer = new PrintWriter("src/horses/RGB2.txt", "UTF-8");
		for(String send : unique){
			writer.println(send);
		}
		writer.close();
	}
	public static boolean isUnique(String word){
		boolean vrai = true;
		for(String checker : unique){
			if(word.equals(checker)){
				vrai = false;
			}
		}
		
		return vrai;
	
	}

	}

