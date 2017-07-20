package songs;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Evegeny on 20/07/2017.
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = Conf.class)
@ActiveProfiles("DEV")
public class SongServiceImplTest {

    @Autowired
    private SongService songService;

    @Test
    public void topX() throws Exception {
        List<String> topX = songService.topX("data/songs/abc.txt", 2);
        System.out.println(topX);
        Assert.assertTrue(topX.get(0).equals("java"));
        Assert.assertTrue(topX.get(1).equals("scala"));

        List<String> list = songService.topX("data/songs/Yesterday.txt", 3);
        System.out.println("list = " + list);
        Assert.assertEquals("yesterday",list.get(0));


    }

}