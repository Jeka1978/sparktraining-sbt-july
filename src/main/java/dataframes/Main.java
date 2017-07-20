package dataframes;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SQLContext;
import org.apache.spark.sql.functions;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.storage.StorageLevel;

import static org.apache.spark.sql.functions.*;

/**
 * Created by Evegeny on 20/07/2017.
 */
public class Main {
    private static final String SALARY = "salary";
    private static final String AGE = "age";
    private static final String KEYWORDS = "keywords";
    private static final String KEYWORD ="keyword" ;
    private static final String AMOUNT = "amount";
    private static final String NAME = "name";

    public static void main(String[] args) {
        SparkConf conf = new SparkConf().setAppName("linkedIn").setMaster("local[*]");
        JavaSparkContext sc = new JavaSparkContext(conf);
        SQLContext sqlContext = new SQLContext(sc);

        DataFrame df = sqlContext.read().json("data/linkedIn/profiles.json");
        df.show(false);
        df.printSchema();
        StructField[] fields = df.schema().fields();
        for (StructField field : fields) {
            System.out.println(field.dataType());
        }
        df = df.withColumn(SALARY, col(AGE).multiply(10).multiply(size(col(KEYWORDS))));
        df.persist(StorageLevel.MEMORY_AND_DISK());
        df.show();
        DataFrame keywordDf = df.withColumn(KEYWORD, explode(col(KEYWORDS))).select(KEYWORD);
        Row row = keywordDf.groupBy(KEYWORD).agg(count(KEYWORD).as(AMOUNT))
                .orderBy(col(AMOUNT).desc()).head();

        String mostPopularTechnology = row.getAs(KEYWORD);
        System.out.println("mostPopularTechnology = " + mostPopularTechnology);
        df.where(col(SALARY).leq(1200).or(col(NAME).like("%Evgeny%"))).
                where(array_contains(col(KEYWORDS), mostPopularTechnology)).show();
    }
}









