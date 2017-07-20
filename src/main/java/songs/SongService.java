package songs;

import org.apache.spark.api.java.JavaRDD;

import java.util.List;

/**
 * Created by Evegeny on 20/07/2017.
 */
public interface SongService {
    List<String> topX(String fileName, int x);
}
