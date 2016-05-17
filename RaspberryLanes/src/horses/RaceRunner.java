package horses;

import java.awt.Window;

//© A+ Computer Science  -  www.apluscompsci.com
//Name - 
//Date -
//Class -
//Lab  -

import javax.swing.JFrame;

public class RaceRunner extends JFrame
{
	public static int WIDTH = (int) Window.RIGHT_ALIGNMENT;
	public static int HEIGHT = (int) Window.BOTTOM_ALIGNMENT;

	public RaceRunner()
	{
		super("Race Runner");
		setSize(1300,800);
						
		getContentPane().add(new AnimatedCar());
		//getContentPane().add(new MagicBall());
		setVisible(true);
	}
	
	public static void main( HorseList stable )
	{
		RaceRunner run = new RaceRunner();
	}
}
