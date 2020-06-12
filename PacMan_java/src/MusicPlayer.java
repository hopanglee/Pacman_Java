import java.io.File;
import java.net.URISyntaxException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;


public class MusicPlayer{
	Clip clip;

	public MusicPlayer(String fileName){
		File bgm;
        AudioInputStream stream;
        AudioFormat format;
        DataLine.Info info;
        
        
        try {
        	bgm = new File(getClass().getResource("/PAC-MAN.wav").toURI());
            stream = AudioSystem.getAudioInputStream(bgm);
            format = stream.getFormat();
            info = new DataLine.Info(Clip.class, format);
            clip = (Clip)AudioSystem.getLine(info);
            clip.open(stream);
            //clip.start();
               
        }catch (Exception e) {
               System.out.println("err : " + e);
        } 

	}
	
	public void Play()
    {
        clip.start();
    }
	
	public void Stop() {
		clip.stop();
	}
}