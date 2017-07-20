package sparkWithSpring;

/**
 * Created by Evegeny on 20/07/2017.
 */
public class IRobot {

    @InjectObject
    private Speaker speaker;
    @InjectObject
    private Cleaner cleaner;

    public void cleanRoom() {
        speaker.speak("I started");
        cleaner.clean();
        speaker.speak("I finished");
    }
}
