package songs;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.broadcast.Broadcast;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import scala.Tuple2;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Evegeny on 20/07/2017.
 */
@Service
public class SongServiceImpl implements SongService, Serializable {

    @Autowired
    private transient JavaSparkContext sc;

    private Broadcast<List<String>> broadcast;

    @Value("${garbage}")
    private void initGarbage(String[] garbageArray) {
        List<String> garbage = Arrays.asList(garbageArray);
        broadcast = sc.broadcast(garbage);
    }

    @Override
    public List<String> topX(String fileName, int x) {
        JavaRDD<String> rdd = sc.textFile(fileName);
        return rdd.flatMap(WordsUtil::getWords)
                .map(String::toLowerCase)
                .filter(word -> !broadcast.getValue().contains(word))
                .mapToPair(word -> new Tuple2<>(word, 1))
                .reduceByKey(Integer::sum)
                .mapToPair(Tuple2::swap)
                .sortByKey(false)
                .map(Tuple2::_2)
                .take(x);


    }
}
