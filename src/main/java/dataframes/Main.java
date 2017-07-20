package dataframes;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.SQLContext;
import org.apache.spark.sql.functions;

/**
 * Created by Evegeny on 20/07/2017.
 */
public class Main {
    public static void main(String[] args) {
        SparkConf conf = new SparkConf().setAppName("linkedIn").setMaster("local[*]");
        JavaSparkContext sc = new JavaSparkContext(conf);
        SQLContext sqlContext = new SQLContext(sc);

//        DataFrame df;
//        df.withColumn("abc", functions.col("age").multiply(10).plus());
//        sqlContext.read().json()
    }
}
