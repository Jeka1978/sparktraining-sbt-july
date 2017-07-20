package sparkWithSpring;

/**
 * Created by Evegeny on 20/07/2017.
 */
public class ConsoleSpeaker implements Speaker {
    @Override
    public void speak(String message) {
        System.out.println(message);
    }
}
