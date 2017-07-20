package transactions;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SQLContext;
import org.springframework.context.annotation.*;
import org.springframework.test.context.ContextConfiguration;

/**
 * Created by Evegeny on 20/07/2017.
 */
@Configuration
@ComponentScan
@PropertySource("classpath:user.properties")
public class Config {
    @Bean
    public JavaSparkContext sc(){
        SparkConf conf = new SparkConf().setAppName("songs").setMaster("local[*]");
        JavaSparkContext sc = new JavaSparkContext(conf);
        return sc;
    }


    @Bean
    public SQLContext sqlContext(){
        return new SQLContext(sc());
    }
}
