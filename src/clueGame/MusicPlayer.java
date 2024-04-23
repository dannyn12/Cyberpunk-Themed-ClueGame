package clueGame;
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class MusicPlayer {
    public static void main(String[] args) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        File audioFile = new File("src/data/Cyber_Theme.wav");
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
        AudioFormat format = audioStream.getFormat();
        DataLine.Info info = new DataLine.Info(Clip.class, format);
        Clip audioClip = (Clip) AudioSystem.getLine(info);
        audioClip.open(audioStream);
        audioClip.start();
        while (!audioClip.isRunning()) {
            Thread.yield();
        }
        while (audioClip.isRunning()) {
            Thread.yield();
        }
        audioClip.close();
        audioStream.close();
    }
}