package songs;

import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.apache.spark.api.java.JavaSparkContext;
import org.springframework.context.annotation.*;

/**
 * Created by Evegeny on 20/07/2017.
 */
@Configuration
@ComponentScan(basePackages = "songs")
@PropertySource("classpath:user.properties")
public class Conf {


    @Bean
    @Profile("DEV")
    public JavaSparkContext sparkContext(){
        SparkConf conf = new SparkConf().setAppName("songs").setMaster("local[*]");
        JavaSparkContext sc = new JavaSparkContext(conf);
        return sc;
    }
    @Bean
    @Profile("PROD")
    public JavaSparkContext prodSparkContext(){
        SparkConf conf = new SparkConf().setAppName("songs");
        JavaSparkContext sc = new JavaSparkContext(conf);
        return sc;
    }
}
