package sparkWithSpring;

import java.util.Random;

/**
 * Created by Evegeny on 20/07/2017.
 */
public class Elf {

    @InjectRandomInt(min=10,max=30)
    private int power;
    private int health;


}
