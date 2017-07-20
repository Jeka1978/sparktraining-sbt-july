package taxi;

/**
 * Created by Evegeny on 20/07/2017.
 */
public class TripUtils {
    public static Trip lineToTrip(String line) {
        String[] arr = line.split(" ");
        Trip trip = Trip.builder().id(arr[0]).cityName(arr[1]).km(Integer.parseInt(arr[2])).build();
        return trip;
    }
}

