import java.io.File;
import javax.sound.sampled.*;

// 2019311680 ±èÁ¤¿ø ÀÛ¼º
public class MusicPlayer {
	private Clip clip;
	private File sound;

	public MusicPlayer(String fileName) {
		AudioInputStream stream;
		AudioFormat format;
		DataLine.Info info;

		try {
			sound = new File(getClass().getResource(fileName).toURI());
			stream = AudioSystem.getAudioInputStream(sound);
			format = stream.getFormat();
			info = new DataLine.Info(Clip.class, format);
			clip = (Clip) AudioSystem.getLine(info);
			clip.open(stream);
			// clip.start();
		} catch (Exception e) {
			System.out.println("err : " + e);
		}
	}

	public void play() {
		clip.start();
	}

	public void stop() {
		clip.stop();
	}

	public void close() {
		clip.close();
	}
}