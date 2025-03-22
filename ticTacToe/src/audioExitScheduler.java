import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
public class audioExitScheduler {
    public void scheduleExit() {

        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.schedule(() -> {
            // Close the program after 4 seconds
            System.exit(0);
        }, 4, TimeUnit.SECONDS);
    }
    public void playAudio(String fileName) {
        try {
            File file = new File(fileName);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);

            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
}
