package taxi;

import lombok.extern.slf4j.Slf4j;
import org.apache.spark.Accumulator;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.storage.StorageLevel;
import scala.Tuple2;

import java.util.List;

/**
 * Created by Evegeny on 19/07/2017.
 */
@Slf4j
public class Main {
    public static void main(String[] args) {


        SparkConf sparkConf = new SparkConf().setMaster("local[*]").setAppName("taxi");
        JavaSparkContext sc = new JavaSparkContext(sparkConf);
        JavaRDD<String> rdd = sc.textFile("data/taxi_order.txt");
        rdd.persist(StorageLevel.MEMORY_AND_DISK());
        long count = rdd.count();

        System.out.println("count = " + count);

        JavaRDD<Trip> tripRdd = rdd.map(TripUtils::lineToTrip);
        tripRdd.persist(StorageLevel.MEMORY_AND_DISK());
        JavaRDD<Trip> bostonTripsRdd = tripRdd
                .filter(trip -> trip.getCityName().equalsIgnoreCase("boston"));

        bostonTripsRdd.persist(StorageLevel.MEMORY_AND_DISK());

        long longTripsToBoston = bostonTripsRdd.filter(trip -> trip.getKm() > 10).count();
        System.out.println("longTripsToBoston = " + longTripsToBoston);


     /*   Integer totalBostonKm = bostonTripsRdd.map(Trip::getKm)
                .reduce(Integer::sum);*/
        double totalBostonKm = bostonTripsRdd.mapToDouble(Trip::getKm).sum();

        JavaPairRDD<String, String> id2NameRdd = sc.textFile("data/drivers.txt")
                .mapToPair(line -> {
                    String[] strings = line.split(",");
                    return new Tuple2<>(strings[0], strings[1]);
                });


        Accumulator<Integer> bigTripsCounter = sc.accumulator(0);
        JavaPairRDD<String, Integer> sortedTripsRdd = tripRdd.mapToPair(trip -> {
            if (trip.getKm() > 5) {
                bigTripsCounter.add(1);
            }
            return new Tuple2<>(trip.getId(), trip.getKm());
        })
                .reduceByKey(Integer::sum)
                .mapToPair(Tuple2::swap)
                .sortByKey(false)
                .mapToPair(Tuple2::swap);

        Integer value = bigTripsCounter.value();


        List<Tuple2<String, Integer>> list = sortedTripsRdd.take(3);

        JavaRDD<Tuple2<String, Integer>> parallelize = sc.parallelize(list);
        JavaPairRDD<String, Integer> pairRDD = parallelize.mapToPair(s -> s);

        pairRDD.join(id2NameRdd).take(3).forEach(System.out::println);


//        id2NameRdd.join(sortedTripsRdd).take(3)


    }
}









