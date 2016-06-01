package horses;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sound {
	Clip clip;
	AudioInputStream audioInputStream;
	String filename;
	int start;
	int end;
	
	public Sound(String filename, int start, int end) throws LineUnavailableException, IOException, UnsupportedAudioFileException{
		this.filename = filename;
		audioInputStream = AudioSystem.getAudioInputStream(new File(this.filename).getAbsoluteFile());
        clip = AudioSystem.getClip();
        clip.open(audioInputStream);
        this.start = start;
        this.end = end;
	}
	void setSound(String filename) throws Exception{
		this.filename = filename;
		audioInputStream = AudioSystem.getAudioInputStream(new File(this.filename).getAbsoluteFile());
		clip.close();
		clip.open(audioInputStream);
	}
	
	void setStart(int start){
		this.start = start;
	}
	void start() throws Exception{
		
		clip.setFramePosition(start);
		clip.start();
	}
	void stop() throws Exception{
		clip.stop();
		audioInputStream.close();
		audioInputStream = AudioSystem.getAudioInputStream(new File(this.filename).getAbsoluteFile());
		clip.close();
		clip.open(audioInputStream);
		
	}
	
}
