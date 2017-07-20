package transactions;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.SQLContext;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;

/**
 * Created by Evegeny on 20/07/2017.
 */
public class Main {
    public static void main(String[] args) {
        SparkConf conf = new SparkConf().setAppName("linkedIn").setMaster("local[*]");
        JavaSparkContext sc = new JavaSparkContext(conf);
        SQLContext sqlContext = new SQLContext(sc);

        JavaRDD<String> rdd = sc.textFile("data/transactions.csv");

        StructType schema = DataTypes.createStructType(new StructField[]{
                        DataTypes.createStructField("countryCode", DataTypes.StringType, false),
                        DataTypes.createStructField("money", DataTypes.IntegerType, false),
                        DataTypes.createStructField("from", DataTypes.StringType, false),
                        DataTypes.createStructField("to", DataTypes.StringType, false)
                }
        );


        JavaRDD<Row> rowJavaRDD = rdd.map(line -> {
            String[] data = line.split(";");
            return RowFactory.create(data[0], Integer.parseInt(data[1]), data[2], data[3]);
        });


        DataFrame df = sqlContext.createDataFrame(rowJavaRDD, schema);
        df.show();
        df.withColumn("countryName",???)


    }
}





