package transactions;

import lombok.SneakyThrows;
import org.apache.spark.SparkContext;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.*;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static org.apache.spark.sql.functions.callUDF;
import static org.apache.spark.sql.functions.col;

/**
 * Created by Evegeny on 20/07/2017.
 */
@Service
public class TransactionService {
    @Autowired
    private JavaSparkContext sc;

    @Autowired
    private SQLContext sqlContext;


    @SneakyThrows
    public void handleTransactions() {
        JavaRDD<String> rdd = sc.textFile("data/transactions.csv");

        StructType schema = buildSchema();


        JavaRDD<Row> rowJavaRDD = rdd.map(line -> {
            String[] data = line.split(";");
            return RowFactory.create(data[0], Integer.parseInt(data[1]), data[2], data[3]);
        });


        DataFrame df = sqlContext.createDataFrame(rowJavaRDD, schema);
        df.withColumn("country name",
                callUDF(CountryCodeToCountryName.class.getName(),col("countryCode"))).show();
        Thread.sleep(3*60*1000);

    }

    private StructType buildSchema() {
        return DataTypes.createStructType(new StructField[]{
                        DataTypes.createStructField("countryCode", DataTypes.StringType, false),
                        DataTypes.createStructField("money", DataTypes.IntegerType, false),
                        DataTypes.createStructField("from", DataTypes.StringType, false),
                        DataTypes.createStructField("to", DataTypes.StringType, false)
                }
        );
    }















}
