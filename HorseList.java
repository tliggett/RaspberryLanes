import java.awt.Color;
import java.awt.Image;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;

public class HorseList {
	ArrayList<Horse> racers;

	public HorseList(boolean isNew) throws NumberFormatException, IOException {
		ArrayList<ArrayList<String>> horses = ReadFile.readfile("resources/RaceHorses.txt");
		ArrayList<ArrayList<String>> horsenames = ReadFile.readfile("resources/HorseNameDatabase.txt");
		racers = new ArrayList<Horse>();
		for (int i = 0; i < horses.size(); i++) {
			String[] fr = horses.get(i).get(0).split(",");
			horses.get(i).remove(0);

			for (String r : fr) {
				horses.get(i).add(r);
			}

		}
		if (!isNew) {
			for (int i = 0; i < horses.size(); i++) {
				racers.add(new Horse(horses.get(i)));
			}
		}
		while (racers.size() < 10) {
			int rand = (int) (Math.random() * (horsenames.size() - 1));
			String horsename = horsenames.get(rand).get(0);
			boolean isSame = true;
			while (isSame) {
				boolean Same = false;
				for (Horse racer : racers) {
					if (racer.name.equals(horsename)) {
						Same = true;
					}
				}
				isSame = Same;
			}
			racers.add(new Horse(horsename));
		}
	}

	public double sumScore() {
		double sum = 0;
		for (Horse racer : racers) {
			sum += racer.calcScore();
		}
		return sum;
	}

	public void saveToFile() throws FileNotFoundException, UnsupportedEncodingException {
		PrintWriter writer = new PrintWriter("resources/RaceHorses.txt", "UTF-8");
		for (Horse racer : racers) {
			writer.println(racer.toFile());
		}
		writer.close();
	}

	public void doRace() {
		for (Horse horse : racers) {
			horse.doRace();
		}

	}

	public String toPreview() {
		String timeString = "";
		for (Horse horse : racers) {
			timeString += horse.toPreview() + "\n";
		}

		return timeString;
	}

	public double getHorseOdds(String horseName) {
		double retu = 0;
		for (Horse racer : racers) {
			if (racer.name.equals(horseName)) {
				retu = (racer.calcScore()) / this.sumScore();
			}
		}
		int rnd = (int) (retu * 100);
		retu = rnd;
		retu /= 100;
		if (retu <= 0) {
			return 0.01;
		}
		return retu;

	}

	public String printResults(int mark) {
		String timeString = "";
		int place = mark;
		for (int i = mark; i < racers.size();) {
			place++;
			if (racers.get(i).age > racers.get(i).maxAge || racers.get(i).getTime() == 999
					|| racers.get(i).getTime() > 130) {

				timeString += "" + Tools.numToPlace(place) + " " + racers.get(i) + "---Retiring" + "\n";
				racers.remove(i);
			} else {
				timeString += "" + Tools.numToPlace(place) + " " + racers.get(i) + "\n";
				racers.get(i).place.add(place);
				i++;
			}
		}

		return timeString;
	}
}

class Horse implements Comparable<Horse> {
	int age;
	int maxAge;
	boolean canCompete;
	double speed;
	double acc;
	double stamina;
	double heart;
	ArrayList<Double> time;
	ArrayList<Integer> place;
	double[] furlongs = new double[10];
	String name;
	double score;
	Color furColor;
	Color maneColor;
	Color saddleColor;
	ImagePanel graphic;
	Image image;
	double x;

	public Horse(int code) {
		if (code == 1) {
			this.name = "Raspberry Lanes";
			age = 0;
			maxAge = 2;
			speed = 150;
			acc = 120;
			stamina = 120;
			heart = 120;
			canCompete = true;
			time = new ArrayList<Double>();
			place = new ArrayList<Integer>();
			furColor = new Color(-3407872);
			maneColor = new Color(-403660);
			saddleColor = new Color(-12865360);

			graphic = new ImagePanel("resources/LogoN.png");
			double x = 0;
			colorGraphic();
		}
		if (code == 2) {
			this.name = "Track";
			age = 0;
			maxAge = 2;
			speed = 50;
			acc = 50;
			stamina = 50;
			heart = 50;
			canCompete = true;
			time = new ArrayList<Double>();
			place = new ArrayList<Integer>();
			furColor = new Color(-3407872);
			maneColor = new Color(-403660);
			saddleColor = new Color(-12865360);

			graphic = new ImagePanel("resources/Track.png");
			double x = 0;
		}
		if (code == 3) {
			this.name = "Donald Trump";
			age = 0;
			maxAge = 2;
			speed = 100;
			acc = 100;
			stamina = 100;
			heart = 100;
			canCompete = true;
			time = new ArrayList<Double>();
			place = new ArrayList<Integer>();
			furColor = new Color(-3407872);
			maneColor = new Color(-403660);
			saddleColor = Color.RED;

			graphic = new ImagePanel("resources/trump.jpg");
			double x = 0;
		}
		if (code == 4) {
			this.name = "Usain Bolt";
			age = 0;
			maxAge = 2;
			speed = 120;
			acc = 90;
			stamina = 120;
			heart = 120;
			canCompete = true;
			time = new ArrayList<Double>();
			place = new ArrayList<Integer>();
			furColor = new Color(-3407872);
			maneColor = new Color(-403660);
			saddleColor = Color.GREEN;

			graphic = new ImagePanel("resources/bolt.jpg");
			double x = 0;
		}
	}

	public Horse(String name) throws IOException {
		this.name = name;
		age = 0;
		maxAge = (int) (Math.random() * 8 + 3);
		speed = (Math.random() * 16) + 85;
		acc = (Math.random() * 16) + 85;
		stamina = (Math.random() * 16) + 85;
		heart = (Math.random() * 16) + 85;
		canCompete = true;
		time = new ArrayList<Double>();
		place = new ArrayList<Integer>();
		furColor = randomColor();
		maneColor = randomColor();
		while (furColor.getRGB() == maneColor.getRGB()) {
			maneColor = randomColor();
		}
		saddleColor = randomSaddleColor();
		while (saddleColor.getRGB() == maneColor.getRGB()) {
			saddleColor = randomSaddleColor();
		}
		graphic = new ImagePanel("resources/LogoN.png");

		double x = 0;
		colorGraphic();

	}

	public Horse(ArrayList<String> fromFile) throws IOException {
		name = fromFile.get(0);
		age = Integer.parseInt(fromFile.get(1));
		maxAge = Integer.parseInt(fromFile.get(2));
		canCompete = Boolean.parseBoolean(fromFile.get(3));
		speed = Double.parseDouble(fromFile.get(4));
		acc = Double.parseDouble(fromFile.get(5));
		stamina = Double.parseDouble(fromFile.get(6));
		heart = Double.parseDouble(fromFile.get(7));
		time = new ArrayList<Double>();
		place = new ArrayList<Integer>();
		furColor = new Color(Integer.parseInt(fromFile.get(8)));
		maneColor = new Color(Integer.parseInt(fromFile.get(9)));
		saddleColor = new Color(Integer.parseInt(fromFile.get(10)));
		graphic = new ImagePanel("resources/LogoN.png");
		for (int i = 11; i < fromFile.size(); i++) {
			time.add(Double.parseDouble(fromFile.get(i)));
		}
		colorGraphic();
	}

	public void colorGraphic() {
		Color darkNavy = new Color(0, 0, 120);
		Color track = new Color(255, 208, 115);
		for (int i = 0; i < graphic.image.getHeight(); i++) {
			for (int j = 0; j < graphic.image.getWidth(); j++) {
				if (graphic.image.getRGB(i, j) == -3407872) {
					graphic.image.setRGB(i, j, furColor.getRGB());
				} else if (graphic.image.getRGB(i, j) == -403660) {
					graphic.image.setRGB(i, j, maneColor.getRGB());
				} else if (graphic.image.getRGB(i, j) == -12865360) {
					graphic.image.setRGB(i, j, saddleColor.getRGB());
				} else if (graphic.image.getRGB(i, j) == -16777095) {
					int rgb = graphic.image.getRGB(i, j);
					rgb = rgb & 0x00FFFFFF;
					graphic.image.setRGB(i, j, rgb);
				} else if (graphic.image.getRGB(i, j) != -1) {
					graphic.image.setRGB(i, j, Color.black.getRGB());
				}
			}

		}
		graphic.repaint();

	}

	public double getPPS() {
		if (time.size() > 0) {
			if (x < 85) {
				return 85 / furlongs[0];
			}
			if (x < 170) {
				return 85 / furlongs[1];
			}
			if (x < 255) {
				return 85 / furlongs[2];
			}
			if (x < 340) {
				return 85 / furlongs[3];
			}
			if (x < 425) {
				return 85 / furlongs[4];
			}
			if (x < 510) {
				return 85 / furlongs[5];
			}
			if (x < 595) {
				return 85 / furlongs[6];
			}
			if (x < 680) {
				return 85 / furlongs[7];
			}
			if (x < 765) {
				return 85 / furlongs[8];
			}
			return 85 / furlongs[9];
		} else {
			return 0;
		}
	}

	public double calcScore() {
		double ret = 0;
		if (time.size() == 0) {
			return 2;
		}
		ret = 130 - time.get(time.size() - 1) - (age / 2);
		if (ret <= 0) {
			ret = 0.01;
		}
		return ret;
	}

	public Color randomColor() {
		Color col = new Color(0);
		int rnd = (int) (Math.random() * 17);
		switch (rnd) {
		case 0:
			col = Color.black;
			break;
		case 1:
			col = new Color(255, 248, 220);
			break;
		case 2:
			col = new Color(184, 134, 11);
			break;
		case 3:
			col = Color.darkGray;
			break;
		case 4:
			col = new Color(245, 245, 220);
			break;
		case 5:
			col = Color.GRAY;
			break;
		case 6:
			col = new Color(153, 51, 0);
			break;
		case 7:
			col = new Color(204, 51, 0);
			break;
		case 8:
			col = new Color(102, 26, 0);
			break;
		case 9:
			col = new Color(77, 19, 0);
			break;
		case 10:
			col = new Color(26, 6, 0);
			break;
		case 11:
			col = new Color(255, 168, 128);
			break;
		case 12:
			col = new Color(255, 255, 204);
			break;
		case 13:
			col = new Color(240, 255, 240);
			break;
		case 14:
			col = new Color(153, 0, 0);
			break;
		}

		return col;
	}

	public Color randomSaddleColor() {
		Color col = new Color(0);
		int rnd = (int) (Math.random() * 17);
		switch (rnd) {
		case 0:
			col = new Color(138, 43, 226);
			break;
		case 1:
			col = Color.pink;
			break;
		case 2:
			col = Color.cyan;
			break;
		case 3:
			col = new Color(75, 0, 130);
			break;
		case 4:
			col = Color.MAGENTA;
			break;
		case 5:
			col = Color.GRAY;
			break;
		case 6:
			col = new Color(153, 51, 0);
			break;
		case 7:
			col = new Color(204, 51, 0);
			break;
		case 8:
			col = new Color(102, 26, 0);
			break;
		case 9:
			col = new Color(77, 19, 0);
			break;
		case 10:
			col = new Color(26, 6, 0);
			break;
		case 11:
			col = new Color(255, 168, 128);
			break;
		case 12:
			col = new Color(255, 255, 204);
			break;
		case 13:
			col = new Color(77, 255, 136);
			break;
		case 14:
			col = new Color(153, 0, 0);
			break;
		}

		return col;
	}

	public void doRace() {
		double time = 0;
		age++;

		int injury = (int) (Math.random() * 100);
		if (injury == 24) {
			this.time.add(999.0);
			return;
		}

		double rnd = ((Math.random() * 6));

		for (int i = 0; i < furlongs.length; i++) {
			furlongs[i] = 21 - (speed / 10) + rnd / 10;
		}

		furlongs[0] += (10 - (acc / 10)) * 2;
		int sdet = (int) ((stamina / 10) - 2);
		int dep = 1;
		for (int i = sdet; i < furlongs.length; i++) {
			furlongs[i] += dep;
			dep++;
		}

		furlongs[9] -= (heart / 20);
		if (furlongs[9] < speed) {
			furlongs[9] = 20.5 - (speed / 10);
		}

		for (double furlong : furlongs) {
			time += furlong;
		}

		time += 4;
		int ageDiff = maxAge - age;
		if (ageDiff != 0) {
			speed -= (10) / (ageDiff);
			acc -= (3) / (ageDiff);
		}

		time = ((int) (time * 100));
		time /= 100;
		Double add = time;
		this.time.add(add);
		return;

	}

	public double getTime() {
		return time.get(time.size() - 1);
	}

	@Override
	public int compareTo(Horse h) {
		if (h.getTime() > this.getTime()) {
			return -1;
		}
		if (h.getTime() == this.getTime()) {
			return 0;
		}
		return 1;

	}

	public String toFile() {
		String forRet = "";
		forRet += "" + name + "," + age + "," + maxAge + "," + canCompete + "," + speed + "," + acc + "," + stamina
				+ "," + heart + "," + furColor.getRGB() + "," + maneColor.getRGB() + "," + saddleColor.getRGB() + ",";
		for (Double tim : time) {
			forRet += tim + ",";
		}

		return forRet;
	}

	public String toString() {
		String time = "";

		if (this.getTime() == 999) {
			time = "DNF";
		} else {
			time = Tools.timeToString(this.getTime());
		}

		return name + " " + time;

	}

	public String toPreview() {
		String forRet = "PREVIOUS TIMES\n";
		for (Double tim : time) {
			forRet += Tools.timeToString(tim) + "\n";
		}
		return forRet;
	}

}
