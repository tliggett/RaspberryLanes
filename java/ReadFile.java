import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;



public class ReadFile {
	
	public static ArrayList<ArrayList<String>> readfile(String filename) throws FileNotFoundException, NumberFormatException {
		Scanner scan = new Scanner(new File (filename));
		String fileName = filename;
		int i = 0;
		int lines = 0;
		
		// This will reference one line at a time
		String line = null;

		try {
			// FileReader reads text files in the default encoding.
			FileReader fileReader = new FileReader(fileName);

			// Always wrap FileReader in BufferedReader.
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			
			ArrayList<ArrayList<String>> mat = new ArrayList<ArrayList<String>>();
			while ((line = bufferedReader.readLine()) != null) {
				int g;
				int baselength = 0;
				String[] base = line.split(" ");
				ArrayList<String> adder = new ArrayList<String>();
				for(String item: base){
					adder.add(item);
				}
				mat.add(adder);
				
			}
			
			

			// Always close files.
			bufferedReader.close();
			return mat;
			
		} catch (FileNotFoundException ex) {
			System.out.println("Unable to open file '" + fileName + "'");
		} catch (IOException ex) {
			System.out.println("Error reading file '" + fileName + "'");
			

			
					}
		ArrayList<ArrayList<String>> ret = new ArrayList<ArrayList<String>>(lines);
		
		
		return ret;
		

	}
	
	
}
