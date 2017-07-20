package sparkWithSpring;

/**
 * Created by Evegeny on 20/07/2017.
 */
public class Main {
    public static void main(String[] args) throws InstantiationException, IllegalAccessException {
       ObjectFactory.getInstance().createObject(IRobot.class).cleanRoom();
    }
}
