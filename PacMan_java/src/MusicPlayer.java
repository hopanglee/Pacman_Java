import java.io.File;
import javax.sound.sampled.*;

public class MusicPlayer {
	private Clip clip;

	public MusicPlayer(String fileName) {
		File bgm;
		AudioInputStream stream;
		AudioFormat format;
		DataLine.Info info;

		try {
			bgm = new File(getClass().getResource(fileName).toURI());
			stream = AudioSystem.getAudioInputStream(bgm);
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
		System.out.println("Play BGM: " + clip.toString());
		clip.start();
	}

	public void stop() {
		clip.stop();
	}
	
	public void close() {
		clip.close();
	}

	public void playSound(File file, float vol, boolean repeat){
		try{
			final Clip clip = (Clip)AudioSystem.getLine(new Line.Info(Clip.class));
			clip.open(AudioSystem.getAudioInputStream(file));
			clip.addLineListener(
					new LineListener() {
						@Override
						public void update(LineEvent event) {
							// TODO Auto-generated method stub
							if(event.getType()==LineEvent.Type.STOP){
								clip.close();
							}
						}
					});
			FloatControl volume = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
			volume.setValue(vol);
			clip.start();
			if(repeat)
				clip.loop(Clip.LOOP_CONTINUOUSLY);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}